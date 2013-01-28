package net.inervo.hangman;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.inervo.hangman.solver.MostPopularCornell;
import net.inervo.hangman.solver.MostPopularOurDictionary;
import net.inervo.hangman.solver.MostPopularOurDictionaryWithWordRegex;
import net.inervo.hangman.solver.MostPopularOurDictionaryWithWords;
import net.inervo.hangman.solver.MostPopularOxford;
import net.inervo.hangman.solver.RandomOrder;
import net.inervo.hangman.solver.ReverseSequentialOrder;
import net.inervo.hangman.solver.SequentialOrder;

import org.commoncrawl.hangman.Guess;
import org.commoncrawl.hangman.GuessingStrategy;
import org.commoncrawl.hangman.HangmanGame;
import org.commoncrawl.hangman.HangmanGame.Status;

public class Runner implements Runnable {

	@Override
	public void run() {
		try {
			SolutionStatistics stat = new SolutionStatistics();

			
			// 'infinite' guesses for the first pass.
			int maxGuesses = 99;
			
			// get 1000 words we'll use for the first few experiments.
			List<String> words = new ArrayList<String>();
			Dictionary dict = new Dictionary();
			for (int i = 0; i < 1000; ++i) {
				words.add(dict.getRandomEntry());
			}


			System.out.println("\n### guesses=99\n\nWith the \"number of guesses\" set to 99. This captures all the information above but is different than the expected output to the problem. Note the avg_wrong_guesses data gives a much better indication of solution fitness.\n\n```");

			runSolver(RandomOrder.class, stat, maxGuesses, words);
			runSolver(SequentialOrder.class, stat, maxGuesses, words);
			runSolver(ReverseSequentialOrder.class, stat, maxGuesses, words);
			runSolver(MostPopularOurDictionary.class, stat, maxGuesses, words);
			runSolver(MostPopularCornell.class, stat, maxGuesses, words);
			runSolver(MostPopularOxford.class, stat, maxGuesses, words);
			runSolver(MostPopularOurDictionaryWithWords.class, stat, maxGuesses, words);
			runSolver(MostPopularOurDictionaryWithWordRegex.class, stat, maxGuesses, words);

			System.out.println(stat.getStats() + "```");
			stat = new SolutionStatistics();
			
			// same words, 6 guesses.
			System.out.println("\n### guesses=6\n\nOutput with \"number of guesses\" set to 6. This provides less information on the average number of guesses to provide a solution. (in fact, the avg_wrong_guesses is basically noise)\n\n```");
			
			maxGuesses = 6;
			runSolver(RandomOrder.class, stat, maxGuesses, words);
			runSolver(SequentialOrder.class, stat, maxGuesses, words);
			runSolver(ReverseSequentialOrder.class, stat, maxGuesses, words);
			runSolver(MostPopularOurDictionary.class, stat, maxGuesses, words);
			runSolver(MostPopularCornell.class, stat, maxGuesses, words);
			runSolver(MostPopularOxford.class, stat, maxGuesses, words);
			runSolver(MostPopularOurDictionaryWithWords.class, stat, maxGuesses, words);
			runSolver(MostPopularOurDictionaryWithWordRegex.class, stat, maxGuesses, words);

			System.out.println(stat.getStats() + "```");
			stat = new SolutionStatistics();

			// 15 words from the original problem, infinite guesses.
			System.out.println("\n### Specific words given in problem:\n\nThe number of words that were determined successfully by these algorithms are *substantially* lower than the implied solutions in the problem email. Admittedly, those were guessed with a combination of letter+word guesses, where six of eight solutions given here are purely based on letter guessing.\n\n```");

			words = Arrays.asList("comaker", "cumulate", "eruptive", "factual", "monadism", "mus", "nagging", "oses",
					"remembered", "spodumenes", "stereoisomers", "toxics", "trichromats", "triose", "uniformed");
			maxGuesses = 99;
			runSolver(RandomOrder.class, stat, maxGuesses, words);
			runSolver(SequentialOrder.class, stat, maxGuesses, words);
			runSolver(ReverseSequentialOrder.class, stat, maxGuesses, words);
			runSolver(MostPopularOurDictionary.class, stat, maxGuesses, words);
			runSolver(MostPopularCornell.class, stat, maxGuesses, words);
			runSolver(MostPopularOxford.class, stat, maxGuesses, words);
			runSolver(MostPopularOurDictionaryWithWords.class, stat, maxGuesses, words);
			runSolver(MostPopularOurDictionaryWithWordRegex.class, stat, maxGuesses, words);

			System.out.println(stat.getStats() + "```");
			
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// System.out.println("final state: " + h.gameStatus() + " / " +
		// h.getIncorrectlyGuessedLetters().size() );
	}

	// forgot the "extends" syntax, thanks SO!
	// http://stackoverflow.com/questions/2859589/passing-interface-class-as-a-parameter-in-java
	protected void runSolver(Class<? extends GuessingStrategy> solverClass, SolutionStatistics stat, int maxGuesses,
			List<String> specificWords) throws InstantiationException, IllegalAccessException {

		Gamemaster gamelist = new Gamemaster(maxGuesses, specificWords);

		while (gamelist.hasNext()) {
			GuessingStrategy s = solverClass.newInstance();
			HangmanGame h = gamelist.next();
			doGame(s, h);
			stat.addStat(s.getClass().getCanonicalName(), h.getIncorrectlyGuessedLetters().size());
		}
	}

	public void doGame(GuessingStrategy s, HangmanGame h) {
		while (h.gameStatus().equals(Status.KEEP_GUESSING)) {
			Guess g = s.nextGuess(h);
			g.makeGuess(h);
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new Runner().run();
	}

}
