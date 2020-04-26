import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.TreeMap;


/**
 * This program runs various sorts and gathers timing information on them.
 *
 * @author <<YOUR NAMES HERE>>
 *         Created May 7, 2013.
 */
public class SortRunner {
	private static Random rand = new Random(17); // uses a fixed seed for debugging. Remove the parameter later.
	
	/**
	 * Starts here.
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		// array size must be an int. You will need to use something much larger
		int size = 100; 
		
		// Each integer will have the range from [0, maxValue). If this is significantly higher than size, you
		// will have small likelihood of getting duplicates.
		int maxValue = Integer.MAX_VALUE; 
		// Test 1: Array of random values.
		System.out.println("random:");
		int[] randomIntArray = getRandomIntArray(size, maxValue);
		runAllSortsForOneArray(randomIntArray);
		
		// TODO: Tests 2-4
		// Generate the three other types of arrays (shuffled, almost sorted, almost reverse sorted)
		// and run the sorts on those as well.
		System.out.println("\nshuffled:");
		int[] shuffledIntArray = getUniqueElementArray(size);
		runAllSortsForOneArray(shuffledIntArray);
		
		System.out.println("\nalmost sorted:");
		int[] almostSortedArray = almostSortedArray(size);
		runAllSortsForOneArray(almostSortedArray);
		
		System.out.println("\nreversed:");
		int[] almostSortedReversed = reverse(almostSortedArray);
		runAllSortsForOneArray(almostSortedReversed);
		
		
		
	}

	/**
	 * 
	 * Runs all the specified sorts on the given array and outputs timing results on each.
	 *
	 * @param array
	 */
	private static void runAllSortsForOneArray(int[] array) {
		long startTime, elapsedTime; 
		boolean isSorted = false;

		// TODO: Read this.
		// We prepare the arrays. This can take as long as needed to shuffle items, convert
		// back and forth from ints to Integers and vice-versa, since you aren't timing this 
		// part. You are just timing the sort itself.
//		System.out.println(Arrays.toString(array));
		int[] sortedIntsUsingDefaultSort = array.clone();
		Integer[] sortedIntegersUsingDefaultSort = copyToIntegerArray(array);
		Integer[] sortedIntegersUsingHeapSort = sortedIntegersUsingDefaultSort.clone();
		Integer[] sortedIntegersUsingTreeSort = sortedIntegersUsingDefaultSort.clone();
		int[] sortedIntsUsingQuickSort = array.clone();

		int size = array.length;
		
		// What is the default sort for ints? Read the javadoc.
		startTime = System.currentTimeMillis();  
		Arrays.sort(sortedIntsUsingDefaultSort); 
		elapsedTime = (System.currentTimeMillis() - startTime);
		isSorted = verifySort(sortedIntsUsingDefaultSort);
		displayResults("int", "the default sort", elapsedTime, size, isSorted);
//		System.out.println(Arrays.toString(sortedIntsUsingDefaultSort));
		// What is the default sort for Integers (which are objects that wrap ints)?
		startTime = System.currentTimeMillis();  
		Arrays.sort(sortedIntegersUsingDefaultSort); 
		elapsedTime = (System.currentTimeMillis() - startTime);
		isSorted = verifySort(sortedIntegersUsingDefaultSort);
		displayResults("Integer", "the default sort", elapsedTime, size, isSorted);

		// Sort using the following methods, and time and verify each like done above. 
		// DONE: a simple sort that uses a TreeSet but handles a few duplicates gracefully.
		startTime = System.currentTimeMillis();  
		treeSort(sortedIntegersUsingTreeSort);
		elapsedTime = (System.currentTimeMillis() - startTime);
		isSorted = verifySort(sortedIntegersUsingTreeSort);
		displayResults("int", "the tree sort", elapsedTime, size, isSorted);
		
		// TODO: your implementation of quick sort. I suggest putting this in a static method in a Quicksort class.
		startTime = System.currentTimeMillis();  
		QuickSort.quicksort(sortedIntsUsingQuickSort);
		elapsedTime = (System.currentTimeMillis() - startTime);
		isSorted = verifySort(sortedIntsUsingQuickSort);
		displayResults("int", "the quick sort", elapsedTime, size, isSorted);
		
		// TODO: your BinaryHeap sort. You can put this sort in a static method in another class. 
		startTime = System.currentTimeMillis();  
		BinaryHeap.sort(sortedIntegersUsingHeapSort, Integer.class);
		elapsedTime = (System.currentTimeMillis() - startTime);
		isSorted = verifySort(sortedIntegersUsingHeapSort);
		displayResults("int", "the heap sort", elapsedTime, size, isSorted);
		
	}
	

