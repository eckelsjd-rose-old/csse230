package graph;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * Abstract class to represent the Graph ADT. It is assumed that every vertex
 * contains some data of type T, which serves as the identity of that node and
 * provides access to it.
 * 
 * @param <T>
 */
public abstract class Graph<T> {

	public Iterator<T> bfsIterator(T start) {
		// Alternative: make this method abstract,
		// and instead implement inside the AdjList and AdjMatrix
		// classes.
		return new bfsIterator(start);
	}

	/**
	 * 
	 * Inner class: Breadth-first Iterator of graph
	 *
	 * @author eckelsjd.
	 *         Created Nov 20, 2019.
	 */
	private class bfsIterator implements Iterator<T> {

		LinkedList<T> queue;
		HashSet<T> visited; //don't repeat already visited nodes
		HashSet<T> queued; // don't repeat already queued nodes

		bfsIterator(T start) {
			this.queue = new LinkedList<>();
			this.visited = new HashSet<>(); // 'node has been visited' set
			this.queued = new HashSet<>(); // 'has been queued' set
			queue.add(start);
		}

		@Override
		public boolean hasNext() {
			return !this.queue.isEmpty();
		}

		@Override
		public T next() {
			T current = queue.remove();
			visited.add(current);
			Iterator<T> iter = Graph.this.successorIterator(current);
			// add all successors to the queue
			while(iter.hasNext()) {
				T next = iter.next();
				if (!visited.contains(next) && !queued.contains(next)) {
					queued.add(next);
					queue.add(next);
				}
			}
			return current;
		}
	}

	/* Selected GraphSurfing Milestone 1 Methods */

	/**
	 * Adds a directed edge from the vertex containing "from" to the vertex
	 * containing "to".
	 * 
	 * @param from
	 * @param to
	 * @return true if the add is successful, false if the edge is already in the
	 *         graph.
	 * @throws NoSuchElementException if either key is not found in the graph
	 */
	public abstract boolean addEdge(T from, T to) throws NoSuchElementException;

	/**
	 * Determines whether a vertex is in the graph.
	 * 
	 * @param key
	 * @return true if the graph has a vertex containing key.
	 */
	public abstract boolean hasVertex(T key);

	/**
	 * Determines whether an edge is in the graph.
	 * 
	 * @param from
	 * @param to
	 * @return true if the dire)cted edge (from, to) is in the graph, otherwise
	 *         false.
	 * @throws NoSuchElementException if either key is not found in the graph
	 */
	public abstract boolean hasEdge(T from, T to) throws NoSuchElementException;

	/**
	 * Returns the Set of vertex keys in the graph.
	 * 
	 * @return
	 */
	public abstract Set<T> keySet();

	/**
	 * Returns a Set of keys that are successors of the given key.
	 * 
	 * @param key
	 * @return
	 * @throws NoSuchElementException if the key is not found in the graph
	 */
	public Set<T> successorSet(T key) throws NoSuchElementException {
		if (!this.hasVertex(key)) {
			throw new NoSuchElementException("Could not find vertex containing " + key.toString());
		}
		Iterator<T> succIt = this.successorIterator(key);
		Set<T> toReturn = new HashSet<T>();
		while (succIt.hasNext()) {
			toReturn.add(succIt.next());
		}
		return toReturn;
	}

	/**
	 * Returns a Set of keys that are predecessors of the given key.
	 * 
	 * @param key
	 * @return
	 * @throws NoSuchElementException if the key is not found in the graph
	 */
	public Set<T> predecessorSet(T key) throws NoSuchElementException {
		if (!this.hasVertex(key)) {
			throw new NoSuchElementException("Could not find vertex containing " + key.toString());
		}
		Iterator<T> predIt = this.predecessorIterator(key);
		Set<T> toReturn = new HashSet<T>();
		while (predIt.hasNext()) {
			toReturn.add(predIt.next());
		}
		return toReturn;
	}

	/**
	 * Returns an Iterator that traverses the keys who are successors of the given
	 * key.
	 * 
	 * @param key
	 * @return
	 * @throws NoSuchElementException if the key is not found in the graph
	 */
	public abstract Iterator<T> successorIterator(T key) throws NoSuchElementException;

	/**
	 * Returns an Iterator that traverses the keys who are successors of the given
	 * key.
	 * 
	 * @param key
	 * @return
	 * @throws NoSuchElementException if the key is not found in the graph
	 */
	public abstract Iterator<T> predecessorIterator(T key) throws NoSuchElementException;
}
