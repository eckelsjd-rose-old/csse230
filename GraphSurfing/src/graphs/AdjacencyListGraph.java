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

public class AdjacencyListGraph<T> extends Graph<T> {
	Map<T, Vertex> keyToVertex;
	int numVertices;

	private class Vertex {
		T key;
		List<Vertex> successors;
		List<Vertex> predecessors;

		Vertex(T key) {
			this.key = key;
			this.successors = new ArrayList<Vertex>();
			this.predecessors = new ArrayList<Vertex>();
		}
	}

	private class MutableBool {
		boolean value;

		MutableBool() {
		}
	}

	AdjacencyListGraph(Set<T> keys) {
		this.numVertices = 0;
		this.keyToVertex = new HashMap<T, Vertex>();
		for (T key : keys) {
			Vertex v = new Vertex(key);
			this.keyToVertex.put(key, v);
			this.numVertices++;
		}
	}

	@Override
	public int size() {
		return this.numVertices;
	}

	@Override
	public int numEdges() {
		int edges = 0;
		for (T key : this.keyToVertex.keySet()) {
			Vertex v = this.keyToVertex.get(key);
			edges += v.successors.size();
		}
		return edges;
	}

	@Override
	public boolean addEdge(T from, T to) throws NoSuchElementException {
		if (!this.hasVertex(from) || !this.hasVertex(to)) {
			throw new NoSuchElementException("Element not found");
		}
		if (this.hasEdge(from, to)) {
			return false;
		}
		Vertex v1 = this.keyToVertex.get(from);
		Vertex v2 = this.keyToVertex.get(to);
		v1.successors.add(v2);
		v2.predecessors.add(v1);
		return true;
	}

	@Override
	public boolean hasVertex(T key) {
		return this.keyToVertex.keySet().contains(key);
	}

	@Override
	public boolean hasEdge(T from, T to) throws NoSuchElementException {
		if (!this.hasVertex(from) || !this.hasVertex(to)) {
			throw new NoSuchElementException("Element not found");
		}
		Vertex v1 = this.keyToVertex.get(from);
		Vertex v2 = this.keyToVertex.get(to);
		return (v1.successors.contains(v2) && v2.predecessors.contains(v1));
	}

	@Override
	public boolean removeEdge(T from, T to) throws NoSuchElementException {
		if (!this.hasVertex(from) || !this.hasVertex(to)) {
			throw new NoSuchElementException("Element not found");
		}
		if (!this.hasEdge(from, to)) {
			return false;
		}
		Vertex v1 = this.keyToVertex.get(from);
		Vertex v2 = this.keyToVertex.get(to);
		v1.successors.remove(v2);
		v2.predecessors.remove(v1);
		return true;
	}

	@Override
	// calculates outDegree in O(1) time
	public int outDegree(T key) {
		if (!this.hasVertex(key)) {
			throw new NoSuchElementException("Element not found");
		}
		Vertex v1 = this.keyToVertex.get(key);
		return v1.successors.size();
	}

	// calculates inDegree in O(1) time
	@Override
	public int inDegree(T key) {
		if (!this.hasVertex(key)) {
			throw new NoSuchElementException("Element not found");
		}
		Vertex v1 = this.keyToVertex.get(key);
		return v1.predecessors.size();
	}

	@Override
	public Set<T> keySet() {
		return this.keyToVertex.keySet();
	}

	@Override
	public Set<T> successorSet(T key) {
		if (!this.hasVertex(key)) {
			throw new NoSuchElementException("Element not found");
		}
		Set<T> outSet = new HashSet<>();
		Vertex v1 = this.keyToVertex.get(key);
		for (Vertex v : v1.successors) {
			outSet.add(v.key);
		}
		return outSet;
	}

