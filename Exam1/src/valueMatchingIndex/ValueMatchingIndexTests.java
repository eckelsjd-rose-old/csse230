package valueMatchingIndex;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Random;


import org.junit.AfterClass;
import org.junit.Test;

/**
 * @author Nate Chenette
 */

public class ValueMatchingIndexTests {
	private static int points = 0;
	
	private ArrayList<Integer> createAL(int[] primArr) {
		ArrayList<Integer> list = new ArrayList<>();
		for (int i = 0; i < primArr.length; i++) {
			list.add(primArr[i]);
		}
		return list;
	}
	
	@Test
	public void testValueMatchingIndexFound() {
		int[] primArr1 = {-2,-1,2,6,9,22};
		ArrayList<Integer> arr = createAL(primArr1);
		assertEquals(new Integer(2),SortedArray.valueMatchingIndex(arr));
		int[] primArr2 = {-14,-11,-3,2,4,7,13};
		arr = createAL(primArr2);
		assertEquals(new Integer(4),SortedArray.valueMatchingIndex(arr));
		int[] primArr3 = {-90,-80,-70,-60,-50,-40,-30,-20,-10,0,10,20,30,40,50,60,70,80,90};
		arr = createAL(primArr3);
		assertEquals(new Integer(10),SortedArray.valueMatchingIndex(arr));
		points += 4;
	}
	

	@Test
	public void testValueMatchingIndexNotFound() {
		int[] primArr4 = {-2,-1,2,6,9,22};
		ArrayList<Integer> arr = createAL(primArr4);
		assertEquals(new Integer(2),SortedArray.valueMatchingIndex(arr));
		int[] primArr5 = {-2,-1,3,6,9,22};
		arr = createAL(primArr5);
		assertEquals(null,SortedArray.valueMatchingIndex(arr));
		int[] primArr6 = {-14,-11,-3,2,3,7,13};
		arr = createAL(primArr6);
		assertEquals(null,SortedArray.valueMatchingIndex(arr));
		int[] primArr7 = {-100,-90,-80,-70,-60,-50,-40,-30,-20,-10,0,10,20,30,40,50,60,70,80,90,100};
		arr = createAL(primArr7);
		assertEquals(null,SortedArray.valueMatchingIndex(arr));
		points += 4;
	}
	

	private ArrayList<Integer> createBigRandomAL(int len) {
		ArrayList<Integer> arr = new ArrayList<>();
		Random rand = new Random();
		int val = -len / 2;
		for (int i = 0; i < len; i++) {
			val += 1 + rand.nextInt(3); // adds either 1, 2, or 3, randomly
			arr.add(val);
		}
		return arr;
	}

	@Test(timeout = 10000)
	public void testValueMatchingIndexEfficiency() {
		// First, run correctness tests
		int[] primArr1 = {-2,-1,2,6,9,22};
		ArrayList<Integer> arr = createAL(primArr1);
		assertEquals(new Integer(2),SortedArray.valueMatchingIndex(arr));
		int[] primArr2 = {-14,-11,-3,2,4,7,13};
		arr = createAL(primArr2);
		assertEquals(new Integer(4),SortedArray.valueMatchingIndex(arr));
		int[] primArr3 = {-90,-80,-70,-60,-50,-40,-30,-20,-10,0,10,20,30,40,50,60,70,80,90};
		arr = createAL(primArr3);
		assertEquals(new Integer(10),SortedArray.valueMatchingIndex(arr));
		int[] primArr4 = {-2,-1,2,6,9,22};
		arr = createAL(primArr4);
		assertEquals(new Integer(2),SortedArray.valueMatchingIndex(arr));
		int[] primArr5 = {-2,-1,3,6,9,22};
		arr = createAL(primArr5);
		assertEquals(null,SortedArray.valueMatchingIndex(arr));
		int[] primArr6 = {-14,-11,-3,2,3,7,13};
		arr = createAL(primArr6);
		assertEquals(null,SortedArray.valueMatchingIndex(arr));
		int[] primArr7 = {-100,-90,-80,-70,-60,-50,-40,-30,-20,-10,0,10,20,30,40,50,60,70,80,90,100};
		arr = createAL(primArr7);
		assertEquals(null,SortedArray.valueMatchingIndex(arr));
		// Efficiency test
		long totalTime = 0;
		for (int j = 0; j < 20; j++) {
			ArrayList<Integer> bigArr = createBigRandomAL(10000);
			final long startTime = System.currentTimeMillis();
			for (int i = 0; i < 4000; i++) {
				SortedArray.valueMatchingIndex(bigArr);
			}
			totalTime += System.currentTimeMillis() - startTime;
		}
		if (totalTime < 100) {
			System.out.printf("ValueMatchingIndex efficiency PASSED: %d ms < 100 ms\n",totalTime);
		} else {
			System.out.printf("ValueMatchingIndex efficiency FAILED: %d ms >= 100 ms\n",totalTime);
		}
		assertTrue(totalTime < 100); // Must be < 100 ms to pass efficiency test  
		points += 10;
	}
	
	@AfterClass
	public static void showPoints() {
		System.out.printf("ValueMatchingIndex points: %d of 18\n", points);
	}
	
}
