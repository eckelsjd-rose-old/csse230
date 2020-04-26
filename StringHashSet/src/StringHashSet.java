import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 
 * A hash set implementation for Strings. Cannot insert null into the set. Other
 * requirements are given with each method.
 *
 * @author Matt Boutell and Joshua Eckels. Created Oct 6, 2014.
 */
public class StringHashSet {

	// The initial size of the internal array.
	private static final int DEFAULT_CAPACITY = 5;
	private int capacity;
	private int size;
	private int modifyCount;
	private Node[] table;

	// You'll want fields for the size (number of elements) and the internal
	// array of Nodes. I also added one for the capacity (the length
	// of the internal array).

	private class Node {
		private String data;
		private Node next;

		Node(String s) {
			this.data = s;
			this.next = null;
		}
	}

	/**
	 * Creates a Hash Set with the default capacity.
	 */
	public StringHashSet() {
		// Recall that using this as a method calls another constructor
		this(DEFAULT_CAPACITY);
	}

	/**
	 * Creates a Hash Set with the given capacity.
	 */
	public StringHashSet(int initialCapacity) {
		initialize(initialCapacity);
	}

	private void initialize(int initialCapacity) {
		// Why did we pull this out into a separate method? Perhaps another
		// method needs to initialize the hash set as well? (Hint)
		this.table = new Node[initialCapacity];
		this.modifyCount = 0;
		this.size = 0;
	}

	/**
	 * Calculates the hash code for Strings, using the x=31*x + y pattern. Follow
	 * the specification in the String.hashCode() method in the Java API. Note that
	 * the hashcode can overflow the max integer, so it can be negative. Later, in
	 * another method, you'll need to account for a negative hashcode by adding
	 * Integer.MAX_VALUE + 1 before you mod by the capacity (table size) to get the
	 * index.
	 *
	 * This method is NOT the place to calculate the index though.
	 *
	 * @param item
	 * @return The hash code for this String
	 */
	public static int stringHashCode(String item) {
		int val = 0;
		for (int i = 0; i < item.length(); i++) {
			val = val * 31 + item.charAt(i);
		}
		return val;
	}

	/**
	 * Adds a new node if it is not there already. If there is a collision, then add
	 * a new node to the -front- of the linked list.
	 * 
	 * Must operate in amortized O(1) time, assuming a good hashcode function.
	 *
	 * If the number of nodes in the hash table would be over double the capacity
	 * (that is, lambda > 2) as a result of adding this item, then first double the
	 * capacity and then rehash all the current items into the double-size table.
	 *
	 * @param item
	 * @return true if the item was successfully added (that is, if that hash table
	 *         was modified as a result of this call), false otherwise.
	 */
	public boolean add(String item) {
		// check duplicate
		if (this.contains(item)) {return false; }
		
		// rehash and resize
		if (this.size + 1 > 2*this.table.length) {
			this.resize();
		}

		int idx = this.hash(item);
		
		// add to empty slot
		if (this.table[idx] == null) {
			this.table[idx] = new Node(item);
		}

		// add to occupied slot
		else {
			Node newNode = new Node(item);
			newNode.next = this.table[idx];
			this.table[idx] = newNode;
		}
		this.size++;
		this.modifyCount++;
		return true;
	}

	// computes index in table
	public int hash(String item) {
		int hash = stringHashCode(item);
		if (hash < 0) {
			hash += Integer.MAX_VALUE + 1;
		}
		return hash % this.table.length;
	}
	
	// doubles size of table and rehashes
	public void resize() {
		Node[] tempArray = this.table;
		this.initialize(this.table.length * 2);
		for (int i = 0; i < tempArray.length; i++) {
			// slot is occupied
			if (tempArray[i] != null) {
				Node current = tempArray[i];
				// traverse linked list
				while (current != null) {
					this.add(current.data);
					current = current.next;
				}
			}
		}
	}

