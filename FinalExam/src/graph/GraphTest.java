package graph;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

import org.junit.AfterClass;
import org.junit.Test;

public class GraphTest {

	private static int graphALPoints = 0;
	private static int graphAMPoints = 0;
	private static int lazinessALPoints = 0;
	private static int lazinessAMPoints = 0;
	

	private Graph<Integer> example1(boolean adjList) {
		Graph<Integer> g;
		HashSet<Integer> keys = new HashSet<Integer>();
		for (int i = 0; i < 10; i++) {
			keys.add(i);
		}
		if (adjList) {
			g = new AdjacencyListGraph<Integer>(keys);
		} else {
			g = new AdjacencyMatrixGraph<Integer>(keys);
		}
		g.addEdge(0, 1);
		g.addEdge(0, 2);
		g.addEdge(1, 4);
		g.addEdge(2, 3);
		g.addEdge(3, 5);
		g.addEdge(3, 6);
		g.addEdge(3, 7);
		g.addEdge(4, 5);
		g.addEdge(4, 7);
		g.addEdge(4, 8);
		g.addEdge(7, 9);
		g.addEdge(8, 9);
		return g;
	}
	
	private Graph<String> example2(boolean adjList) {
		Graph<String> g;
		String s = "ABCDEFGHIJK";
		HashSet<String> keys = new HashSet<String>();
		for (int i = 0; i < 11; i++) {
			keys.add(s.substring(i, i+1));
		}
		if (adjList) {
			g = new AdjacencyListGraph<String>(keys);
		} else {
			g = new AdjacencyMatrixGraph<String>(keys);
		}
		g.addEdge("A","B");
		g.addEdge("A","E");
		g.addEdge("B","D");
		g.addEdge("B","F");
		g.addEdge("C","B");
		g.addEdge("C","J");
		g.addEdge("D","A");
		g.addEdge("E","J");
		g.addEdge("F","D");
		g.addEdge("F","G");
		g.addEdge("F","K");
		g.addEdge("G","B");
		g.addEdge("H","G");
		g.addEdge("I","K");
		g.addEdge("J","I");
		g.addEdge("K","H");
		return g;
	}
	
	@Test
	public void testExample1_AL() {
		Graph<Integer> g = example1(true);
		Iterator<Integer> iter = g.bfsIterator(0);
		assertEquals(new Integer(0),iter.next());
		assertEquals(new Integer(1),iter.next());
		assertEquals(new Integer(2),iter.next());
		assertEquals(new Integer(4),iter.next());
		assertEquals(new Integer(3),iter.next());
		assertEquals(new Integer(5),iter.next());
		assertEquals(new Integer(7),iter.next());
		assertEquals(new Integer(8),iter.next());
		graphALPoints += 1;
		assertEquals(new Integer(6),iter.next());
		assertTrue(iter.hasNext());
		graphALPoints += 1;
		assertEquals(new Integer(9),iter.next());
		assertFalse(iter.hasNext());
		graphALPoints += 1;
		
	}
	
	@Test
	public void testExample2A_AL() {
		Graph<String> g = example2(true);
		Iterator<String> iter = g.bfsIterator("A");
		assertEquals("A",iter.next());
		assertEquals("B",iter.next());
		assertEquals("E",iter.next());
		graphALPoints += 1;
		assertEquals("D",iter.next());
		assertEquals("F",iter.next());
		assertEquals("J",iter.next());
		assertEquals("G",iter.next());
		assertEquals("K",iter.next());
		assertEquals("I",iter.next());
		assertEquals("H",iter.next());
		graphALPoints += 1;
	}
	
	@Test
	public void testExample2C_AL() {
		Graph<String> g = example2(true);
		Iterator<String> iter = g.bfsIterator("C");
		assertEquals("C",iter.next());
		assertEquals("B",iter.next());
		assertEquals("J",iter.next());
		assertEquals("D",iter.next());
		graphALPoints += 1;
		assertEquals("F",iter.next());
		assertEquals("I",iter.next());
		assertEquals("A",iter.next());
		assertEquals("G",iter.next());
		assertEquals("K",iter.next());
		assertEquals("E",iter.next());
		assertEquals("H",iter.next());
		graphALPoints += 1;
	}
	
	@Test
	public void testExample1_AM() {
		Graph<Integer> g = example1(false);
		Iterator<Integer> iter = g.bfsIterator(0);
		assertEquals(new Integer(0),iter.next());
		assertEquals(new Integer(1),iter.next());
		assertEquals(new Integer(2),iter.next());
		assertEquals(new Integer(4),iter.next());
		assertEquals(new Integer(3),iter.next());
		assertEquals(new Integer(5),iter.next());
		assertEquals(new Integer(7),iter.next());
		assertEquals(new Integer(8),iter.next());
		graphAMPoints += 1;
		assertEquals(new Integer(6),iter.next());
		assertTrue(iter.hasNext());
		graphAMPoints += 1;
		assertEquals(new Integer(9),iter.next());
		assertFalse(iter.hasNext());
		graphAMPoints += 1;
		
	}

