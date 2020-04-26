import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Arrays;

import org.junit.Test;

/**
 * Tests binary heaps.
 * 
 * @author Matt Boutell. Created May 7, 2013.
 */
public class BinaryHeapTest {

	/**
	 * Simple test method for insert, delete, toString, and sort. Enforces the
	 * method signatures.
	 */
	@Test
	public void testSimple() {
		/*
		 * CONSIDER: Since we're implementing the heap's internal storage
		 * using a primitive array (and not an ArrayList), like we did on
		 * GrowableCircularArrayQueue from the StacksAndQueues assignment,
		 * we need to pass in to the constructor a parameter that tells what
		 * class it is.
		 */
		BinaryHeap<Integer> heap = new BinaryHeap<>(Integer.class);
		
		// deleteMin returns null if it has no elements.
		assertNull(heap.deleteMin());
		heap.insert(21);
		assertEquals("[null, 21]", heap.toString());
		int min = heap.deleteMin();
		assertEquals(21, min);
		assertEquals("[null]", heap.toString());
		String[] csLegends = new String[] { "Edsger Dijkstra", "Grace Hopper", "Donald Knuth", "Ada Lovelace",
				"Claude Shannon", "Alan Turing" };
		BinaryHeap.sort(csLegends, String.class);
		assertEquals("[Ada Lovelace, Alan Turing, Claude Shannon, Donald Knuth, Edsger Dijkstra, Grace Hopper]",
				Arrays.toString(csLegends));
	}

	// TODO: Add unit tests for each method until you are satisfied your code is
	// correct. I will test your code with more complex tests. Since this
	// assignment is short, I'd like you to give the tests some thought, rather
	// than just always relying on someone else's tests. Professional developers
	// usually write their own unit tests.
	
	@Test
	public void testInsertNoResizeNoRotate() {
		BinaryHeap<Integer> heap = new BinaryHeap<>(Integer.class);
		assertNull(heap.findMin());
		
		heap.insert(7);
		int num = heap.findMin();
		assertEquals(7, num);
		
		heap.insert(10);
		heap.insert(9);
		heap.insert(12);
		num = heap.findMin();
		assertEquals(7, num);
		assertEquals("[null, 7, 10, 9, 12]", heap.toString());
		
		// initial length is 5, 4 items total
		assertEquals(4, heap.size());
		assertEquals(5, heap.length());
	}
	
	@Test
	public void testInsertWithResizeNoRotate() {
		BinaryHeap<Integer> heap = new BinaryHeap<>(Integer.class);
		assertNull(heap.findMin());
		
		heap.insert(7);
		heap.insert(10);
		heap.insert(9);
		heap.insert(12);
		
		assertEquals("[null, 7, 10, 9, 12]", heap.toString());
		assertEquals(4, heap.size());
		assertEquals(5, heap.length());
		
		// cause a resize
		heap.insert(15);
		assertEquals("[null, 7, 10, 9, 12, 15]", heap.toString());
		assertEquals(5, heap.size());
		assertEquals(10, heap.length());
		
		heap.insert(13);
		heap.insert(14);
		heap.insert(16);
		heap.insert(19);
		heap.insert(20);
		assertEquals("[null, 7, 10, 9, 12, 15, 13, 14, 16, 19, 20]", heap.toString());
		int num = heap.findMin();
		assertEquals(7, num);
		assertEquals(10, heap.size());
		assertEquals(20, heap.length());
	}
	
	@Test
	public void testInsertResizeAndRotate() {
		BinaryHeap<Integer> heap = new BinaryHeap<>(Integer.class);
		assertNull(heap.findMin());
		
		heap.insert(7);
		heap.insert(10);
		heap.insert(9);
		heap.insert(12);
		
		// cause resize
		heap.insert(4);
		int num = heap.findMin();
		assertEquals(4, num);
		assertEquals("[null, 4, 7, 9, 12, 10]", heap.toString());
		
		heap.insert(20);
		heap.insert(5);
		assertEquals(4, num);
		assertEquals("[null, 4, 7, 5, 12, 10, 20, 9]", heap.toString());
		
		heap.insert(8);
		assertEquals("[null, 4, 7, 5, 8, 10, 20, 9, 12]", heap.toString());
		heap.insert(6);
		assertEquals("[null, 4, 6, 5, 7, 10, 20, 9, 12, 8]", heap.toString());
		heap.insert(11);
		heap.insert(13);
		heap.insert(3);
		num = heap.findMin();
		assertEquals(3, num);
		assertEquals("[null, 3, 6, 4, 7, 10, 5, 9, 12, 8, 11, 13, 20]", heap.toString());
		assertEquals(12, heap.size());
		assertEquals(20, heap.length());
	}
	
