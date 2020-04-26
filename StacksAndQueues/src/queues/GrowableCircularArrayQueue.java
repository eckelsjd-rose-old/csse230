package queues;

import java.lang.reflect.Array;
import java.util.NoSuchElementException;

/**
 * A circular queue that grows as needed.
 * 
 * @author Natalie Koenig and Joshua Eckels date: 9/14/2019
 * @param <T>
 */
public class GrowableCircularArrayQueue<T> implements SimpleQueue<T> {
	// GrowableCircularArrayQueue<T> implements the interface SimpleQueue<T> and
	// it's methods

	// DONE: The javadoc for overridden methods is in the SimpleQueue interface.
	// Read it!

	private static final int INITIAL_CAPACITY = 5;

	private T[] array;
	private int start;
	private int end;
	private Class<T> type;

	/**
	 * Creates an empty queue with an initial capacity of 5
	 * 
	 * @param type So that an array of this type can be constructed.
	 */
	@SuppressWarnings("unchecked")
	public GrowableCircularArrayQueue(Class<T> type) {
		this.type = type;
		// This is a workaround due to a limitation Java has with
		// constructing generic arrays.
		this.array = (T[]) Array.newInstance(this.type, INITIAL_CAPACITY);
		this.start = 0;
		this.end = 0;
	}

	/**
	 * Removes all the items from this queue and everything back to the initial
	 * state, including setting its capacity back to the initial capacity.
	 */
	@Override
	public void clear() {
		this.start = 0;
		this.end = 0;
		this.array = (T[]) Array.newInstance(this.type, INITIAL_CAPACITY);
	}

	/**
	 * Adds the given item to the tail of the queue, resizing the internal array if
	 * needed.
	 * 
	 * @param item
	 */
	@Override
	public void enqueue(T item) {
		if (this.isEmpty()) {
			array[0] = item;
			start = 0;
			end = 0;
		} else {
			//resize if full, if it is resized we know it is ordered start -> end with extra room
			if (this.isFull()) {
				this.resize();
			}
			
			if(end == array.length - 1) {
				end = 0;
				array[end] = item;
			}
			else {
				array[end + 1] = item;
				end++;
			}
		}
	}

	/**
	 * Removes and returns the item at the head of the queue.
	 * 
	 * @return The item at the head of the queue.
	 * @throws NoSuchElementException If the queue is empty.
	 */
	@Override
	public T dequeue() throws NoSuchElementException {
		
		if(this.isEmpty()) {
			throw new NoSuchElementException();
		}
		
		T head = array[start];
		array[start] = null;
		
		if (start == array.length - 1) {
			start = 0;
		} else {
			start++;
		}
		
		return head;
	}

	/**
	 * Checks if array is at full capacity
	 * 
	 * @return boolean of whether array is full
	 */
	public boolean isFull() {
		if (this.size() == array.length) {
			return true;
		}

		return false;
	}

	/**
	 * Resizes the array by multiplying the capacity by 2
	 * 
	 */
	public T[] resize() {
		T[] resizedArray = (T[]) Array.newInstance(this.type, this.array.length * 2);
		int index = 0;

		if (end < start) {
			for (int i = start; i < array.length; i++) {
				resizedArray[index] = array[i];
				index++;
			}
			for (int j = 0; j <= end; j++) {
				resizedArray[index] = array[j];
				index++;
			}

		} else {
			for (int i = start; i <= end; i++) {
				resizedArray[index] = array[i];
				index++;
			}
		}

		this.array = resizedArray;
		start = 0;
		end = this.size() - 1;
		return resizedArray;
	}

	/**
	 * Returns the item at the head of the queue without mutating the queue.
	 * 
	 * @return The item at the head of the queue.
	 * @throws NoSuchElementException If the queue is empty.
	 */
	@Override
	public T peek() throws NoSuchElementException {
		if(this.isEmpty()) {
			throw new NoSuchElementException();
		}
		
		return array[start];
	}

	/**
	 * @return True if and only if the queue contains no items.
	 */
	@Override
	public boolean isEmpty() {
		for (int i = 0; i < array.length; i++) {
			if (array[i] != null) {
				return false;
			}
		}

		return true;
	}

	/**
	 * @return The number of items in this queue.
	 */
	@Override
	public int size() {
		int size = 0;
		for (int i = 0; i < array.length; i++) {
			if (array[i] != null) {
				size++;
			}
		}
		return size;
	}

	/**
	 * Searches for the given item in this queue.
	 * 
	 * @param item
	 * @return True if the item is contained within the queue.
	 */
	@Override
	public boolean contains(T item) {
		for (int i = 0; i < array.length; i++) {
			if (array[i] != null && array[i].equals(item)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Displays the contents of the queue in insertion order, with the most-recently
	 * inserted one last, in other words, not wrapped around. Each adjacent pair
	 * will be separated by a comma and a space, and the whole contents will be
	 * bounded by square brackets. See the unit tests for examples.
	 */
	@Override
	public String toString() {
		if (this.isEmpty()) {
			return "[]";
		}

		String queue = "[";
		if (end < start) {
			for (int i = start; i < array.length; i++) {
				if (array[i] != null) {
					queue += array[i] + ", ";
				}
			}
			for (int j = 0; j <= end; j++) {
				if (array[j] != null) {
					if (j == end) {
						queue += array[j];
					} else {
						queue += array[j] + ", ";
					}
				}
			}
		} else {
			for (int i = start; i <= end; i++) {
				if (array[i] != null) {
					if (i == end) {
						queue += array[i];
					} else {
						queue += array[i] + ", ";
					}
				}
			}
		}
		queue += "]";
		return queue;
	}

	/**
	 * Displays the contents of the queue in a raw format that will depend on the
	 * implementation. This is for your debugging purposes.
	 */
	@Override
	public String debugString() {
		String print = "[";
		for (int i = 0; i < array.length; i++) {
			if (array[i] != null) {
				print += array[i] + ", ";
			}
		}
		print += "]";

		return print;
	}

}
