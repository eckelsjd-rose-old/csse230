package redblack;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.Test;


public class Testing {

	private static int isRedBlackTreePoints = 0;

	// Creates a valid red-black tree with black height 1
	public BinarySearchTreeWithColor buildBlackHeight1() {
		BinarySearchTreeWithColor t = new BinarySearchTreeWithColor();
		t.insert(50,true);
		t.insert(20, false);
		t.insert(10, true);
		t.insert(30, true);
		t.insert(80, true);
		t.insert(60, false);
		return t;
	}
	
	// Creates a valid red-black tree with black height 2
	public BinarySearchTreeWithColor buildBlackHeight2() {
		BinarySearchTreeWithColor t = new BinarySearchTreeWithColor();
		t.insert(50,true);
		t.insert(20, false);
		t.insert(10, true);
		t.insert(30, true);
		t.insert(5, true);
		t.insert(15, true);
		t.insert(12, false);
		t.insert(25, true);
		t.insert(40, false);
		t.insert(35, true);
		t.insert(45, true);
		t.insert(80, true);
		t.insert(60, false);
		t.insert(55, true);
		t.insert(75, true);
		t.insert(78, false);
		t.insert(90, true);
		t.insert(95, false);
		return t;
	}
	
	// Uncomment to view the trees used in test cases.
	
//	@Test
//	public void printTrees() {
//		BinarySearchTreeWithColor t = buildBlackHeight1();
//		System.out.println(t.toIndentString());
//		t = buildBlackHeight2();
//		System.out.println(t.toIndentString());
//	}
	
	
	@Test
	public void testIsRedBlackTreeEmptyTree() {
		BinarySearchTreeWithColor t = new BinarySearchTreeWithColor();
		assertTrue(t.isRedBlack());
		isRedBlackTreePoints += 1;
	}
	
	@Test
	public void testIsRedBlackTreeBlackBalance() {
		BinarySearchTreeWithColor t = buildBlackHeight1(); // black balanced, at black height of 1
		assertTrue(t.isRedBlack());
		t.insert(40, false);
		assertTrue(t.isRedBlack());
		t.insert(5, true);           // upsets black balance
		assertFalse(t.isRedBlack());
		isRedBlackTreePoints += 6;
		t.insert(15, true);
		t.insert(25, true);
		t.insert(35, true);
		t.insert(45, true);
		t.insert(55, true);
		t.insert(75, true);
		t.insert(78, false);
		assertFalse(t.isRedBlack());
		t.insert(90, true);          // restores black balance, black height is now 2
		assertTrue(t.isRedBlack());
		isRedBlackTreePoints += 5;
	}
	
	@Test
	public void testIsRedBlackTreeDoubleRed() {
		BinarySearchTreeWithColor t = new BinarySearchTreeWithColor(); 
		t.insert(50, true);
		t.insert(60, false);
		assertTrue(t.isRedBlack());
		t.insert(70, false);         // makes a double red
		assertFalse(t.isRedBlack());
		isRedBlackTreePoints += 3;
		t = buildBlackHeight1();
		assertTrue(t.isRedBlack());
		t.insert(40, false);
		assertTrue(t.isRedBlack());
		t.insert(35, false);         // makes a double red
		assertFalse(t.isRedBlack());
		isRedBlackTreePoints += 3;
		t = buildBlackHeight1();     // reset
		assertTrue(t.isRedBlack());
		t.insert(75, false); // makes a double red
		assertFalse(t.isRedBlack());
		t.insert(5, true);
		t.insert(15, true);
		t.insert(25, true);
		t.insert(35, true);
		t.insert(45, true);
		t.insert(55, true);
		t.insert(73, true);
		t.insert(78, true);
		assertFalse(t.isRedBlack());
		t.insert(90, true);          // restores black balance, but still has double red
		assertFalse(t.isRedBlack());
		isRedBlackTreePoints += 5;
	}
	
	@Test
	public void testIsRedBlackTreeRedRoot() {
		BinarySearchTreeWithColor t = buildBlackHeight1(); // root is black
		assertTrue(t.isRedBlack());
		t = buildBlackHeight2();             // root is black
		assertTrue(t.isRedBlack());
		t = new BinarySearchTreeWithColor();
		t.insert(50,false);                  // root is red
		assertFalse(t.isRedBlack());
		t.insert(40, true);
		t.insert(60, true);
		assertFalse(t.isRedBlack());         // root is still red
		isRedBlackTreePoints += 2;
	}
	


	@AfterClass
	public static void displayPoints() {
		System.out.printf("isRedBlackTree points:           %2d/25\n", isRedBlackTreePoints);
		System.out.printf("The 10 efficiency points will be checked by the grader\n");
	}
}