	/**
	 * Prints an array value on each line. Each line will be an array index followed
	 * by a colon and a list of Node data values, ending in null. For example,
	 * inserting the strings in the testAddSmallCollisions example gives "0: shalom
	 * hola null 1 bonjour null 2 caio hello null 3 null 4 hi null". Use a
	 * StringBuilder, so you can build the string in O(n) time. (Repeatedly
	 * concatenating n strings onto a growing string gives O(n^2) time)
	 * 
	 * @return A slightly-formatted string, mostly used for debugging
	 */
	public String toRawString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < this.table.length; i++) {
			sb.append(i + ": ");
			Node current = this.table[i];
			while (current != null) {
				sb.append(current.data + " ");
				current = current.next;
			}
			sb.append("null\n");
		}
		//sb.deleteCharAt(sb.length() - 1); // trim whitespace
		
		return sb.toString();
	}

	/**
	 * 
	 * Checks if the given item is in the hash table.
	 * 
	 * Must operate in O(1) time, assuming a good hashcode function.
	 *
	 * @param item
	 * @return True if and only if the item is in the hash table.
	 */
	public boolean contains(String item) {
		Node current = this.table[this.hash(item)];
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
		return this.size;
	}

	/**
	 * @return True iff the hash table contains no items.
	 */
	public boolean isEmpty() {
		return (this.size == 0);
	}

	/**
	 * Removes all the items from the hash table. Resets the capacity to the
	 * DEFAULT_CAPACITY
	 */
	public void clear() {
		this.initialize(DEFAULT_CAPACITY);
	}

	/**
	 * Removes the given item from the hash table if it is there. You do NOT need to
	 * resize down if the load factor decreases below the threshold.
	 * 
	 * @param item
	 * @return True If the item was in the hash table (or equivalently, if the table
	 *         changed as a result).
	 */
	public boolean remove(String item) {
		if (this.contains(item)) {
			int idx = this.hash(item);
			this.size--;
			this.modifyCount++;
			Node current = this.table[idx];
			while (current.next != null) {
				if (current.next.data.equals(item)) {
					current.next = current.next.next;
					return true;
				}
				current = current.next;
			}
			// one node case
			this.table[idx] = null;
			return true;
		}
		return false;
	}

	/**
	 * Adds all the items from the given collection to the hash table.
	 *
	 * @param collection
	 * @return True if the hash table is modified in any way.
	 */
	public boolean addAll(Collection<String> collection) {
		boolean modified = false;
		for (String s : collection) {
			if (this.add(s)) {modified = true; }
		}
		return modified;
	}

	/**
	 * 
	 * Challenge Feature: Returns an iterator over the set. Return the items in any
	 * order that you can do efficiently. Should throw a NoSuchElementException if
	 * there are no more items and next() is called. Should throw a
	 * ConcurrentModificationException if next() is called and the set has been
	 * mutated since the iterator was created.
	 *
	 * @return an iterator.
	 */
	public Iterator<String> iterator() {
		return new TableIterator();
	}

	// Challenge Feature: If you have an iterator, this is easy. Use a
	// StringBuilder, so you can build the string in O(n) time. (Repeatedly
	// concatenating n strings onto a string gives O(n^2) time)
	// Format it like any other Collection's toString (like [a, b, c])
	@Override
	public String toString() {
		if (this.size == 0) {
			return "[]";
		}
		StringBuilder sb = new StringBuilder();
		Iterator<String> iter = this.iterator();
		sb.append("[");
		while (iter.hasNext()) {
			String s = iter.next();
			sb.append(s + ", ");
		}
		String newString = sb.substring(0, sb.length() - 2) + "]";
		return newString;
	}
	
	private class TableIterator implements Iterator<String> {

		int count;
		int idx;
		int chainDepth;
		
		int startCount; // for tracking modifications

		TableIterator() {
			this.count = 0; // track number of items visited
			this.idx = 0; // track index in table
			this.chainDepth = 0; // track depth in linked list
			this.startCount = StringHashSet.this.modifyCount; // track changes
		}

		@Override
		public boolean hasNext() {
			return this.count < StringHashSet.this.size;
		}

		@Override
		public String next() throws NoSuchElementException, ConcurrentModificationException {
			try {
				// break if table has been modified since iterator's creation
				if (this.startCount != StringHashSet.this.modifyCount) {
					throw new ConcurrentModificationException("Table has been modified.");
				}
				
				// find first non empty table index
				while (StringHashSet.this.table[this.idx] == null) {
					this.idx++;
				}
				
				// found a chain
				Node current = StringHashSet.this.table[this.idx];
				int i = 0;
				while (current != null) {
					if (i == this.chainDepth) {
						this.chainDepth++;
						this.count++;
						return current.data;
					}
					i++;
					current = current.next;
				}
				
				// move past the end of a chain
				this.chainDepth = 0;
				this.idx++;
				return this.next();
				
				
			} catch (IndexOutOfBoundsException e) {
				throw new NoSuchElementException("Nothing left to iterate over.");
			}
		}
	}
}
