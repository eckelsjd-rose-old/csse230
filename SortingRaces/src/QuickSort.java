import java.util.Random;

public class QuickSort {
	private static Random rand = new Random(17);
	
	public static void quicksort(int[] a) {
		quicksort(a, 0, a.length - 1);
	}
	
	public static void quicksort(int[] a, int low, int high) {
		if (high <= low) {
			return; // base case
		}
		
		int pivot = partition(a, low, high);
		quicksort(a, low, pivot - 1);
		quicksort(a, pivot + 1, high);
	}
	
	public static int partition(int[] a, int low, int high) {
		int pivotIndex;
		if ((high - low + 1) >= 3) {
			pivotIndex = getRandomThree(a, low, high);
		} else {
			pivotIndex = low;
		}
		int pivot = a[pivotIndex];
		swap(a, low, pivotIndex);
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

	private static int getRandomThree(int[] array, int low, int high) {
		int a = low + rand.nextInt(high - low + 1);
		int b = low + rand.nextInt(high - low + 1);
		int c = low + rand.nextInt(high - low + 1);
		if (((array[a] >= array[b]) && (array[a] <= array[c])) || ((array[a] >= array[c]) && (array[a] <= array[b]))) {
			return a;
		} else if (((array[b] >= array[a]) && (array[b] <= array[c])) || ((array[b] >= array[c]) && (array[b] <= array[a]))) {
			return b;
		} else if (((array[c] >= array[b]) && (array[c] <= array[a])) || ((array[c] >= array[a]) && (array[c] <= array[b]))) {
			return c;
		}
		return 0;
	}
}
