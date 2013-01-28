package net.inervo.hangman.solver;

import java.util.ArrayList;
import java.util.List;

import org.commoncrawl.hangman.Guess;
import org.commoncrawl.hangman.GuessLetter;
import org.commoncrawl.hangman.GuessingStrategy;
import org.commoncrawl.hangman.HangmanGame;

public class RandomAlphabetSolver implements GuessingStrategy {
	List<Character> letters = null;
	
	
	public RandomAlphabetSolver( ){
		// Arrays.asList doesn't work on primitives :-( so we'll do it by hand.
		letters = new ArrayList<Character>();

		// stole String.toCharArray from coderanch. No reason to make it more complicated.
		// http://www.coderanch.com/t/514528/java/java/initialize-character-array-characters-predefined
		for (char c : "abcdefghijklmnopqrstuvwxyz".toCharArray() ) {
			letters.add(c);
		}
	}
	
	@Override
	public Guess nextGuess(HangmanGame game) {
		// round returns a long. We need an int for the arrayList, and we know it isn't going to be a long anyhow, so this is safeish.
		int rand = (int) Math.round( Math.random() * (letters.size()-1) );
		Character c = letters.remove(rand);
		
		return new GuessLetter(c);
	}

}
