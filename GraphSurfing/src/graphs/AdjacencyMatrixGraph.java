package graphs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.Stack;

public class AdjacencyMatrixGraph<T> extends Graph<T> {
	Map<T, Integer> keyToIndex;
	List<T> indexToKey;
	int[][] matrix;

	@SuppressWarnings("boxing")
	AdjacencyMatrixGraph(Set<T> keys) {
		int size = keys.size();
		this.keyToIndex = new HashMap<>();
		this.indexToKey = new ArrayList<>();
		this.matrix = new int[size][size];
		// need to populate keyToIndex and indexToKey with info from keys
		int i = 0;
		for (T key : keys) {
			this.indexToKey.add(key);
			this.keyToIndex.put(key, i);
			i++;
		}
	}

	private class MutableBool {
		boolean value;

		MutableBool() {
		}
	}

	@Override
	public int size() {
		return this.indexToKey.size();
	}

	@Override
	public int numEdges() {
		int edges = 0;
		for (int i = 0; i < this.size(); i++) {
			for (int j = 0; j < this.size(); j++) {
				edges += this.matrix[i][j];
			}
		}
		return edges;
	}

	@SuppressWarnings("boxing")
	@Override
	public boolean addEdge(T from, T to) {
		if (!this.hasVertex(from) || !this.hasVertex(to)) {
			throw new NoSuchElementException("Element not found");
		}
		if (this.hasEdge(from, to)) {
			return false;
		}
		int i = this.keyToIndex.get(from);
		int j = this.keyToIndex.get(to);
		this.matrix[i][j] = 1;
		return true;
	}

	@Override
	public boolean hasVertex(T key) {
		return this.keyToIndex.keySet().contains(key);
	}

	@SuppressWarnings("boxing")
	@Override
	public boolean hasEdge(T from, T to) throws NoSuchElementException {
		if (!this.hasVertex(from) || !this.hasVertex(to)) {
			throw new NoSuchElementException("Element not found");
		}
		int i = this.keyToIndex.get(from);
		int j = this.keyToIndex.get(to);
		return (this.matrix[i][j] == 1);
	}

	@SuppressWarnings("boxing")
	@Override
	public boolean removeEdge(T from, T to) throws NoSuchElementException {
		if (!this.hasVertex(from) || !this.hasVertex(to)) {
			throw new NoSuchElementException("Element not found");
		}
		if (!this.hasEdge(from, to)) {
			return false;
		}
		int i = this.keyToIndex.get(from);
		int j = this.keyToIndex.get(to);
		this.matrix[i][j] = 0;
		return true;
	}

	@SuppressWarnings("boxing")
	@Override
	// calculates outDegree in O(n) time
	public int outDegree(T key) {
		if (!this.hasVertex(key)) {
			throw new NoSuchElementException("Element not found");
		}
		int i = this.keyToIndex.get(key);
		int outDegree = 0;
		for (int j = 0; j < this.size(); j++) {
			outDegree += this.matrix[i][j];
		}
		return outDegree;
	}

	@SuppressWarnings("boxing")
	@Override
	// calculates inDegree in O(n) time
	public int inDegree(T key) {
		if (!this.hasVertex(key)) {
			throw new NoSuchElementException("Element not found");
		}
		int j = this.keyToIndex.get(key);
		int inDegree = 0;
		for (int i = 0; i < this.size(); i++) {
			inDegree += this.matrix[i][j];
		}
		return inDegree;
	}

	@Override
	public Set<T> keySet() {
		return this.keyToIndex.keySet();
	}

	@SuppressWarnings("boxing")
	@Override
	public Set<T> successorSet(T key) {
		if (!this.hasVertex(key)) {
			throw new NoSuchElementException("Element not found");
		}
		Set<T> outSet = new HashSet<>();
		int i = this.keyToIndex.get(key);
		for (int j = 0; j < this.size(); j++) {
			if (this.matrix[i][j] == 1) {
				T s = this.indexToKey.get(j);
				outSet.add(s);
			}
		}
		return outSet;
	}

	@SuppressWarnings("boxing")
	@Override
	public Set<T> predecessorSet(T key) {
		if (!this.hasVertex(key)) {
			throw new NoSuchElementException("Element not found");
		}
		Set<T> inSet = new HashSet<>();
		int j = this.keyToIndex.get(key);
		for (int i = 0; i < this.size(); i++) {
			if (this.matrix[i][j] == 1) {
				T s = this.indexToKey.get(i);
				inSet.add(s);
			}
		}
		return inSet;
	}

	@Override
	@SuppressWarnings("boxing")
	public Iterator<T> successorIterator(T key) {
		if (!this.hasVertex(key)) {
			throw new NoSuchElementException("Element not found");
		}
		return new Iterator<T>() {
			int row = AdjacencyMatrixGraph.this.keyToIndex.get(key);
			int column = 0;

			@Override
			public boolean hasNext() {
				for (int j = this.column; j < AdjacencyMatrixGraph.this.size(); j++) {
					if (AdjacencyMatrixGraph.this.matrix[row][j] == 1) {
						return true;
					}
				}
				return false;
			}

			@Override
			public T next() {
				while (AdjacencyMatrixGraph.this.matrix[row][column] != 1) {
					if (this.column >= AdjacencyMatrixGraph.this.size()) {
						throw new NoSuchElementException("No more elements");
					}
					this.column++;
				}
				return AdjacencyMatrixGraph.this.indexToKey.get(column++);
			}

		};
	}

