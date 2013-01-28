package net.inervo.hangman.solver;

import org.commoncrawl.hangman.Guess;
import org.commoncrawl.hangman.GuessLetter;
import org.commoncrawl.hangman.GuessingStrategy;
import org.commoncrawl.hangman.HangmanGame;

public class SequentialAlphabetSolver implements GuessingStrategy {
	char currentLetter = '_';

	@Override
	public Guess nextGuess(HangmanGame game) {
		// couldn't figure out an elegant way to start this.
		if (currentLetter == '_') {
			currentLetter = 'a';
		} else {
			++currentLetter;
		}
		
		return new GuessLetter(currentLetter);
	}

}
