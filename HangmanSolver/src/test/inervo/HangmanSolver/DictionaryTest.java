package test.inervo.HangmanSolver;

import static org.junit.Assert.*;

import java.util.List;

import net.inervo.hangman.Dictionary;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class DictionaryTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testGetDictionaryStream() {
		List<String> d = new Dictionary().getDictionaryAsList();
		assertNotNull(d.get(0));
		assertNotNull(d.get(d.size() - 10));
	}

}
