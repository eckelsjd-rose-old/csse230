package median;

import java.util.Scanner;

public class MedianAlgorithm {

	/**
	 * 
	 * Algorithm to find the overall median of two sorted arrays. Runtime is
	 * O(logN), i.e. the median is found without needing to create a new array.
	 * Assumes input arrays are of same size N.
	 *
	 * @param a1 - a sorted array with size N
	 * @param a2 - a sorted array with size N
	 * @return median of the two arrays in O(logN)
	 */
	public static double findMedian(int[] a1, int[] a2) {
		MutableBool bool = new MutableBool();
		return medianRec(a1, a2, 0, a1.length - 1, bool);
	}

	private static double medianRec(int[] A, int[] B, int start, int end, MutableBool bool) {
		int mid = (end + 1 + start) / 2;
		int freeSpots = (A.length - 1) - mid;
		if (start == end) {
			// we have already exhausted the second array, this must be the right one
			if (bool.flipped) {
				double firstMedian = A[start];
				double secondMedian = A[start+1];
				return (firstMedian + secondMedian) / 2;
			}
			bool.flipped = true;
			return medianRec(B, A, 0, B.length - 1,bool);
		}

		// we have reached the far right of A array
		if (freeSpots == 0) {
			if (A[end] < B[0]) {
				double firstMedian = A[end];
				double secondMedian = B[0];
				return (firstMedian + secondMedian) / 2;
			}
			// median is not in A
			bool.flipped = true;
			return medianRec(B, A, 0, B.length - 1, bool);
		}

		// exhausted searching A array, switch and search B
		if (freeSpots == A.length - 1) {
			bool.flipped = true;
			return medianRec(B, A, 0, B.length - 1, bool);
		}

		if (B[freeSpots - 1] < A[mid]) {
			if (B[freeSpots] < A[mid]) {
				// chosen median is too large
				return medianRec(A, B, start, mid - 1, bool);
			}
			// chosen median is the first median
			double firstMedian = A[mid];
			double secondMedian = (B[freeSpots] < A[mid + 1]) ? B[freeSpots] : A[mid + 1];
			return (firstMedian + secondMedian) / 2;
		}
		// chosen median is too small
		return medianRec(A, B, mid, end, bool);
	}
	
	public static void main(String[] args) {
		System.out.println("Welcome to the MedianAlgorithm.");
		System.out.println("Array format: 1,2,3,4,5");
		System.out.println("Type 'quit' to exit");
		Scanner scanner = new Scanner(System.in);
		int[] a1;
		int[] a2;
		outer: while (true) {
			// get first array
			System.out.println("Enter first array > ");
			String line = scanner.nextLine();
			String[] s = line.split(",");
			int length = s.length;
			a1 = new int[length];
			a2 = new int[length];
			int i = 0;
			for (String str : s) {
				if (str.equals("quit")) {
					break outer;
				}
				try {
					int next = Integer.parseInt(str);
					a1[i++] = next;
				} catch (NumberFormatException nfe) {
					System.out.println(nfe);
					System.out.println("Proper format: 1,2,3,4,5");
					continue outer;
				}
			}
			
			// get second array
			System.out.println("Enter second array > ");
			line = scanner.nextLine();
			s = line.split(",");
			
			// check size
			if (s.length != length) {
				System.out.println("Array's must be same length. Try again.");
				continue outer;
			}
			
			i = 0;
			for (String str : s) {
				if (str.equals("quit")) {
					break outer;
				}
				try {
					int next = Integer.parseInt(str);
					a2[i++] = next;
				} catch (NumberFormatException nfe) {
					System.out.println(nfe);
					System.out.println("Proper format: 1,2,3,4,5");
					continue outer;
				}
			}
			
			// find median
			double median = MedianAlgorithm.findMedian(a1, a2);
			System.out.println("Median: " + median);
		}
		scanner.close();
		System.out.println("Goodbye!");
	}

}
