package net.inervo.HangmanSolver;

import java.util.ArrayList;
import java.util.List;

import org.commoncrawl.hangman.Guess;
import org.commoncrawl.hangman.GuessLetter;
import org.commoncrawl.hangman.GuessingStrategy;
import org.commoncrawl.hangman.HangmanGame;

public class MostPopularOxfordAlphabetSolver implements GuessingStrategy {
	List<Character> letters = new ArrayList<Character>();
	

	public MostPopularOxfordAlphabetSolver() {
		// using this frequency list: http://en.wikipedia.org/wiki/Letter_frequency#Relative_frequencies_of_letters_in_the_English_language
		for (char c : "etaoinshrdlcumwfgypbvkjxqz".toCharArray() ) {
			letters.add(c);
		}
	}

	@Override
	public Guess nextGuess(HangmanGame game) {
		return new GuessLetter(letters.remove(0));
	}
	
}