	@Override
	public Set<T> predecessorSet(T key) {
		if (!this.hasVertex(key)) {
			throw new NoSuchElementException("Element not found");
		}
		Set<T> inSet = new HashSet<>();
		Vertex v1 = this.keyToVertex.get(key);
		for (Vertex v : v1.predecessors) {
			inSet.add(v.key);
		}
		return inSet;
	}

	@Override
	public Iterator<T> successorIterator(T key) {
		if (!this.hasVertex(key)) {
			throw new NoSuchElementException("Element not found");
		}
		return AdjacencyListGraph.this.successorSet(key).iterator();
	}

	@Override
	public Iterator<T> predecessorIterator(T key) {
		if (!this.hasVertex(key)) {
			throw new NoSuchElementException("Element not found");
		}
		return AdjacencyListGraph.this.predecessorSet(key).iterator();
	}

	// Inefficient brute-force scc algorithm (first attempt)
	@Override
	public Set<T> stronglyConnectedComponent(T key) {
		if (!this.hasVertex(key)) {
			throw new NoSuchElementException("Element not found");
		}
		Set<T> scc = new HashSet<>();
		int i = 0;
		for (T element : this.keyToVertex.keySet()) {
			if (this.bfs(key, element) && this.bfs(element, key)) {
				scc.add(element);
				System.out.println(i++);
			}
		}
		scc.add(key);
		return scc;
	}
	
//	// Efficient scc algorithm using kosaraju's algorithm
//	@SuppressWarnings("boxing")
//	@Override
//	public Set<T> stronglyConnectedComponent(T key) {
//		if (!this.hasVertex(key)) {
//			throw new NoSuchElementException("Element not found");
//		}
//		Set<T> scc = new HashSet<>();
//		HashMap<T, Integer> sccMap = this.kosaraju();
//		int sccNum = sccMap.get(key);
//		
//		// iterate through all nodes in the map returned by kosaraju algorithm
//		for (T node : sccMap.keySet()) {
//			if (sccMap.get(node) == sccNum) {
//				scc.add(node);
//			}
//		}
//		return scc;
//	}

//	public HashMap<T, Integer> kosaraju() {
//		// reverse all edges in the graph
//		AdjacencyListGraph<T> reverse = this.reverse();
//		
//		// Run full dfs on reverse graph and store order of traversal on stack;
//		// Nodes on top of stack are guaranteed to be in sink SCCs
//		Stack<T> stack = this.visitOrder(reverse);
//		
//		// Retrace all nodes on the stack. Run another dfs on each node.
//		// The extent of this dfs will span 1 full SCC. Label each node with
//		// the level "i" SCC that it is in. Store each node and it's respective
//		// SCC level in a map.
//		HashMap<T, Integer> sccMap = new HashMap<>();
//		int i = 0;
//		
//		while (!stack.isEmpty()) {
//			T start = stack.pop();
//			if (sccMap.containsKey(start)) { continue; }
//			this.kosarajuDFS(start, sccMap, i);
//			i++;
//		}
//		return sccMap;
//	}
//	
//	public void kosarajuDFS(T node, HashMap<T,Integer> sccMap, int i) {
//		if (sccMap.containsKey(node)) {return; }
//		sccMap.put(node, i);
//		Vertex v1 = this.keyToVertex.get(node);
//		for (Vertex succ : v1.successors) {
//			this.kosarajuDFS(succ.key, sccMap, i);
//		}
//	}
//
//	/*
//	 * Given a graph, stores the nodes of the graph on a stack in the
//	 * order in which a DFS of that graph finishes expanding nodes.
//	 * 
//	 * Returns this visitOrder stack of nodes.
//	 */
//	public Stack<T> visitOrder(AdjacencyListGraph<T> g) {
//		Stack<T> stack = new Stack<>();
//		Set<T> visited = new HashSet<>();
//		// DFS on each node
//		for (T node : g.keySet()) {
//			visitOrder(node, g, stack, visited);
//		}
//		return stack;
//	}
//
//	public void visitOrder(T node, AdjacencyListGraph<T> g, Stack<T> stack, Set<T> visited) {
//		// skip already visited nodes
//		if (visited.contains(node)) { return; }
//		visited.add(node);
//		Vertex v1 = g.keyToVertex.get(node);
//		for (Vertex succ : v1.successors) {
//			this.visitOrder(succ.key, g, stack, visited);
//		}
//		stack.push(node);
//	}
//
//	// Returns a new graph with all edges reversed
//	public AdjacencyListGraph<T> reverse() {
//		AdjacencyListGraph<T> result = new AdjacencyListGraph<>(this.keySet());
//		for (T key : result.keySet()) {
//			Vertex v1 = this.keyToVertex.get(key);
//			for (Vertex v : v1.successors) {
//				result.addEdge(v.key, v1.key);
//			}
//		}
//		return result;
//	}

