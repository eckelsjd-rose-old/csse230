package select;

import java.util.Random;

public class Quickselect {

	/**
	 * Selects the k-th largest element of the given array,
	 * using the quickselect algorithm.
	 * The given array should be left unchanged. (We provide an initial
	 * clone command that copies the givenArray over to a new array,
	 * so you can do whatever you want to the clone.)
	 * 
	 * Pivot selection: when partitioning a part of the array between
	 * index _low_ and index _high_, you should *always use* 
	 * 		array[_low_]
	 * as the pivot.
	 * 
	 * Solutions that completely sort the array will be given no credit!
	 * 
	 * @param givenArray
	 * @param k
	 */
	
	public static int select(int[] givenArray, int k) {
		int[] array = givenArray.clone();
		return select(array, k, 0, array.length - 1);
	}
	
	public static int select(int[] a, int k, int low, int high) {
		if (high <= low) {
			return a[low]; // base case
		}
		
		int pivot = partition(a, low, high);
		if (k > pivot) {
			return select(a, k, pivot + 1, high);
		} else if (k < pivot) {
			return select(a, k, low, pivot - 1);
		} 
		
		// pivot == k
		return a[pivot];
	}
	
	public static int partition(int[] a, int low, int high) {
		int pivot = a[low];
		int i = low + 1;
		int j = high;
		while (true) {
			while (a[i] < pivot && i < high) { i++; }
			while (a[j] > pivot && j > low) { j--; }
			if (i >= j) { break; }
			swap(a, i, j);
			i++;
			j--;
		}
		swap(a, low, j);
		return j;
	}
	
	private static void swap(int[] a, int i, int j) {
		int temp = a[j];
		a[j] = a[i];
		a[i] = temp;
	}
	
	/**
	 * This method intends to fill the given int array with data,
	 * arranging the data and returning an index in such a way that
	 * 		select(array, index)
	 * will run as long as possible.
	 * 
	 * @param example
	 * @return
	 */
	public static int worstCaseFillArray(int[] example) {
		// DONE: replace the following line with some code that will
		// populate the array, example, and return an index such that
		// select(example, index) will run as long as possible given
		// the array length of the provided array.
		for (int i = 0; i < example.length; i++) {
			example[i] = i;
		}
		return example.length - 1;
		//return randomFillArray(example);
	}
	
	/**
	 * Fills the given int array with random data in the range [0,array.length],
	 * returns the median index array.length/2.
	 * 
	 * @param example
	 * @return
	 */
	public static int randomFillArray(int[] example) {
		Random rand = new Random();
		int len = example.length;
		for (int i = 0; i < len; i++) {
			example[i] = rand.nextInt(len);
		}
		return len/2;
	}

}
