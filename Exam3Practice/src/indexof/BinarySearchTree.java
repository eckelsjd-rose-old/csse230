package indexof;

import java.util.NoSuchElementException;

/**
 * @author TODO: your name here.
 */
public class BinarySearchTree {
	private BinaryNode root;

	private final BinaryNode NULL_NODE = new BinaryNode();

	public BinarySearchTree() {
		root = NULL_NODE;
	}

	
	/**
	 * Returns the index (in an inorder traversal) of the given value in the BST.
	 * If the value is not in the tree, throws a NoSuchElementException.
	 */
	public int indexOf(Integer value) {
		// TODO: Write me!

		return -1;
	}

	
	
	public boolean isEmpty() {
		return root == NULL_NODE;
	}
	
	public void insert(Integer item) {
		root = root.insert(item);
	}

	private class BinaryNode {
		private Integer data;
		private BinaryNode left;
		private BinaryNode right;

		public BinaryNode() {
			this.data = null;
			this.left = null;
			this.right = null;
		}
		
		public BinaryNode(Integer element) {
			this.data = element;
			this.left = NULL_NODE;
			this.right = NULL_NODE;
		}

		public BinaryNode insert(Integer item) {
			if (this == NULL_NODE) {
				return new BinaryNode(item);
			}
			int comp = item.compareTo(data);
			if (comp < 0) {
				left = left.insert(item);
			} else if (comp > 0) {
				right = right.insert(item);
			}
			return this;
		}

	}

}