	private static void displayResults(String typeName, String sortName, long elapsedTime, int size,  boolean isSorted) {
		if (isSorted) {
			System.out.printf("Sorted %.1e %ss using %s in %d milliseconds\n", (double)size, typeName, sortName, elapsedTime);
		} else {
			System.out.println("ARRAY NOT SORTED");
		}
	}
	
	/**
	 * Checks in O(n) time if this array is sorted.
	 *
	 * @param a An array to check to see if it is sorted.
	 */
	private static boolean verifySort(int[] a) {
		for (int i = 0; i < a.length - 1; i++) {
			if (a[i+1] < a[i]) { return false; }
		}
		return true;
	}

	/**
	 * Checks in O(n) time if this array is sorted.
	 *
	 * @param a An array to check to see if it is sorted.
	 */
	@SuppressWarnings("boxing")
	private static boolean verifySort(Integer[] a) {
		for (int i = 0; i < a.length - 1; i++) {
			if (a[i+1] < a[i]) { return false; }
		}
		return true;
	}

	/**
	 * Copies from an int array to an Integer array.
	 *
	 * @param randomIntArray
	 * @return A clone of the primitive int array, but with Integer objects.
	 */
	private static Integer[] copyToIntegerArray(int[] ints) {
		Integer[] integers = new Integer[ints.length];
		for (int i = 0; i < ints.length; i++) {
			integers[i] = ints[i];
		}
		return integers;
	}

	/**
	 * Creates and returns an array of random ints of the given size.
	 *
	 * @return An array of random ints.
	 */
	private static int[] getRandomIntArray(int size, int maxValue) {
		int[] a = new int[size];
		for (int i = 0; i < size; i++) {
			a[i] = rand.nextInt(maxValue);
		}
		return a;
	}

	/**
	 * Creates a shuffled random array.
	 *
	 * @param size
	 * @return An array of the ints from 0 to size-1, all shuffled
	 */
	@SuppressWarnings("boxing")
	private static int[] getUniqueElementArray(int size) {
		List<Integer> a = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			a.add(i);
		}
		Collections.shuffle(a);
		int[] array = new int[a.size()];
		for (int i = 0; i < a.size(); i++) {
			array[i] = a.get(i);
		}
		return array;
	}
	
	private static int[] almostSortedArray(int size) {
		int[] a = new int[size];
		for (int i = 0; i < size; i++) {
			a[i] = i;
		}
		int index1;
		int index2;
		for (int j = 0; j < 0.01*a.length; j++) {
			index1 = rand.nextInt(size-1);
			index2 = rand.nextInt(size-1);
			int temp = a[index1];
			a[index1] = a[index2];
			a[index2] = temp;
		}
		return a;
	}
	
	private static int[] reverse(int[] array) {
		int length = array.length;
		int[] a = new int[length];
		for (int i = 0; i < length - 1; i++) {
			a[i] = array[length - 1 - i];
		}
		return a;
	}
	
	@SuppressWarnings("boxing")
	public static void treeSort(Integer[] array) {
		TreeMap<Integer, Integer> tree = new TreeMap<>();
		
 		for (int i = 0; i < array.length; i++) {
 			if (tree.containsKey(array[i])) {
 				tree.put(array[i], tree.get(array[i]) + 1);
 			} else {
 				tree.put(array[i], 1);
 			}
		}
 		
 		int i = 0;
 		for (Integer key : tree.keySet()) {
 			for (int j = 0; j < tree.get(key); j++) {
 				array[i] = key;
 				i++;
 			}
 		}
	}
	
}
