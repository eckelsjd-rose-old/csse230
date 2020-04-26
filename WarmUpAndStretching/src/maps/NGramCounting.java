package maps;

import java.util.HashMap;
import java.util.Map;

/**
 * Practice in using maps.
 * 
 * @author Nate Chenette
 *
 */

public class NGramCounting {

	/**
	 * Given an input text and a length n, the method should produce a Map from n-grams of 
	 * the text (i.e., length-n substrings) to counts, where n-gram S is mapped to count C
	 * if S shows up C times among substrings of the text. 
	 * 
	 * This method would be useful in frequency-based cryptanalysis of the classic substitution 
	 * cipher.
	 * 
	 * @param text
	 * @param n, the length of the n-grams to count
	 * @return
	 */
	static Map<String,Integer> nGramCounter(String text, int n) {
		//TODO: write this method!
		HashMap<String,Integer> map = new HashMap<>();
		for (int i = 0; i < text.length()-n+1; i++){
			String gram = text.substring(i, i+n);
			
			if (map.containsKey(gram)) {
				map.put(gram, map.get(gram)+1);
			} else {
				map.put(gram, 1);
			}
		}
		return map;
	}

}