package valueMatchingIndex;

import java.util.ArrayList;

public class SortedArray {

	public static Integer valueMatchingIndex(ArrayList<Integer> arr) {
		// TODO: Correctly implement this method!
		// See exam sheet for spec.
		int num = helper(arr, 0, arr.size() - 1);
		if (num == -1) { return null; }
		return num;
	}
	
	public static int helper(ArrayList<Integer> arr, int start, int end) {
		
		if (start == end - 1) {
			if (arr.get(start) == start) {return start; }
			if (arr.get(end) == end) {return end; }
			else { return -1; }
		} 
		
		int midindex = (end+start) / 2;
		int middle = arr.get(midindex);
		if (middle > midindex) {
			end = midindex;
		}
		if (middle < midindex) {
			start = midindex;
		}
		if (middle == midindex) {
			return midindex;
		}
		
		return helper(arr, start, end);
	}

}
