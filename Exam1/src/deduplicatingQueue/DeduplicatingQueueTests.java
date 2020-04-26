package deduplicatingQueue;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.Test;

/**
 * @author Nate Chenette
 */

public class DeduplicatingQueueTests {
	private static int points = 0;
	
	@Test
	public void testEnqueueDequeueIsEmpty() {
		DeduplicatingQueue<String> q = new DeduplicatingQueue<>();
		q.enqueue("a");
		q.enqueue("b");
		q.enqueue("c");
		assertEquals("a",q.dequeue());
		assertEquals("b",q.dequeue());
		points += 1;
		q.enqueue("d");
		assertEquals("c",q.dequeue());
		assertEquals("d",q.dequeue());
		assertTrue(q.isEmpty());
		q.enqueue("e");
		assertFalse(q.isEmpty());
		assertEquals("e",q.dequeue());
		assertTrue(q.isEmpty());
		points += 1;
	}
	
	@Test
	public void testEnqueueDequeueWithRepeats() {
		DeduplicatingQueue<Integer> q = new DeduplicatingQueue<>();
		q.enqueue(1);
		q.enqueue(2);
		q.enqueue(2);
		q.enqueue(3);
		q.enqueue(1);
		q.enqueue(1);
		q.enqueue(4);
		assertEquals(new Integer(1),q.dequeue());
		assertEquals(new Integer(2),q.dequeue());
		assertEquals(new Integer(2),q.dequeue());
		assertEquals(new Integer(3),q.dequeue());
		assertEquals(new Integer(1),q.dequeue());
		assertEquals(new Integer(1),q.dequeue());
		assertEquals(new Integer(4),q.dequeue());
		points += 2;
	}
	
	@Test
	public void testDeduplicate() {
		DeduplicatingQueue<Integer> q = new DeduplicatingQueue<>();
		q.enqueue(1);
		q.enqueue(2);
		q.enqueue(2);
		q.enqueue(3);
		q.enqueue(1);
		q.enqueue(1);
		q.enqueue(4);
		q.deduplicate();
		assertEquals(new Integer(1),q.dequeue());
		assertEquals(new Integer(2),q.dequeue());
		assertEquals(new Integer(3),q.dequeue());
		assertEquals(new Integer(4),q.dequeue());
		points += 3;
	}

	@Test
	public void testDuplicatesAfterDeduplicate() {
		DeduplicatingQueue<Integer> q = new DeduplicatingQueue<>();
		q.enqueue(1);
		q.enqueue(2);
		q.enqueue(2);
		q.enqueue(3);
		q.enqueue(1);
		q.deduplicate();
		q.enqueue(1);
		q.enqueue(4);
		q.enqueue(2);
		q.enqueue(2);
		q.enqueue(1);
		assertEquals(new Integer(1),q.dequeue());
		assertEquals(new Integer(2),q.dequeue());
		assertEquals(new Integer(3),q.dequeue());
		assertEquals(new Integer(1),q.dequeue());
		assertEquals(new Integer(4),q.dequeue());
		assertEquals(new Integer(2),q.dequeue());
		assertEquals(new Integer(2),q.dequeue());
		assertEquals(new Integer(1),q.dequeue());
		points += 2;
	}
	
	@Test
	public void testDeduplicateLong() {
		DeduplicatingQueue<String> q = new DeduplicatingQueue<>();
		q.enqueue("badger");
		q.enqueue("badger");
		q.enqueue("badger");
		q.enqueue("badger");
		q.enqueue("mushroom");
		q.enqueue("mushroom");
		assertEquals("badger",q.dequeue());
		q.deduplicate();
		assertEquals("badger",q.dequeue());
		assertEquals("mushroom",q.dequeue());
		assertTrue(q.isEmpty());
		q.enqueue("badger");
		q.enqueue("badger");
		q.enqueue("badger");
		q.enqueue("badger");
		q.enqueue("mushroom");
		q.enqueue("mushroom");
		assertEquals("badger",q.dequeue());
		assertEquals("badger",q.dequeue());
		assertEquals("badger",q.dequeue());
		q.deduplicate();
		assertEquals("badger",q.dequeue());
		assertEquals("mushroom",q.dequeue());
		assertTrue(q.isEmpty());
		q.enqueue("snake");
		q.enqueue("badger");
		q.enqueue("badger");
		q.enqueue("badger");
		q.enqueue("badger");
		q.enqueue("mushroom");
		q.enqueue("mushroom");
		q.enqueue("badger");
		q.enqueue("badger");
		q.enqueue("badger");
		q.enqueue("badger");
		q.enqueue("mushroom");
		q.enqueue("mushroom");
		assertEquals("snake",q.dequeue());
		q.deduplicate();
		q.enqueue("snake");
		assertEquals("badger",q.dequeue());
		assertEquals("mushroom",q.dequeue());
		assertEquals("snake",q.dequeue());
		assertTrue(q.isEmpty());
		points += 3;
	}
	
	@AfterClass
	public static void showPoints() {
		System.out.printf("DeduplicatingQueue unit test points: %d of 12\n", points);
		System.out.printf("DeduplicatingQueue efficiency points: ? of 8 (graded manually)\n");
	}
	
}
