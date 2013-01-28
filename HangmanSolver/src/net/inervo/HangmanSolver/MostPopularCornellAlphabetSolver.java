package net.inervo.HangmanSolver;

import java.util.ArrayList;
import java.util.List;

import org.commoncrawl.hangman.Guess;
import org.commoncrawl.hangman.GuessLetter;
import org.commoncrawl.hangman.GuessingStrategy;
import org.commoncrawl.hangman.HangmanGame;

public class MostPopularCornellAlphabetSolver implements GuessingStrategy {
	List<Character> letters = new ArrayList<Character>();
	

	public MostPopularCornellAlphabetSolver() {
		// using this frequency list: http://www.math.cornell.edu/~mec/2003-2004/cryptography/subs/frequencies.html
		for (char c : "etaoinsrhdlucmfywgpbvkxqjz".toCharArray() ) {
			letters.add(c);
		}
	}

	@Override
	public Guess nextGuess(HangmanGame game) {
		return new GuessLetter(letters.remove(0));
	}
	
}