	@Test
	public void testExample2A_AM() {
		Graph<String> g = example2(false);
		Iterator<String> iter = g.bfsIterator("A");
		assertEquals("A",iter.next());
		assertEquals("B",iter.next());
		assertEquals("E",iter.next());
		graphAMPoints += 1;
		assertEquals("D",iter.next());
		assertEquals("F",iter.next());
		assertEquals("J",iter.next());
		assertEquals("G",iter.next());
		assertEquals("K",iter.next());
		assertEquals("I",iter.next());
		assertEquals("H",iter.next());
		graphAMPoints += 1;
	}
	
	@Test
	public void testExample2C_AM() {
		Graph<String> g = example2(false);
		Iterator<String> iter = g.bfsIterator("C");
		assertEquals("C",iter.next());
		assertEquals("B",iter.next());
		assertEquals("J",iter.next());
		assertEquals("D",iter.next());
		graphAMPoints += 1;
		assertEquals("F",iter.next());
		assertEquals("I",iter.next());
		assertEquals("A",iter.next());
		assertEquals("G",iter.next());
		assertEquals("K",iter.next());
		assertEquals("E",iter.next());
		assertEquals("H",iter.next());
		graphAMPoints += 1;
	}

	
	private Graph<Integer> largeRandom(boolean adjList) {
		Random rand = new Random();
		int numVerts = 1000;
		HashSet<Integer> keys = new HashSet<Integer>();
		for (int i = 0; i < numVerts; i++) {
			keys.add(i);
		}
		Graph<Integer> g;
		if (adjList) {
			g = new AdjacencyListGraph<Integer>(keys);
		} else {
			g = new AdjacencyMatrixGraph<Integer>(keys);
		}
		// Make sure vertex 0 has a bunch of successors
		for (int i = 0; i < 100; i++) {
			int to = rand.nextInt(numVerts);
			g.addEdge(0, to);
		}
		// Add many more edges
		for (int i = 0; i < 1000000; i++) {
			int from = rand.nextInt(numVerts);
			int to = rand.nextInt(numVerts);
			g.addEdge(from, to);
		}
		return g;
	}
	
	@Test
	public void testLazy_AL() {
		Graph<Integer> g = largeRandom(true);
		Long beforeTime = System.currentTimeMillis();
		// Create iterator and call .next() a couple times
		Iterator<Integer> iter = g.bfsIterator(0);
		assertEquals(new Integer(0),iter.next());
		iter.next();
		Long afterTime = System.currentTimeMillis();
		Long totalTime = afterTime - beforeTime;
		int timeLimit = 5;
		if (totalTime <= timeLimit) {
			System.out.println("bfsIterator laziness test (AL): PASS. " + Long.toString(totalTime) + " ms <= " + Integer.toString(timeLimit) + " ms");
		} else {
			System.out.println("bfsIterator laziness test (AL): FAIL. " + Long.toString(totalTime) + " ms > " + Integer.toString(timeLimit) + " ms");
		}
		assertTrue(totalTime <= timeLimit);
		lazinessALPoints += 3;
	}
	
	@Test
	public void testLazy_AM() {
		Graph<Integer> g = largeRandom(false);
		Long beforeTime = System.currentTimeMillis();
		// Create iterator and call .next() a couple times
		Iterator<Integer> iter = g.bfsIterator(0);
		assertEquals(new Integer(0),iter.next());
		iter.next();
		Long afterTime = System.currentTimeMillis();
		Long totalTime = afterTime - beforeTime;
		int timeLimit = 5;
		if (totalTime <= timeLimit) {
			System.out.println("bfsIterator laziness test (AM): PASS. " + Long.toString(totalTime) + " ms <= " + Integer.toString(timeLimit) + " ms");
		} else {
			System.out.println("bfsIterator laziness test (AM): FAIL. " + Long.toString(totalTime) + " ms > " + Integer.toString(timeLimit) + " ms");
		}
		assertTrue(totalTime <= timeLimit);
		lazinessAMPoints += 3;
	}
	

	@AfterClass
	public static void displayPoints() {
		System.out.println("----------------------------------------------------------------------");
		System.out.printf("3.  %1d/7   bfsIterator (AL) correctness points        \n", graphALPoints);
		if (graphALPoints < 7) {
			System.out.println("    0/3   Laziness points cannot be earned until iterators are correct");
		} else {
			System.out.printf("    %1d/3   bfsIterator (AL) laziness points       \n", lazinessALPoints);
		}
		System.out.printf("    %1d/7   bfsIterator (AM) correctness points        \n", graphAMPoints);
		if (graphALPoints < 7) {
			System.out.println("    0/3   Laziness points cannot be earned until iterators are correct");
		} else {
			System.out.printf("    %1d/3   bfsIterator (AM) laziness points       \n", lazinessAMPoints);
		}
		System.out.println("----------------------------------------------------------------------");
	}

}