	// depth-first search (uses recursion/stack)
	@Override
	public boolean dfs(T from, T to) {
		HashSet<T> visitedSet = new HashSet<>();
		MutableBool bool = new MutableBool();
		bool.value = false;
		this.dfs(from, to, visitedSet, bool);
		return bool.value;
	}

	public void dfs(T from, T to, HashSet<T> set, MutableBool bool) {
		Vertex v1 = this.keyToVertex.get(from);
		set.add(from);
		if (!bool.value) {
			for (Vertex v : v1.successors) {
				if (v.key.equals(to)) {
					bool.value = true;
				}
				if (!set.contains(v.key)) {
					this.dfs(v.key, to, set, bool);
				}
			}
		}
		return;
	}

	@Override
	public List<T> shortestPath(T startLabel, T endLabel) {
		if (!this.hasVertex(startLabel) || !this.hasVertex(endLabel)) {
			throw new NoSuchElementException("Element not found");
		}
		HashMap<T, T> pred = new HashMap<>(); // predecessor map
		HashMap<T, Integer> dist = new HashMap<>(); // distance from source map
		ArrayList<T> path = new ArrayList<>(); // store shortest path

		// performs bfs and stores results in pred and dist maps
		if (!this.bfs(startLabel, endLabel, pred, dist)) {
			return null; // start and end are not connected
		}

		// retrieve predecessors (backwards)
		Stack<T> stack = new Stack<>();
		stack.push(endLabel);
		T predecessor = pred.get(endLabel);
		while (predecessor != null) {
			stack.push(predecessor);
			predecessor = pred.get(predecessor);
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
		HashMap<T, T> pred = new HashMap<>(); // predecessor map
		HashMap<T, Integer> dist = new HashMap<>(); // distance from source map
		return bfs(src, dest, pred, dist);
	}

	@SuppressWarnings("boxing")
	public boolean bfs(T src, T dest, HashMap<T, T> pred, HashMap<T, Integer> dist) {
		HashMap<T, Boolean> visited = new HashMap<>(); // 'node has been visited' map
		LinkedList<T> queue = new LinkedList<>(); // for bfs traversal

		// Initialize maps
		for (T vertex : this.keyToVertex.keySet()) {
			pred.put(vertex, null);
			dist.put(vertex, Integer.MAX_VALUE);
			visited.put(vertex, false);
		}

		// Initialize first vertex
		visited.put(src, true);
		dist.put(src, 0);
		queue.add(src);

		// perform a breadth-first search
		while (!queue.isEmpty()) {
			T curr = queue.remove();
			Vertex current = this.keyToVertex.get(curr);
			for (Vertex succ : current.successors) {
				T successor = succ.key;
				// make sure we haven't already visited this node
				if (!visited.get(successor)) {
					visited.put(successor, true);
					dist.put(successor, dist.get(curr) + 1);
					pred.put(successor, curr); // maps the successor's parent to the current vertex
					queue.add(successor);
				}

				if (successor.equals(dest)) {
					return true;
				}
			}

		}
		return false;
	}

}
