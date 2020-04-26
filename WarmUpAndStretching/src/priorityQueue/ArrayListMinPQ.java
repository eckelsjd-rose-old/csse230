package priorityQueue;
import java.util.ArrayList;

/**
 * An implementation of the Priority Queue interface using an array list.
 * 
 * @author Matt Boutell and <<Joshua Eckels>>>. Created Mar 29, 2014.
 * 
 * @param <T>
 *            Generic type of PQ elements
 */
public class ArrayListMinPQ<T extends Comparable<T>> {
	// Requirement: You must use this instance variable without changes.
	private ArrayList<T> items;

	public ArrayListMinPQ() {
		// DONE: implement
		this.items = new ArrayList<>();
	}

	public T findMin() {
		// This is also known as peekMin
		// DONE: implement
		// empty case
		if (this.items.size() == 0) { return null; }
		return this.items.get(this.items.size() - 1);
	}

	public T deleteMin() {
		// DONE: implement
		// empty case
		if (this.items.size() == 0) { return null; }
		return this.items.remove(this.items.size() - 1);
	}

	public void insert(T item) {
		// DONE: implement
		// handle 0 items case
		if (this.items.size() == 0) { this.items.add(item); return; }
		
		// handle general case
		int i = this.items.size() - 1;
		while (i > -1) {
			if (item.compareTo(this.items.get(i)) <= 0) {
				// handle lowest priority case
				if (i == this.items.size() - 1) {
					this.items.add(item);
					return;
				}
				this.items.add(i+1, item);
				return;
			}
			i--;
		}
		// place at front of queue (highest priority)
		this.items.add(0, item);
		return;
	}

	public int size() {
		return this.items.size();
	}

	public boolean isEmpty() {
		return this.items.isEmpty();
	}

	public void clear() {
		this.items.clear();
	}
}
