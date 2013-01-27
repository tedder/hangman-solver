package net.inervo.HangmanSolver;

import org.commoncrawl.hangman.Guess;
import org.commoncrawl.hangman.GuessLetter;
import org.commoncrawl.hangman.GuessingStrategy;
import org.commoncrawl.hangman.HangmanGame;

public class ReverseSequentialAlphabetSolver implements GuessingStrategy {
	char currentLetter = '{';

	@Override
	public Guess nextGuess(HangmanGame game) {
		// couldn't figure out an elegant way to start this.
		if (currentLetter == '{') {
			currentLetter = 'z';
		} else {
			--currentLetter;
		}
		
		return new GuessLetter(currentLetter);
	}

}
