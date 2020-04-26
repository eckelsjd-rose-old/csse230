package select;

import static org.junit.Assert.*;

import java.lang.reflect.Array;
import java.util.Iterator;

import org.junit.AfterClass;
import org.junit.Test;



public class QuickselectTest {

	private static int selectPoints = 0;
	private static int selectTimingPoints = 0;
	
	@Test
	public void testQuickselectSmall() {	
		int[] test = new int[] { 5, 1, 8, 2, 7, 3, 9 };
		assertEquals(1,Quickselect.select(test, 0));
		assertEquals(2,Quickselect.select(test, 1));
		selectPoints += 2;
		assertEquals(3,Quickselect.select(test, 2));
		assertEquals(5,Quickselect.select(test, 3));
		assertEquals(7,Quickselect.select(test, 4));
		assertEquals(8,Quickselect.select(test, 5));
		assertEquals(9,Quickselect.select(test, 6));
		selectPoints += 4;
	}
	
	@Test
	public void testQuickselectExample() {	
		int[] test = new int[] { 6, 4, 13, 3, 3, 3, 8, 7, 5, 1, 6, 12, -4 };
		assertEquals(-4,Quickselect.select(test, 0));
		assertEquals(1,Quickselect.select(test, 1));
		selectPoints += 2;
		assertEquals(3,Quickselect.select(test, 2));
		assertEquals(3,Quickselect.select(test, 3));
		assertEquals(3,Quickselect.select(test, 4));
		assertEquals(4,Quickselect.select(test, 5));
		assertEquals(5,Quickselect.select(test, 6));
		assertEquals(6,Quickselect.select(test, 7));
		assertEquals(6,Quickselect.select(test, 8));
		assertEquals(7,Quickselect.select(test, 9));
		assertEquals(8,Quickselect.select(test, 10));
		assertEquals(12,Quickselect.select(test, 11));
		assertEquals(13,Quickselect.select(test, 12));
		selectPoints += 4;
	}
	
	
	/**
	 * Tests the runtime of the quickselect algorithm on two cases:
	 *  - case "Random": array filled with random data, select median
	 *  - case "Worst": array filled with ?????, select ??
	 *  
	 *  [It's your job to decide how to fill out the worst-case array,
	 *  and which index of it to select.]
	 *  
	 *  The test will pass if your worst-case array and index cause select
	 *  to run at least twice as slow as the random case.
	 */
	@Test
	public void testQuickselectWorstCaseVsRandomCase() {
		int arrLen = 300;  // Size of test arrays. Do not change this value
		int numReps = 100; // Number of times run. Do not change this value

		int[] exampleWorst = (int[]) Array.newInstance(int.class, arrLen);
		int indexWorst = Quickselect.worstCaseFillArray(exampleWorst);
		Long beforeTimeWorst = System.currentTimeMillis();
		for (int i = 0; i < numReps; i++) {
			int[] exampleWorstCopy = exampleWorst.clone();
			Quickselect.select(exampleWorstCopy, indexWorst);
		}
		Long afterTimeWorst = System.currentTimeMillis();
		Long totalTimeWorst = afterTimeWorst - beforeTimeWorst;

		int[] exampleRandom = (int[]) Array.newInstance(int.class, arrLen);
		// The next line of code calls a method that you should implement
		// in the Quickselect class, so that the upcoming select() call
		// runs as slowly as possible.
		int indexRandom = Quickselect.randomFillArray(exampleRandom);
		Long beforeTimeRandom = System.currentTimeMillis();
		for (int i = 0; i < numReps; i++) {
			int[] exampleRandomCopy = exampleRandom.clone();
			Quickselect.select(exampleRandomCopy, indexRandom);
		}
		Long afterTimeRandom = System.currentTimeMillis();
		Long totalTimeRandom = afterTimeRandom - beforeTimeRandom;
		
		if (totalTimeWorst >= 2 * totalTimeRandom ) {
			System.out.println("quickselect worst vs. random: PASS. " + Long.toString(totalTimeWorst) + "ms >= 2 * " + Long.toString(totalTimeRandom) + "ms");
		} else {
			System.out.println("quickselect worst vs. random: FAIL. " + Long.toString(totalTimeWorst) + "ms < 2 * " + Long.toString(totalTimeRandom) + "ms");
		}
		assertTrue(totalTimeWorst >= 2 * totalTimeRandom);
		selectTimingPoints += 8;
	}

	@AfterClass
	public static void displayPoints() {
		System.out.println("----------------------------------------------------------------------");
		System.out.printf("2. %2d/12  quickselect correctness points        \n", selectPoints);
		if (selectPoints < 12) {
			System.out.println("    0/ 8  Timing points cannot be earned until select is correct");
		} else {
			System.out.printf("    %1d/ 8  quickselect timing points        \n", selectTimingPoints);
		}
		System.out.println("----------------------------------------------------------------------");
	}
}
