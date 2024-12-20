/**
 * Binary Tree practice problems
 * 
 * @author Matt Boutell and <<<YOUR NAME HERE>>>. 2014.
 * @param <T>
 */

/*
 * TODO: 0 You are to implement the four methods below. I took most of them from
 * a CSSE230 exam given in a prior term. These can all be solved by recursion -
 * I encourage you to do so too, since most students find practicing recursion
 * to be more useful.
 */
public class BinarySearchTree<T extends Comparable<? super T>> {

	private BinaryNode root;

	private final BinaryNode NULL_NODE = new BinaryNode(null);

	public BinarySearchTree() {
		root = NULL_NODE;
	}

	/**
	 * This method counts the number of occurrences of positive Integers in the
	 * tree that is of type Integer. Hint: You may assume this tree contains
	 * integers, so may use a cast.
	 * 
	 * @return The number of positive integers in the tree.
	 */

	public int countPositives() {
		return countPositives(this.root);
	}

	public int countPositives(BinaryNode b) {
		if (b == NULL_NODE) {
			return 0;
		}
		if ((int) b.element <= 0) {
			return countPositives(b.right) + countPositives(b.left);
		}
		return 1 + countPositives(b.left) + countPositives(b.right);
	}

	/**
	 * Recall that the depth of a node is number of edges in a path from this
	 * node to the root. Returns the depth of the given item in the tree. If the
	 * item isn't in the tree, then it returns -1.
	 * 
	 * @param item
	 * @return The depth, or -1 if it isn't in the tree.
	 */
	public int getDepth(T item) {
		return getDepth(item, this.root, -1);
	}

	public int getDepth(T item, BinaryNode b, int count) {
		if (b == NULL_NODE) {
			return -1;
		}
		if (b.element.equals(item)) {
			return ++count;
		}
		count++;
		return Math.max(getDepth(item, b.left, count), getDepth(item, b.right, count));
	}

	/**
	 * This method visits each node of the BST in pre-order and determines the
	 * number of children of each node. It produces a string of those numbers.
	 * If the tree is empty, an empty string is to be returned. For the
	 * following tree, the method returns the string: "2200110"
	 * 
	 * 10 5 15 2 7 18 10
	 * 
	 * @return A string representing the number of children of each node when
	 *         the nodes are visited in pre-order.
	 */

	public String numChildrenOfEachNode() {
		StringBuilder s = new StringBuilder();
		numChildrenOfEachNode(s, this.root);
		return s.toString();
	}

	public void numChildrenOfEachNode(StringBuilder s, BinaryNode b) {
		if (b == NULL_NODE) {
			return;
		}
		if ((b.left != NULL_NODE) && (b.right != NULL_NODE)) {
			s.append("2");
		} else if ((b.left == NULL_NODE) && (b.right != NULL_NODE) || (b.left != NULL_NODE) && (b.right == NULL_NODE)) {
			s.append("1");
		} else {
			s.append("0");
		}
		numChildrenOfEachNode(s, b.left);
		numChildrenOfEachNode(s, b.right);
	}

	/**
	 * This method determines if a BST forms a zig-zag pattern. By this we mean
	 * that each node has exactly one child, except for the leaf. In addition,
	 * the nodes alternate between being a left and a right child. An empty tree
	 * or a tree consisting of just the root are both said to form a zigzag
	 * pattern. For example, if you insert the elements 10, 5, 9, 6, 7 into a
	 * BST in that order. , you will get a zig-zag.
	 * 
	 * @return True if the tree forms a zigzag and false otherwise.
	 */
	public boolean isZigZag() {
		// zig-zag right initially
		if (this.root.right != NULL_NODE && this.root.left == NULL_NODE) {
			return isZigZag(this.root.right, "right");
		}
		// zig-zag left initially
		if (this.root.right == NULL_NODE && this.root.left != NULL_NODE) {
			return isZigZag(this.root.left, "left");
		}
		// will fall into base cases
		return isZigZag(this.root, "NA");
	}

	// Parameter "direction" indicates the last direction of the zig-zag.
	// Two lefts in a row -> zig-zag = false
	// Two rights in a row -> zig-zag = false
	// Two children -> zig-zag = false
	public boolean isZigZag(BinaryNode b, String direction) {
		if (b == NULL_NODE) {
			return true;
		}

		// no children
		if ((b.left == NULL_NODE) && (b.right == NULL_NODE)) {
			return true;
		}

		// check for two children
		if ((b.left != NULL_NODE) && (b.right != NULL_NODE)) {
			return false;
		}

		// check left zig-zag
		if (direction.equals("left")) {
			if (b.left != NULL_NODE) {
				return false;
			}
			return isZigZag(b.right, "right");
		}

		// check right zig-zag
		if (b.right != NULL_NODE) {
			return false;
		}
		return isZigZag(b.left, "left");
	}

	public void insert(T e) {
		root = root.insert(e);
	}

	// /////////////// BinaryNode
	public class BinaryNode {

		public T element;
		public BinaryNode left;
		public BinaryNode right;

		public BinaryNode(T element) {
			this.element = element;
			this.left = NULL_NODE;
			this.right = NULL_NODE;
		}

		public BinaryNode insert(T e) {
			if (this == NULL_NODE) {
				return new BinaryNode(e);
			} else if (e.compareTo(element) < 0) {
				left = left.insert(e);
			} else if (e.compareTo(element) > 0) {
				right = right.insert(e);
			} else {
				// do nothing
			}
			return this;
		}
	}
}