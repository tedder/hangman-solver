package net.inervo.HangmanSolver;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class Dictionary {
	private List<String> dict = null;

	public InputStream getDictionaryAsStream() {
		return this.getClass().getResourceAsStream("words.txt");
	}

	public List<String> getDictionaryAsList() {
		// cache it.
		if (dict != null)
			return dict;

		try {
			dict = IOUtils.readLines(getDictionaryAsStream());
		} catch (IOException e) {
			// silently fail (for now)
		}

		return dict;
	}

	public String getRandomEntry() {
		getDictionaryAsList();
		int rand = (int) Math.round(Math.random() * (dict.size() - 1));
		String word = dict.get(rand);
		return word;
	}
}
