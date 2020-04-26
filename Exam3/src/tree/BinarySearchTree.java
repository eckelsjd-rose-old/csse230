package tree;

/**
 * An implementation of a binary search tree, containing Character data.
 */


public class BinarySearchTree {	
	private BinaryNode root;
	private final BinaryNode NULL_NODE = new BinaryNode(null);

	public BinarySearchTree() {
		root = NULL_NODE;
	} // BinarySearchTree

	// The next methods are used by the unit tests
	public void insert(Character e) {
		root = root.insert(e);
	} // insert

	@Override
	public String toString() {
		return root.toString();
	} // toString
	
	// recursion method traverses each node exactly once.
	// use SizeTracker container object for multiple return values
	public int netSizeDiff() {
		SizeTracker tracker = this.root.netSizeDiff();
		return tracker.diff;
	}
	
	public int netBalance() {
		HeightTracker tracker = this.root.netBalance();
		return tracker.diff;	
	}
	
	/*
	 * Wrapper class to track size on the way back up
	 * to prevent having to call .size() method each time.
	 * 
	 * We want the tracker to track two things on way back up tree:
	 * 1. Size of the subtree: 				 subSize
	 * 2. Result of N(Tright) -N(Tleft):     diff
	 * 
	 */
	class SizeTracker {
		private int diff;
		private int subSize;
		
		SizeTracker() {
			this.diff = 0;
			this.subSize = 0;
		}
		
		SizeTracker(int size, int diff) {
			this.diff = diff;
			this.subSize = size;
		}
	}
	
	/*
	 * Wrapper class to track height on the way back up
	 * to prevent having to call .height() method each time.
	 * 
	 * We want the tracker to track two things on way back up tree:
	 * 1. height of the subtree: 			  subHeight
	 * 2. Result of H(Tright) - H(Tleft):     diff
	 */
	class HeightTracker {
		private int diff;
		private int subHeight;
		
		HeightTracker() {
			this.diff = 0;
			this.subHeight = 0;
		}
		
		HeightTracker(int height, int diff) {
			this.diff = diff;
			this.subHeight = height;
		}
	}

	// ----------------------------
	// /////////////// BinaryNode
	// ----------------------------
	public class BinaryNode {
		public Character data;
		public BinaryNode left;
		public BinaryNode right;

		public BinaryNode(Character element) {
			this.data = element;
			this.left = NULL_NODE;
			this.right = NULL_NODE;
		} // BinaryNode
		
		public HeightTracker netBalance() {
			// base case
			if (this == NULL_NODE) {
				return new HeightTracker(-1,0);
			}
			HeightTracker leftT = this.left.netBalance();
			HeightTracker rightT = this.right.netBalance();
			int thisHeight = Integer.max(leftT.subHeight, rightT.subHeight) + 1;
			int totalDiff = leftT.diff + rightT.diff + (rightT.subHeight - leftT.subHeight);
			return new HeightTracker(thisHeight, totalDiff);
//			int diff = this.right.height() - this.left.height();
//			return diff + this.left.netBalance() + this.right.netBalance();
		}
		
		public int height() {
			if (this == NULL_NODE) {
				return -1;
			}
			return 1 + Integer.max(this.left.height(), this.right.height());
		}

		public SizeTracker netSizeDiff() {
			// base case
			if (this == NULL_NODE) {
				return new SizeTracker(0, 0);
			}
			SizeTracker leftT = this.left.netSizeDiff();
			SizeTracker rightT = this.right.netSizeDiff();
			int thisSize = leftT.subSize + rightT.subSize + 1;
			int totalDiff = leftT.diff + rightT.diff + (rightT.subSize - leftT.subSize);
			return new SizeTracker(thisSize, totalDiff);
//			int diff = this.right.size() - this.left.size();
//			return diff + this.left.netSizeDiff() + this.right.netSizeDiff();
		}
		
		public int size() {
			if (this == NULL_NODE) {
				return 0;
			}
			return 1 + this.left.size() + this.right.size();
		}

		// The next method is used by the unit tests
		public BinaryNode insert(Character e) {
			if (this == NULL_NODE) {
				return new BinaryNode(e);
			} else if (e.compareTo(this.data) < 0) {
				left = left.insert(e);
			} else if (e.compareTo(this.data) > 0) {
				right = right.insert(e);
			} else {
				// do nothing
			}
			return this;
		} // insert
		
		@Override
		// You can use this to see in-order traversal 
		public String toString() {
			if (this == NULL_NODE) {
				return "";
			}
			return left.toString() + this.data.toString() + right.toString();
		} // toString


		
	} // end BinaryNode Class
	
	
} // end BinarySearchTree Class