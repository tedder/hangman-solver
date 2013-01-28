package net.inervo.hangman;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

public class SolutionStatistics {
	HashMap<String, List<Integer>> statball = null;

	public SolutionStatistics() {
		statball = new HashMap<String, List<Integer>>();
	}

	public void addStat(String label, int value) {
		List<Integer> list = null;
		if (statball.containsKey(label)) {
			list = statball.get(label);
		} else {
			list = new ArrayList<Integer>();
			statball.put(label, list);
		}

		list.add(value);
	}

	private double getAverage(List<Integer> list) {
		// this is private because it's just a quick calculation and we aren't worried about edge cases, it's
		// just a simple utility.
		if (list == null || list.size() == 0) {
			return 0;
		}
		double sum = 0.0;
		for (Integer i : list) {
			sum += (double) i;
		}

		return sum / list.size();
	}

	private double getProbabilityWithinNPicks(List<Integer> list, int picks) {
		// this is private because it's just a quick calculation and we aren't worried about edge cases, it's
		// just a simple utility.
		if (list == null || list.size() == 0) {
			return 0;
		}

		int count = 0;
		for (Integer i : list) {
			if (i < picks)
				++count;
		}
		return ((double) count) / list.size();
	}

	public String getStats() {
		// just a little formatting trick.
		int classlength = getMaxKey(statball.keySet()) + 4;
		
		StringBuilder sb = new StringBuilder();
		for (Entry<String, List<Integer>> entry : statball.entrySet()) {
			sb.append(String.format("%-" + classlength + "s  games=%-5d avg_guesses=%09.6f prob_below_5=%7.6f\n", entry.getKey(), entry
					.getValue().size(), getAverage(entry.getValue()), getProbabilityWithinNPicks(entry.getValue(), 5)));
		}

		return sb.toString();
	}

	private int getMaxKey(Set<String> set) {
		int max = 0;
		for (String entry : set) {
			if (entry.length() > max) { max = entry.length(); }
		}
		
		return max;
	}
}
