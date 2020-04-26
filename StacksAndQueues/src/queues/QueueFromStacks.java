package queues;

import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * 
 * A class that makes a queue data structure from two stacks.
 *
 * @author eckelsjd and koenigm
 *         Created Sep 18, 2019.
 * @param <T>
 */
public class QueueFromStacks<T> implements SimpleQueue<T> {
	
	private Stack<T> in;
	private Stack<T> out;

	
	/**
	 * Creates an empty queue using 2 stacks. The final queue is always stored
	 * in the "in" stack. The "out" stack is used as a temporary data holder.
	 * 
	 * @param None
	 */
	@SuppressWarnings("unchecked")
	public QueueFromStacks() {
		in = new Stack<>();
		out = new Stack<>();
	}
	
	/*
	 * Clears the queue
	 */
	@Override
	public void clear() {
		in.clear();
	}

	/*
	 * Adds a new item to the end of the queue
	 */
	@Override
	public void enqueue(T item) {
		in.push(item);
	}

	/*
	 * Removes and returns the first item from the queue
	 */
	@Override
	public T dequeue() throws NoSuchElementException {
		if(in.isEmpty()) {
			throw new NoSuchElementException();
		}
		inToOutBox();
		T temp = out.pop();
		outToInBox();
		return temp;
	}

	/*
	 * Returns the top of the queue without removing it
	 */
	@Override
	public T peek() throws NoSuchElementException {
		if(in.isEmpty()) {
			throw new NoSuchElementException();
		}
		inToOutBox();
		T temp = out.peek();
		outToInBox();
		return temp;
	}

	/*
	 * Returns true if the queue is empty; else false
	 */
	@Override
	public boolean isEmpty() {
		return in.empty();
	}

	/*
	 * Returns the size of the queue
	 */
	@Override
	public int size() {
		if(in.isEmpty()) {
			return 0;
		}
		int size = 0;
		while (!in.empty()) {
			T temp = in.pop();
			out.push(temp);
			size++;
		}
		outToInBox();
		return size;
	}

	@Override
	public boolean contains(T item) {
		if(in.isEmpty()) {
			return false;
		}
		
		while (!in.empty()) {
			T temp = in.pop();
			out.push(temp);
			if (temp.equals(item)) {
				outToInBox();
				return true;
			}
		}
		outToInBox();
		return false;
	}

	@Override
	public String debugString() {
		String queue = "[";
		if (in.empty()) {
			return "[]";
		}
		while (!in.empty()) {
			T temp = in.pop();
			if (in.size() == 0) {
				queue += temp.toString() + "]";
			} else {
				queue += temp.toString() + ", ";
			}
			out.push(temp); // put item back on "out" stack
		}
		outToInBox(); // return everything as it was to "in" stack
		return queue;
	}
	
	@Override
	public String toString() {
		String queue = "[";
		if (in.empty()) {
			return "[]";
		}
		inToOutBox();
		while (!out.empty()) {
			T temp = out.pop();
			if (out.size() == 0) {
				queue += temp.toString() + "]";
			} else {
				queue += temp.toString() + ", ";
			}
			in.push(temp); // put item back on "in" stack
		}
		return queue;
	}
	
	/*
	 * Takes all items from "out" Stack and moves over to "in" Stack
	 */
	public void outToInBox() {
		while (!out.empty()) {
			T temp = out.pop();
			in.push(temp);
		}
	}
	
	/*
	 * Takes all items from "in" Stack and moves over to "out" Stack
	 */
	public void inToOutBox() {
		while (!in.empty()) {
			T temp = in.pop();
			out.push(temp);
		}
	}


}
