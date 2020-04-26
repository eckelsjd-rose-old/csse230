package search;

public class EfficientSearch {
	public static int search(int[] sortedArray, int searchTerm) {
		// TODO You recognize this starting code as a SEQUENTIAL (one at a time, in-order) search. 
		// It runs in O(n) worst-case time.
		// So if there are 1,000,000 items in the array, it will have to make that many comparisons in the worst case.
		// 
		// Since the array is sorted, 
		// replace it with the much-more efficient BINARY search, which runs in O(log n) worst case time. 
		// If there are 1,000,000 items in the array, it will only have to make ~20 comparisons.
		//
		// You can look up binary search algorithm from the CSSE220 materials
		// or here: https://en.wikipedia.org/wiki/Binary_search_algorithm#Procedure
		
//		for (int i = 0; i < sortedArray.length; i++) {
//			if (searchTerm == sortedArray[i]) {
//				return i;
//			}
//		}
		int num = helper(sortedArray, searchTerm, 0, sortedArray.length - 1);
		return num;
		
	}
	
	public static int helper(int[]sortedArray, int searchTerm, int start, int end) {
		
		if (start == end - 1) {
			if (sortedArray[start] == searchTerm) {return start; }
			if (sortedArray[end] == searchTerm) {return end; }
			else { return -1; }
		} 
		
		
		int midindex = (end+start) / 2;
		int middle = sortedArray[midindex];
		if (middle > searchTerm) {
			end = midindex;
		}
		if (middle < searchTerm) {
			start = midindex;
		}
		if (middle == searchTerm) {
			return midindex;
		}
		
		int rectnum = helper(sortedArray, searchTerm, start, end);
		return rectnum;
	}
}
