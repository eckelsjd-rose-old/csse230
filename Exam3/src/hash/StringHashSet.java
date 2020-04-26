package hash;

import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 * 
 * A hash set implementation for Strings. Cannot insert null into the set. Other
 * requirements are given with each method.
 */
public class StringHashSet {

	// The initial size of the internal array.
	private static final int INITIAL_CAPACITY = 4;
	
	private int numElements;
	private Node[] table;

	/**
	 * Creates a Hash Set with the default capacity.
	 */
	public StringHashSet() {
		// Recall that using this as a method calls another constructor
		this(INITIAL_CAPACITY);
	}

	/**
	 * Creates a Hash Set with the given capacity.
	 */
	public StringHashSet(int initialCapacity) {
		table = new Node[initialCapacity];
		numElements = 0;
	}
	
	public double loadFactor() {
		double n = numElements;
		double m = this.table.length;
		return n / m;
	}

	public double averageLengthOfNonemptyChains() {
		double totalLength = 0;
		double numChains = 0;
		for (int i = 0; i < this.table.length; i++) {
			// slot is occupied
			if (this.table[i] != null) {
				numChains++;
				Node current = this.table[i];
				// traverse linked list and count
				while (current != null) {
					totalLength++;
					current = current.next;
				}
			}
		}
		return totalLength / numChains;
	}

	public int worstCaseNumComparisonsInSearch() {
		int maxChainLength = 0;
		for (int i = 0; i < this.table.length; i++) {
			// slot is occupied
			if (this.table[i] != null) {
				Node current = this.table[i];
				int chainLength = 0;
				// traverse linked list and count length of chain
				while (current != null) {
					chainLength++;
					current = current.next;
				}
				if (chainLength > maxChainLength) {
					maxChainLength = chainLength;
				}
			}
		}
		return maxChainLength;
	}
	
	private int getIndex(int hashCode, int capacity) {;
		if (hashCode < 0) {
			hashCode += Integer.MAX_VALUE + 1;
		}
		return hashCode % capacity;
	}
	
	
	/**
	 * Adds a new node if it is not there already. If there is a collision, then
	 * add a new node to the -front- of the linked list.
	 *
	 * If the number of nodes in the hash table would be over double the
	 * capacity (that is, lambda > 2) as a result of adding this item, then
	 * first double the capacity and then rehash all the current items into the
	 * double-size table.
	 *
	 * @param item
	 * @return true if the item was successfully added (that is, if that hash
	 *         table was modified as a result of this call), false otherwise.
	 */
	public boolean add(String item) {
		if (contains(item)) {
			return false;
		}

		if ((numElements + 1) > 2 * table.length) {
			// Rehash since lambda > 2
			int newCapacity = table.length * 2;
			Node[] newArray = new Node[newCapacity];
			Iterator<String> iter = iterator();
			String s;
			while (iter.hasNext()) {
				s = iter.next();
				int index = getIndex(s.hashCode(),newCapacity);
				newArray[index] = new Node(s, newArray[index]);
			}
			table = newArray;
		}

		int index = getIndex(item.hashCode(),table.length);
		table[index] = new Node(item, table[index]);
		numElements++;
		return true;
	}


	/**
	 * 
	 * Checks if the given item is in the hash table.
	 *
	 * @param item
	 * @return True if and only if the item is in the hash table.
	 */
	public boolean contains(String item) {
		int index = getIndex(item.hashCode(),table.length);
		Node current = table[index];
		while (current != null) {
			if (current.data.equals(item)) {
				return true;
			}
			current = current.next;
		}
		return false;
	}

	/**
	 * Returns the number of items added to the hash table. Must operate in O(1)
	 * time.
	 *
	 * @return The number of items in the hash table.
	 */
	public int size() {
		return numElements;
	}

	/**
	 * @return True iff the hash table contains no items.
	 */
	public boolean isEmpty() {
		return size() == 0;
	}
	
	/**
	 * Prints an array value on each line. Each line will be an array index
	 * followed by a colon and a list of Node data values, ending in null. For
	 * example, inserting the strings in the testAddSmallCollisions example
	 * gives "0: shalom hola null 1 bonjour null 2 caio hello null 3 null 4 hi
	 * null". Use a StringBuilder, so you can build the string in O(n) time.
	 * (Repeatedly concatenating n strings onto a growing string gives O(n^2)
	 * time)
	 * 
	 * @return A slightly-formatted string, mostly used for debugging
	 */
	public String toRawString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < table.length; i++) {
			sb.append(String.format("%d: %s\n", i, nodeString(table[i])));
		}

		return sb.toString();
	}

	private String nodeString(Node n) {
		if (n == null) {
			return "";
		}
		return n.data + " " + nodeString(n.next);
	}

	private class Node {
		private String data;
		Node next;

		public Node(String item, Node next) {
			this.data = item;
			this.next = next;
		}

	}


	/**
	 * 
	 * Returns an iterator over the set.
	 *
	 * @return
	 */
	public Iterator<String> iterator() {
		return new UnsortedIterator();
	}

	public class UnsortedIterator implements Iterator<String> {
		Node mCurrentNode;
		int mNumReturned;
		int mCurrentIndex;
		
		public UnsortedIterator() {
			mNumReturned = 0;
			mCurrentIndex = 0;
			mCurrentNode = table[mCurrentIndex];
		}

		@Override
		public boolean hasNext() {
			return mNumReturned < size();
		}

		private void advanceUntilFind() {
			while (mCurrentNode == null) {
				mCurrentIndex++;
				mCurrentNode = table[mCurrentIndex];
			}
		}

		@Override
		public String next() {
			
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			// Find the next one
			advanceUntilFind();
			// Get its data
			String toReturn = mCurrentNode.data;
			mNumReturned++;

			// Start prep for next node
			mCurrentNode = mCurrentNode.next;
			return toReturn;
		}
	}

	@Override
	public String toString() {
		Iterator<String> iter = iterator();
		StringBuilder sb = new StringBuilder();
		while (iter.hasNext()) {
			sb.append(iter.next() + ", ");
		}
		return "[" + sb.toString().substring(0, sb.length() - 2) + "]";
	}


}
