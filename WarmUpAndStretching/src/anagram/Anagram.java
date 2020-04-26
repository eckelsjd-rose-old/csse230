package anagram;


/** 
 * This utility class can test whether two strings are anagrams.
 *
 * @author Claude Anderson. 
 */
public class Anagram {

	/**
	 * We say that two strings are anagrams if one can be transformed into the
	 * other by permuting the characters (and ignoring case).
	 * 
	 * For example, "Data Structure" and "Saturated Curt" are anagrams,
	 * as are "Elvis" and "Lives".
	 * 
	 * @param s1 
	 *            first string
	 * @param s2
	 *            second string
	 * @return true iff s1 is an anagram of s2
	 */
	public static boolean isAnagram(String s1, String s2) {
		if (s1.equals("") && s2.equals("")) {
			return true;
		}
		if ((s1.equals("") && !s2.equals("")) || (!s1.equals("") && s2.equals(""))) {
			return false;
		}
		String s1_lower = s1.toLowerCase();
		String s2_lower = s2.toLowerCase();
		char c = s1_lower.charAt(0);
		int index = s2_lower.indexOf(c);
	
		if (index >= 0) {
			String s2_new = s2_lower.substring(0, index) + s2_lower.substring(index+1);
			return isAnagram(s1_lower.substring(1), s2_new);
		}
		return false;
	}
}
