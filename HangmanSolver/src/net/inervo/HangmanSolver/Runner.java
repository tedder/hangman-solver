package net.inervo.HangmanSolver;

import org.commoncrawl.hangman.Guess;
import org.commoncrawl.hangman.GuessingStrategy;
import org.commoncrawl.hangman.HangmanGame;
import org.commoncrawl.hangman.HangmanGame.Status;

public class Runner implements Runnable {

	@Override
	public void run() {
		SolutionStatistics stat = new SolutionStatistics();

		try {
			runSolver(RandomAlphabetSolver.class, stat);
			runSolver(SequentialAlphabetSolver.class, stat);
			runSolver(ReverseSequentialAlphabetSolver.class, stat);
			runSolver(MostPopularOurDictionaryAlphabetSolver.class, stat);
			runSolver(MostPopularCornellAlphabetSolver.class, stat);
			runSolver(MostPopularOxfordAlphabetSolver.class, stat);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(stat.getStats());
		// System.out.println("final state: " + h.gameStatus() + " / " +
		// h.getIncorrectlyGuessedLetters().size() );
	}

	// forgot the "extends" syntax, thanks SO!
	// http://stackoverflow.com/questions/2859589/passing-interface-class-as-a-parameter-in-java
	protected void runSolver(Class<? extends GuessingStrategy> solverClass, SolutionStatistics stat)
			throws InstantiationException, IllegalAccessException {
		Gamemaster gamelist = new Gamemaster(99, 1000);

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
			//System.out.println("guess: " + g + " solved_state: " + h.getGuessedSoFar());
			g.makeGuess(h);
			//System.out.println(" .. solved_state: " + h.getGuessedSoFar());
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("hello world.");
		new Runner().run();
		System.out.println("we're out.");
	}

}
