package net.inervo.hangman.solver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.inervo.hangman.Dictionary;

import org.commoncrawl.hangman.Guess;
import org.commoncrawl.hangman.GuessLetter;
import org.commoncrawl.hangman.GuessingStrategy;
import org.commoncrawl.hangman.HangmanGame;

public class MostPopularOurDictionary implements GuessingStrategy {
	final static List<Character> CALCULATED_LETTERS = getMostPopularLetters(new Dictionary().getDictionaryAsList());
	List<Character> letters = new ArrayList<Character>(CALCULATED_LETTERS);
	

	public MostPopularOurDictionary() {

	}

	private static List<Character> getMostPopularLetters(List<String> dictlist) {
		Map<Character, Integer> popularityContest = new HashMap<Character, Integer>();
		for (String word : dictlist) {
			for (char c : word.toCharArray()) {
				Integer i = popularityContest.get(c);
				if (i == null) { i = 0; }
				popularityContest.put(c, ++i);
			}
		}

		List<Character> popularLetters = new ArrayList<Character>(popularityContest.keySet());
		Collections.sort(popularLetters, new ValueComparator(popularityContest) );
		return popularLetters;
	}

	@Override
	public Guess nextGuess(HangmanGame game) {
		return new GuessLetter(letters.remove(0));
	}
	
	// stolen from SO: http://stackoverflow.com/questions/109383/how-to-sort-a-mapkey-value-on-the-values-in-java
	static class ValueComparator implements Comparator<Character> {
	    Map<Character, Integer> base;
	    public ValueComparator(Map<Character, Integer> base) {
	        this.base = base;
	    }

	    // Note: this comparator imposes orderings that are inconsistent with equals.    
	    public int compare(Character a, Character b) {
	        return base.get(b).compareTo(base.get(a));
	    }
	}

}
