package org.commoncrawl.hangman;

/**
 * A strategy for generating guesses given the current state of a Hangman game.
 */
public interface GuessingStrategy {
  Guess nextGuess(HangmanGame game);
}