	@SuppressWarnings("boxing")
	@Override
	public Iterator<T> predecessorIterator(T key) {
		if (!this.hasVertex(key)) {
			throw new NoSuchElementException("Element not found");
		}
		return new Iterator<T>() {
			int row = 0;
			int column = AdjacencyMatrixGraph.this.keyToIndex.get(key);

			@Override
			public boolean hasNext() {
				for (int i = this.row; i < AdjacencyMatrixGraph.this.size(); i++) {
					if (AdjacencyMatrixGraph.this.matrix[i][column] == 1) {
						return true;
					}
				}
				return false;
			}

			@Override
			public T next() {
				while (AdjacencyMatrixGraph.this.matrix[row][column] != 1) {
					if (this.row >= AdjacencyMatrixGraph.this.size()) {
						throw new NoSuchElementException("No more elements");
					}
					this.row++;
				}
				return AdjacencyMatrixGraph.this.indexToKey.get(row++);
			}

		};
	}

	@Override
	public Set<T> stronglyConnectedComponent(T key) {
		if (!this.hasVertex(key)) {
			throw new NoSuchElementException("Element not found");
		}
		Set<T> scc = new HashSet<>();
		for (int i = 0; i < this.size(); i++) {
			T element = this.indexToKey.get(i);
			if (this.bfs(key, element) && this.bfs(element, key)) {
				scc.add(element);
			}
		}
		scc.add(key);
		return scc;
	}

	@Override
	public boolean dfs(T from, T to) {
		HashSet<T> visitedSet = new HashSet<>();
		MutableBool bool = new MutableBool();
		bool.value = false;
		this.dfs(from, to, visitedSet, bool);
		return bool.value;
	}

	public void dfs(T from, T to, HashSet<T> set, MutableBool bool) {
		set.add(from);
		Iterator<T> iter = this.successorIterator(from);
		if (!bool.value) {
			while (iter.hasNext()) {
				T element = iter.next();
				if (element.equals(to)) {
					bool.value = true;
				}
				if (!set.contains(element)) {
					this.dfs(element, to, set, bool);
				}
			}
		}
		return;
	}

	@SuppressWarnings("boxing")
	@Override
	public List<T> shortestPath(T startLabel, T endLabel) {
		if (!this.hasVertex(startLabel) || !this.hasVertex(endLabel)) {
			throw new NoSuchElementException("Element not found");
		}
		ArrayList<T> pred = new ArrayList<>(); // predecessor list
		ArrayList<Integer> dist = new ArrayList<>(); // 'distance from source' list
		ArrayList<T> path = new ArrayList<>(); // store shortest path

		// performs bfs and stores results in pred and dist maps
		if (!this.bfs(startLabel, endLabel, pred, dist)) {
			return null; // start and end are not connected
		}

		// retrieve predecessors (backwards)
		Stack<T> stack = new Stack<>();
		stack.push(endLabel);
		T predecessor = pred.get(this.keyToIndex.get(endLabel));
		while (predecessor != null) {
			stack.push(predecessor);
			predecessor = pred.get(this.keyToIndex.get(predecessor));
		}

		// reverse direction off the stack
		while (!stack.isEmpty()) {
			path.add(stack.pop());
		}

		return path;
	}

	// Breadth-first search
	@Override
	public boolean bfs(T src, T dest) {
		ArrayList<T> pred = new ArrayList<>(); // predecessor list
		ArrayList<Integer> dist = new ArrayList<>(); // 'distance from source' list
		return bfs(src, dest, pred, dist);
	}

	// Breadth-first search
	@SuppressWarnings("boxing")
	public boolean bfs(T src, T dest, ArrayList<T> pred, ArrayList<Integer> dist) {
		ArrayList<Boolean> visited = new ArrayList<>(); // 'node has been visited' list
		LinkedList<T> queue = new LinkedList<>(); // for bfs traversal

		// Initialize lists
		for (int i = 0; i < this.size(); i++) {
			pred.add(i, null);
			dist.add(i, Integer.MAX_VALUE);
			visited.add(i, false);
		}

		// Initialize first vertex
		int srcIdx = this.keyToIndex.get(src);
		visited.set(srcIdx, true);
		dist.set(srcIdx, 0);
		queue.add(src);

		// perform a breadth-first search
		while (!queue.isEmpty()) {
			T curr = queue.remove();
			int i = this.keyToIndex.get(curr);
			for (int j = 0; j < this.size(); j++) {
				if (this.matrix[i][j] == 1) {
					// found a successor
					T successor = this.indexToKey.get(j);
					// make sure we haven't already visited this node
					if (!visited.get(j)) {
						visited.set(j, true);
						dist.set(j, dist.get(i) + 1);
						pred.set(j, curr); // maps the successor's parent to the current vertex
						queue.add(successor);
					}

					if (successor.equals(dest)) {
						return true;
					}
				}
			}

		}
		return false;
	}

}