	@Test
	public void testDeleteMin() {
		BinaryHeap<Integer> heap = new BinaryHeap<>(Integer.class);
		int deleteValue;
		assertNull(heap.deleteMin());
		
		heap.insert(7);
		deleteValue = heap.deleteMin();
		assertEquals(7, deleteValue);
		assertEquals(0, heap.size());
		assertEquals(5, heap.length());
		assertEquals("[null]", heap.toString());
		
		heap.insert(7);
		heap.insert(10);
		heap.insert(9);
		heap.insert(12);
		deleteValue = heap.deleteMin();
		assertEquals(7, deleteValue);
		assertEquals(3, heap.size());
		assertEquals(5, heap.length());
		int num = heap.findMin();
		assertEquals(9, num);
		assertEquals("[null, 9, 10, 12]", heap.toString());
		
		heap.insert(3);
		num = heap.findMin();
		assertEquals(3, num);
		assertEquals("[null, 3, 9, 12, 10]", heap.toString());
		assertEquals(4, heap.size());
		deleteValue = heap.deleteMin();
		assertEquals(3, deleteValue);
		assertEquals(3, heap.size());
		assertEquals(5, heap.length());
		num = heap.findMin();
		assertEquals(9, num);
		assertEquals("[null, 9, 10, 12]", heap.toString());
		
		// cause resize
		heap.insert(4);
		num = heap.findMin();
		assertEquals(4, num);
		assertEquals("[null, 4, 9, 12, 10]", heap.toString());
		heap.insert(11);
		heap.insert(16);
		heap.insert(17);
		assertEquals("[null, 4, 9, 12, 10, 11, 16, 17]", heap.toString());
		
		deleteValue = heap.deleteMin();
		assertEquals(4, deleteValue);
		assertEquals("[null, 9, 10, 12, 17, 11, 16]", heap.toString());
		assertEquals(6, heap.size());
		assertEquals(10, heap.length());
		
		deleteValue = heap.deleteMin();
		assertEquals(9, deleteValue);
		assertEquals("[null, 10, 11, 12, 17, 16]", heap.toString());
		assertEquals(5, heap.size());
		assertEquals(10, heap.length());
		
		deleteValue = heap.deleteMin();
		assertEquals(10, deleteValue);
		assertEquals("[null, 11, 16, 12, 17]", heap.toString());
		assertEquals(4, heap.size());
		assertEquals(10, heap.length());
		
		deleteValue = heap.deleteMin();
		assertEquals(11, deleteValue);
		assertEquals("[null, 12, 16, 17]", heap.toString());
		assertEquals(3, heap.size());
		assertEquals(10, heap.length());
		
		deleteValue = heap.deleteMin();
		assertEquals(12, deleteValue);
		assertEquals("[null, 16, 17]", heap.toString());
		assertEquals(2, heap.size());
		assertEquals(10, heap.length());
		
		deleteValue = heap.deleteMin();
		assertEquals(16, deleteValue);
		assertEquals("[null, 17]", heap.toString());
		assertEquals(1, heap.size());
		assertEquals(10, heap.length());
		
		deleteValue = heap.deleteMin();
		assertEquals(17, deleteValue);
		assertEquals("[null]", heap.toString());
		assertEquals(0, heap.size());
		assertEquals(10, heap.length());
		
		assertNull(heap.deleteMin());
		assertEquals("[null]", heap.toString());
		assertEquals(0, heap.size());
		assertEquals(10, heap.length());
	}

}
