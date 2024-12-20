package tree;

/**
 * An implementation of a binary search tree, containing Integer data.
 * 
 * @author <<TODO: Joshua Eckels>>.
 *
 */

public class BinarySearchTree {

	BinaryNode root;

	public final BinaryNode NULL_NODE = new BinaryNode(null);

	public BinarySearchTree() {
		root = NULL_NODE;
	}
	
	public int countExactlyBalancedNodes() {
		HeightTracker tracker = this.root.countNodes();
		return tracker.count;
	}
	
	// The next methods are used by the unit tests
	public void insert(Integer e) {
		root = root.insert(e);
	}

	@Override
	public String toString() {
		return root.toString();
	}
	
	class HeightTracker {
		private int height;
		private int count;
		
		HeightTracker() {
			this.height = 0;
			this.count = 0;
		}
		
		HeightTracker(int height, int count) {
			this.height = height;
			this.count = count;
		}
	}

	// /////////////// BinaryNode
	class BinaryNode {
		
		public Integer data;
		public BinaryNode left;
		public BinaryNode right;

		public BinaryNode(Integer data) {
			this.data = data;
			this.left = NULL_NODE;
			this.right = NULL_NODE;
		}
		
		public HeightTracker countNodes() {
			// base case
			if (this == NULL_NODE) {
				return new HeightTracker(-1,1);
			}
			int count = 0;
			HeightTracker leftT = this.left.countNodes();
			HeightTracker rightT = this.right.countNodes();
			int thisHeight = Integer.max(leftT.height, rightT.height) + 1;
			if (leftT.height == rightT.height) {count++;}
			int totalCount = leftT.count + rightT.count + count;
			return new HeightTracker(thisHeight, totalCount);
//			int diff = this.right.height() - this.left.height();
//			return diff + this.left.netBalance() + this.right.netBalance();
		}


		// The next 2 methods are used by the unit tests
		public BinaryNode insert(Integer e) {
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
		}

		@Override
		public String toString() {
			if (this == NULL_NODE) {
				return "";
			}
			return left.toString() + this.data.toString() + right.toString();
		}
	}

}