package bst;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Random;

import org.junit.AfterClass;
import org.junit.Test;


public class Testing {

	private static int countNumberInSetPoints = 0;
	private static int removeSmallestDescendentPoints = 0;
	private static int sumOfKSmallestPoints = 0;
	

	// *************************************************************
	// JUnit helper operations
	// *************************************************************
	
	private void loadUpBst(BinarySearchTree bst1, Integer a1[]) {
		for (int k = 0; k < a1.length; k++) {
			bst1.insert(a1[k]);
		} // end for		
	} // loadUpBst
		

	// *************************************************************
	// countNumberInSet JUnit tests
	// *************************************************************
	
	@Test
	public void testCountNumberInSetEmptyTreeAndSet() {
		HashSet<Integer> emptySet = new HashSet<Integer>();
		BinarySearchTree bst1 = new BinarySearchTree();
		
		assertEquals(0, bst1.countNumberInSet(emptySet));		
		countNumberInSetPoints += 2;
	} // testCountNumberInSetEmptyTreeAndSet

	// -------------------------------------------------------------
	
	@Test
	public void testCountNumberInSetNonEmptyTreeNoMatches() {
		final Integer setData[] = { 2, 53, 61, 17, 89, 11, 3, 43, 31 }; 
		final Integer treeData[] = {77, 37, 93, 35, 52, 90, 94, 36 }; 
		
		HashSet<Integer> nonEmptySet = new HashSet<Integer>(Arrays.asList(setData));
		BinarySearchTree nonEmptyBst = new BinarySearchTree();
		
		this.loadUpBst(nonEmptyBst, treeData);
		
		assertEquals(0, nonEmptyBst.countNumberInSet(nonEmptySet));		
		countNumberInSetPoints += 3;
	} // testCountNumberInSetNonEmptyTreeNoMatches
	
	// -------------------------------------------------------------
	
	@Test
	public void testCountNumberInSetNonEmptyTree3Matches() {
		// Match on 77, 36, 93 - tree's root, a leaf, an interior node
		final Integer setData[] = { 77, 36, 93, 2, 53, 61, 17, 89, 11 }; 
		final Integer treeData[] = {77, 37, 93, 35, 52, 90, 94, 36 }; 
		final int expectedNumberOfMatches = 3;
		
		HashSet<Integer> nonEmptySet = new HashSet<Integer>(Arrays.asList(setData));
		BinarySearchTree nonEmptyBst = new BinarySearchTree();
		
		this.loadUpBst(nonEmptyBst, treeData);
		
		assertEquals(expectedNumberOfMatches,nonEmptyBst.countNumberInSet(nonEmptySet));		
		countNumberInSetPoints += 10;
	} // testCountNumberInSetNonEmptyTree3Matches
	
	
	
	// *************************************************************
	// removeSmallestDescendent JUnit tests
	// *************************************************************
	
	@Test
	public void testRemoveSmallestDescendentAtLeaf() {
		final Integer treeData[] = { 11, 37, 35, 77, 20, 52, 93, 29, 90, 94 }; 
		BinarySearchTree nonEmptyBst = new BinarySearchTree();
		
		this.loadUpBst(nonEmptyBst, treeData);
		assertEquals("11202935375277909394",nonEmptyBst.toString()); // tree before remove
		
		nonEmptyBst.removeSmallestDescendent(90);
		assertEquals("112029353752779394",nonEmptyBst.toString()); // tree after remove	
		removeSmallestDescendentPoints += 5;
	} //testRemoveSmallestDescendentAtLeaf
	
	// -------------------------------------------------------------
	
	@Test
	public void testRemoveSmallestDescendentAtRoot() {
		final Integer treeData[] = { 11, 37, 35, 77, 20, 52, 93, 29, 90, 94 }; 
		BinarySearchTree nonEmptyBst = new BinarySearchTree();
		
		this.loadUpBst(nonEmptyBst, treeData);
		assertEquals("11202935375277909394",nonEmptyBst.toString()); // tree before remove
		
		nonEmptyBst.removeSmallestDescendent(11);
		assertEquals("202935375277909394",nonEmptyBst.toString()); // tree after remove		
		removeSmallestDescendentPoints += 5;	
	} //testRemoveSmallestDescendentAtRoot
	
