package heap;

/**
 * 
 * A class containing the static IsHeap method.
 *
 * @author Nate Chenette and <<TODO: Joshua Eckels>>.
 */

public class MinIndexOfHeapRoot {

	/**
	 * Using the standard 1-indexed "heap structure" interpretation of the given
	 * input array of ints, returns the lowest index x such that the tree rooted at
	 * x forms a valid (min) binary heap.
	 * 
	 * @param arr, the input array. Note that the 0th element is irrelevant to the
	 *             method, and the array will always be "filled" with data, so that
	 *             the heap-structure interpretation of the array will have
	 *             arr.length() - 1 elements in the corresponding tree.
	 * @return the smallest index
	 */
	public static int minIndexOfHeapRoot(int[] arr) {
		for (int i = 1; i < arr.length - 1; i++) {
			if (checkHeap(arr, i)) {
				return i;
			}
		}
		return -1;
	}

	public static boolean checkHeap(int[] arr, int i) {
		int left = 2 * i;
		int right = 2 * i + 1;
		if (right >= arr.length) {
			// edge case
			if (right == arr.length) {
				return (arr[left] > arr[i]);
			}
			return true;
		}
		if (arr[left] < arr[i] || arr[right] < arr[i]) {
			return false;
		}
		return checkHeap(arr, left) && checkHeap(arr, right);
	}

}
