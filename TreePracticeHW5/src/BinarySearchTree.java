/**
 * More Binary Tree practice problems. This problem creates BSTs of type
 * Integer: 1. Neither problem makes use of the BST ordering property; I just
 * found insert() to be a convenient way to build trees for testing. 2. I used
 * Integer instead of T since the makeTree method sets the data value of each
 * node to be a depth, which is an Integer.
 * 
 * @author Matt Boutell and <<<YOUR NAME HERE>>>.
 * @param <T>
 */

/*
 * TODO: 0 You are to implement the methods below. Use recursion!
 */
public class BinarySearchTree {

	private BinaryNode root;

	private final BinaryNode NULL_NODE = new BinaryNode(null);

	public BinarySearchTree() {
		root = NULL_NODE;
	}

	/**
	 * This constructor creates a full tree of Integers, where the value of each
	 * node is just the depth of that node in the tree.
	 * 
	 * @param maxDepth
	 *            The depth of the leaves in the tree.
	 * @throws Exception 
	 */
	public BinarySearchTree(int maxDepth) {
		
		// handle negative input
		if (maxDepth < 0) {
			root = NULL_NODE;
			return;
		}
		
		this.root = new BinaryNode(0);
		this.root.fill(1, maxDepth);
	
	}

	public int getSumOfHeights() {
		// TODO. 2 Write this.
		// Can you do it in O(n) time instead of O(n log n) by avoiding repeated
		// calls to height()?
		MutableInteger sum = new MutableInteger();
		int temp = this.root.sumHeights(sum);
		return sum.num;
	}

	// These are here for testing.
	public void insert(Integer e) {
		root = root.insert(e);
	}

	/**
	 * @return A string showing an in-order traversal of nodes with extra
	 *         brackets so that the structure of the tree can be determined.
	 */
	public String toStructuredString() {
		return root.toStructuredString();
	}

	// /////////////// BinaryNode
	public class BinaryNode {

		public Integer data;
		public BinaryNode left;
		public BinaryNode right;

		public BinaryNode(Integer element) {
			this.data = element;
			this.left = NULL_NODE;
			this.right = NULL_NODE;
		}

		public BinaryNode insert(Integer e) {
			if (this == NULL_NODE) {
				return new BinaryNode(e);
			} else if (e.compareTo(data) < 0) {
				left = left.insert(e);
			} else if (e.compareTo(data) > 0) {
				right = right.insert(e);
			} else {
				// do nothing
			}
			return this;
		}
		
		public void fill(int depth, int maxDepth) {
			if (depth > maxDepth) {
				return;
			}
			if (this.left == NULL_NODE) {
				this.left = new BinaryNode(depth);
			}
			if (this.right == NULL_NODE) {
				this.right = new BinaryNode(depth);
			}
			this.left.fill(depth + 1, maxDepth);
			this.right.fill(depth + 1, maxDepth);
		}
		
		public int sumHeights(MutableInteger count) {
			if (this == NULL_NODE) {
				return -1;
			}
			int height = Math.max(this.left.sumHeights(count), this.right.sumHeights(count)) + 1;
			count.num += height;
			return height;
			
		}

		public String toStructuredString() {
			if (this == NULL_NODE) {
				return "";
			}
			return "[" + left.toStructuredString() + this.data
					+ right.toStructuredString() + "]";
		}

	}
	
	class MutableInteger {
		int num;
		MutableInteger() {
			num = 0;
		}
	}
}