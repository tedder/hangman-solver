package net.inervo.HangmanSolver;

import org.commoncrawl.hangman.Guess;
import org.commoncrawl.hangman.GuessingStrategy;
import org.commoncrawl.hangman.HangmanGame;
import org.commoncrawl.hangman.HangmanGame.Status;

public class Runner implements Runnable {

	@Override
	public void run() {
		SolutionStatistics stat = new SolutionStatistics();
		Gamemaster gamelist = new Gamemaster(99);

		while (gamelist.hasNext()) {
			HangmanGame h = gamelist.next();

			GuessingStrategy sas = new SequentialAlphabetSolver();
			doGame(sas, h);
			stat.addStat(sas.getClass().getCanonicalName(), h.getIncorrectlyGuessedLetters().size());
		}
		System.out.println(stat.getStats());
		// System.out.println("final state: " + h.gameStatus() + " / " +
		// h.getIncorrectlyGuessedLetters().size() );
	}

	public void doGame(GuessingStrategy s, HangmanGame h) {
		while (h.gameStatus().equals(Status.KEEP_GUESSING)) {
			Guess g = s.nextGuess(h);
			System.out.println("guess: " + g + " solved_state: " + h.getGuessedSoFar());
			g.makeGuess(h);
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
