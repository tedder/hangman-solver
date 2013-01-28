package net.inervo.hangman;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

import org.commoncrawl.hangman.HangmanGame;

public class Gamemaster implements Iterator<HangmanGame> {
	Deque<String> games = null;
	int maxGuesses;

	public Gamemaster() {
		this(5, 10);
	}

	public Gamemaster(int maxGuesses, int gamesToRun) {
		this.maxGuesses = maxGuesses;

		games = new ArrayDeque<String>();

		// create a list of games. For now, this is a static list. Later we'll read the dict.
		Dictionary dict = new Dictionary();
		for (int i = 0; i < gamesToRun; ++i) {
			games.add(dict.getRandomEntry());
		}
	}

	@Override
	public boolean hasNext() {
		return !games.isEmpty();
	}

	@Override
	public HangmanGame next() {
		return new HangmanGame(games.pop(), maxGuesses);
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException("nope");
	}

}
