import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * 
 * A class to create and store a hashmap of all strings in a dictionary with their
 * potential candidates for solving the Doublets puzzle.
 *
 * @author Joshua Eckels and Tom Meehan
 *         Created Sep 24, 2019.
 */
public class Links implements LinksInterface {
	
	private HashMap<String, Set<String>> linkmap;

	public Links(String filename) {
		this.linkmap = new HashMap<>();
		try {
			
			// open and read a text file
			File file = new File(filename);
			Scanner sc = new Scanner(file);
			
			// initializes links map
			while (sc.hasNext()) {
				String word = sc.next();
				if (linkmap.containsKey(word)) {
					// shouldn't find duplicate words in a dictionary
					// do nothing
				} else {
					linkmap.put(word, new HashSet<String>());
					for (String key : linkmap.keySet()) {
						if (compareString(word, key)) {
							linkmap.get(word).add(key);
							linkmap.get(key).add(word);
						}
					}
				}	
			}
			
		} catch (FileNotFoundException fe) {
			System.out.println(fe);
		}
		
	}

	/**
	 * 
	 * Checks two input strings and returns true if they are different
	 * by exactly one character
	 *
	 * @param word
	 * @param key
	 * @return true on condition above; else, false
	 */
	private static boolean compareString(String word, String key) {
		if (word.length() != key.length()) {
			return false;
		}
		
		// count number of unalike characters
		int count = 0;
		for (int i = 0; i < word.length(); i++) {
			if (word.charAt(i) != key.charAt(i)) {
				count++;
			}
		}
		
		// we only want the case where exactly one character was unalike
		return (count == 1);
	}

	/*
	 * Returns all possible candidates of the input string "word"
	 */
	@Override
	public Set<String> getCandidates(String word) {
		if (!exists(word)) { return null; }
		if (linkmap.get(word).isEmpty()) {return null; }
		return linkmap.get(word);
	}

	/*
	 * Checks the links map if it contains the input string "word"
	 */
	@Override
	public boolean exists(String word) {
		return linkmap.containsKey(word);
	}

}
