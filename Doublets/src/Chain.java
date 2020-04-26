import java.util.Iterator;
import java.util.LinkedHashSet;

/**
 * 
 * A class that represents an immutable string of words that are each different
 * by exactly one letter from word to word.
 *
 * @author Joshua Eckels and Tom Meehan
 *         Created Sep 24, 2019.
 */
public class Chain implements Iterable<String> {

	private LinkedHashSet<String> hs;
	private String last;
	
	public Chain() {
		this.hs = new LinkedHashSet<>();
		this.last = null;
	}
	
	/**
	 * 
	 * Constructor used when addLast is called. Constructs a new chain out of
	 * lhs and adds newString to it.
	 *
	 * @param lhs
	 * @param newString
	 * @throws Exception
	 */
	//throws exception if the string being added to the chain is already in the chain
	private Chain(LinkedHashSet<String> lhs, String newString) throws Exception {
		LinkedHashSet<String> newSet = new LinkedHashSet<>();
		Iterator<String> itr = lhs.iterator();
		while(itr.hasNext()) {
			String next = itr.next();
			if(!newString.equals(next)) {
				newSet.add(next);
			} else {
				throw new Exception();
			}
		}
		newSet.add(newString);
		this.last = newString;
		this.hs = newSet;
	}
	
	/**
	 * 
	 * Method to get the size of the LinkedHashSet field "hs"
	 *
	 * @return length of chain
	 */
	public int length() {
		return this.hs.size();
	}
	
	public boolean contains(String s) {
		return this.hs.contains(s);
	}
	
	/**
	 * 
	 * Takes a string "s" and returns a new chain with "s" added to the end.
	 * Catches Exception from constructor if string was already in the chain.
	 *
	 * @param s
	 * @return
	 */
	public Chain addLast(String s) {
		try {
			Chain newChain = new Chain(this.hs, s);
			return newChain;
		}
		// don't return new chain if same string found
		catch (Exception e) {
			return null;
		}
	}

	// returns last string in the chain
	public String getLast() {
		return last;
	}

	// used to iterate over the chain
	@Override
	public Iterator<String> iterator() {
		Iterator<String> itr = hs.iterator();
		return itr;
	}

	public String toString() {
		return this.hs.toString();
	}
}