	// -------------------------------------------------------------
	
	@Test
	public void testRemoveSmallestDescendentAtInteriorNode() {
		final Integer treeData[] = { 11, 37, 35, 77, 20, 52, 93, 29, 90, 94 }; 
		BinarySearchTree nonEmptyBst = new BinarySearchTree();
		
		this.loadUpBst(nonEmptyBst, treeData);		
		assertEquals("11202935375277909394",nonEmptyBst.toString()); // tree before remove
		
		nonEmptyBst.removeSmallestDescendent(37);
		assertEquals("112935375277909394",nonEmptyBst.toString()); // tree after remove		
		removeSmallestDescendentPoints += 5;		
	} //testRemoveSmallestDescendentAtInteriorNode


	// *************************************************************
	// sumOfKSmallest JUnit tests
	// *************************************************************
	
	@Test
	public void testSumOfKSmallestCorrectness() {
		final Integer treeData[] = { 11, 37, 35, 77, 20, 52, 93, 29, 90, 94 }; 
		BinarySearchTree nonEmptyBst = new BinarySearchTree();

		this.loadUpBst(nonEmptyBst, treeData);	
		assertEquals(31,nonEmptyBst.sumOfKSmallest(2));
		assertEquals(132,nonEmptyBst.sumOfKSmallest(5));
		sumOfKSmallestPoints += 5;
		
		nonEmptyBst.insert(15);
		nonEmptyBst.insert(31);
		assertEquals(26,nonEmptyBst.sumOfKSmallest(2));
		assertEquals(106,nonEmptyBst.sumOfKSmallest(5));
		sumOfKSmallestPoints += 5;
		
		assertEquals(0,nonEmptyBst.sumOfKSmallest(0));
		sumOfKSmallestPoints += 2;
	} // testSumOfKSmallestCorrectness
	
	// -------------------------------------------------------------

		@Test
		public void testSumOfKSmallestEfficiency() {
			BinarySearchTree t = new BinarySearchTree();
			int numToAdd = 200000;
			int maxVal = 100000000;
			PriorityQueue<Integer> pq = new PriorityQueue<Integer>();
			
			
			Random rand = new Random();
			for (int i = 0; i < numToAdd; i++) {
				int toAdd = rand.nextInt(maxVal);
				t.insert(toAdd);
				pq.add(toAdd);
			}
			
			int sumOf10 = 0;
			for (int i = 0; i < 10; i++) {
				sumOf10 += pq.poll();
			}
			
			long totalTime = 0;
			final long startTime = System.currentTimeMillis();
			assertEquals(sumOf10,t.sumOfKSmallest(10));
			totalTime += System.currentTimeMillis() - startTime;
			if (totalTime < 5) {
				System.out.printf("sumOfKSmallest efficiency PASSED: %d ms < 2 ms\n",totalTime);
			} else {
				System.out.printf("sumOfKSmallest efficiency FAILED: %d ms >= 2 ms\n",totalTime);
			}
			assertTrue(totalTime < 2); 
			sumOfKSmallestPoints += 8;
		} // testSumOfKSmallestEfficiency

	// -------------------------------------------------------------


	@AfterClass
	public static void displayPoints() {
		System.out.printf("%2d/15 countNumberInSet points\n", countNumberInSetPoints);

		System.out.printf("%2d/15 removeSmallestDescendent correctness points\n", removeSmallestDescendentPoints);
		System.out.printf(" ?/ 5 removeSmallestDescendent efficiency will be checked by the instructor\n");

		System.out.printf("%2d/20 sumOfKSmallest points\n", sumOfKSmallestPoints);
		System.out.printf(" ?/ 5 overall for elegance will be checked by the instructor\n");
	
	}
}
