package averagenodevalue;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.Test;

public class Testing {

	private static int points = 0;
	// Used by unit tests when comparing doubles to handle round off error. 
	// The two values must be within DELTA of each other to be considered equal. 
	private double DELTA = 0.000000001;
	
	@Test
	public void testAverageValueEmptyAndRootOnly() {
		BinarySearchTree b1 = new BinarySearchTree();
		assertEquals(0.0, b1.averageValue(), DELTA);
		
		BinarySearchTree b2 = new BinarySearchTree();
		b2.insert(17);
		assertEquals(17, b2.averageValue(), DELTA);
		points += 2;
	}

	@Test
	public void testAverageValueTiny() {
		BinarySearchTree b1 = new BinarySearchTree();
		b1.insert(10);
		b1.insert(7);
		assertEquals(8.5, b1.averageValue(), DELTA);
		
		b1.insert(15);
		assertEquals(32.0/3, b1.averageValue(), DELTA);
		points += 2;
	}
	
	@Test
	public void testAverageValue() {
		BinarySearchTree b1 = new BinarySearchTree();
		b1.insert(15);
		b1.insert(10);
		b1.insert(20);
		assertEquals(15, b1.averageValue(), DELTA);
		b1.insert(5);
		b1.insert(2);
		b1.insert(7);
		assertEquals(59.0/6, b1.averageValue(), DELTA);
		b1.insert(12);
		b1.insert(18);
		b1.insert(50);
		b1.insert(40);
		b1.insert(30);
		assertEquals(19.0, b1.averageValue(), DELTA);

		
		
		
		
		
		
		points += 16;
	}
	
	

	@AfterClass
	public static void displayPoints() {
		System.out.println("------------------------------------------------------");
		System.out.printf("# AVERAGE_VALUE earned %d/20 points\n", points);
		System.out.println("# Efficiency: to earn the other 15 points, can only recurse once to the left and once to the right from any node.");
		System.out.println("Must return multiple things from recursive call to make this happen");
		System.out.println("------------------------------------------------------");
		System.out.println();
	}
}