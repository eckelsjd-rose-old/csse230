package indexof;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

import org.junit.AfterClass;
import org.junit.Test;


public class Testing {

	private static int indexOfPoints = 0;

	@Test
	public void testIndexOfBasic() {
		BinarySearchTree t = new BinarySearchTree();
		t.insert(50);
		t.insert(40);
		t.insert(60);
		t.insert(20);
		assertEquals(2,t.indexOf(50));
		assertEquals(1,t.indexOf(40));
		assertEquals(3,t.indexOf(60));
		assertEquals(0,t.indexOf(20));
		indexOfPoints += 5;
	}	
	
	
	@Test
	public void testIndexOfGivenExample() {
		BinarySearchTree t = new BinarySearchTree();
		t.insert(50);
		t.insert(40);
		t.insert(30);
		t.insert(20);
		t.insert(45);
		t.insert(80);
		t.insert(70);
		t.insert(60);
		t.insert(55);
		t.insert(75);
		t.insert(90);
		t.insert(85);
		assertEquals(0,t.indexOf(20));
		assertEquals(2,t.indexOf(40));
		assertEquals(4,t.indexOf(50));
		assertEquals(5,t.indexOf(55));
		assertEquals(10,t.indexOf(85));
		assertEquals(11,t.indexOf(90));
		indexOfPoints += 5;
	}
	
	@Test
	public void testIndexOfNotInTree() {
		BinarySearchTree t = new BinarySearchTree();
		t.insert(50);
		t.insert(40);
		t.insert(60);
		t.insert(20);
		try {
			t.indexOf(30);
			fail("Did not throw NoSuchElementException");
		} catch (Exception e) {
			if (!(e instanceof NoSuchElementException)) {
				fail("Did not throw NoSuchElementException");
			}
		}
		indexOfPoints += 2;
	}	
	
	
	@Test
	public void testIndexOfRandom() {
		HashSet<Integer> set = new HashSet<Integer>();
		Random ran = new Random();
		for (Integer i = 0; i < 100; i++) {  // Generate 100 random Integers and add them to the set
			set.add(ran.nextInt(1000));      // (due to repetition, may end up with < 100)
		}
		BinarySearchTree t = new BinarySearchTree();
		List<Integer> list = new ArrayList<Integer>();
		for (Integer x : set) {
			t.insert(x);           // Insert into tree
			list.add(x);           // Insert into list
		}
		list.sort(null);           // Sort list
		for (Integer x : set) {
			assertEquals(list.indexOf(x),t.indexOf(x)); // check to see if indexes match
		}
		indexOfPoints += 8;
	}


	@AfterClass
	public static void displayPoints() {
		System.out.printf("indexOf points:                  %2d/20\n", indexOfPoints);
	}
}