package net.inervo.hangman.solver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.inervo.hangman.Dictionary;

import org.commoncrawl.hangman.Guess;
import org.commoncrawl.hangman.GuessLetter;
import org.commoncrawl.hangman.GuessWord;
import org.commoncrawl.hangman.GuessingStrategy;
import org.commoncrawl.hangman.HangmanGame;

public class MostPopularOurDictionaryWithWords implements GuessingStrategy {
	final static List<Character> CALCULATED_LETTERS = getMostPopularLetters(new Dictionary().getDictionaryAsList());
	List<Character> letters = new ArrayList<Character>(CALCULATED_LETTERS);
	List<String> remainingWords = new Dictionary().getDictionaryAsList();

	private static List<Character> getMostPopularLetters(List<String> dictlist) {
		Map<Character, Integer> popularityContest = new HashMap<Character, Integer>();
		for (String word : dictlist) {
			for (char c : word.toCharArray()) {
				Integer i = popularityContest.get(c);
				if (i == null) {
					i = 0;
				}
				popularityContest.put(c, ++i);
			}
		}

		List<Character> popularLetters = new ArrayList<Character>(popularityContest.keySet());
		Collections.sort(popularLetters, new ValueComparator(popularityContest));
		return popularLetters;
	}

	public List<String> wordsRemaining(Set<Character> set, int wordLength, HangmanGame game) {
		List<String> narrowedList = new ArrayList<String>();
		WORD: for (String dword : remainingWords) {
			// this is a fast check.
			if (dword.length() != wordLength)
				continue;

			// this is much uglier.
			for (char c : set ) {
				c = Character.toLowerCase(c);
				if (dword.indexOf(c) < 0) continue WORD;
			}
			
			for (char c : dword.toCharArray() ) {
				if (! set.contains(c)) continue WORD;
			}
			
			narrowedList.add(dword);
		}
		
		remainingWords = narrowedList;

		return narrowedList;
	}

	@Override
	public Guess nextGuess(HangmanGame game) {
		List<String> wordlist = wordsRemaining(	game.getCorrectlyGuessedLetters(), game.getSecretWordLength(), game );
		if (wordlist.size() > 0 && wordlist.size() < letters.size() ) {
			return new GuessWord(wordlist.remove(0));
		}
		
		return new GuessLetter(letters.remove(0));
	}

	// stolen from SO:
	// http://stackoverflow.com/questions/109383/how-to-sort-a-mapkey-value-on-the-values-in-java
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
