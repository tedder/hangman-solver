package test.commoncrawl.hangman;

import static org.junit.Assert.*;


import org.commoncrawl.hangman.HangmanGame;
import org.commoncrawl.hangman.HangmanGame.Status;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class HangmanGameTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testHangmanGameIncomplete() {
		HangmanGame h = new HangmanGame("foobar", 99); // 99 problems but number of guesses isn't one
		h.guessLetter('f');
		h.guessLetter('o');
		assertEquals(h.gameStatus(), Status.KEEP_GUESSING);
	}

	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Test
	public void testHangmanGameOutOfGuesses() {
		int guesses = 3;
		HangmanGame h = new HangmanGame("z", guesses);
		h.guessLetter('a');
		h.guessLetter('b');
		h.guessLetter('c');
		h.guessLetter('d'); // fails quietly here, need to check state to see we're through.
		assertEquals(h.gameStatus(), Status.GAME_LOST);
		
		thrown.expect(IllegalStateException.class);
		thrown.expectMessage("Cannot keep guessing in current game state: GAME_LOST");
		h.guessLetter('e');

		assertEquals(h.gameStatus(), Status.GAME_LOST);
	}
	

	@Test
	public void testHangmanGameRepeatedLetter() {
		int guesses = 5;
		HangmanGame h = new HangmanGame("foobar", guesses);
		// this is a lot of work and not terribly elegant, but I wanted to vary the number of guesses.
		for (int i = 1; i < guesses + 1; ++i) {
			h.guessLetter('x');
		}
		
		assertEquals(h.gameStatus(), Status.KEEP_GUESSING);
		assertEquals(h.getIncorrectlyGuessedLetters().size(), 1);
	}

	@Test
	public void testHangmanGameSimple() {
		HangmanGame h = new HangmanGame("foo", 99); // 99 problems but number of guesses isn't one
		h.guessLetter('f');
		h.guessLetter('o');
		assertEquals(h.gameStatus(), Status.GAME_WON);
	}

}
