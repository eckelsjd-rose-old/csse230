package buildtree;

import java.util.Stack;

/**
 * 
 * @author Matt Boutell and Joshua Eckels
 * @param <T>
 */

public class BinaryTree {

	private BinaryNode root;

	private final BinaryNode NULL_NODE = new BinaryNode(null);

	public BinaryTree() {
		root = NULL_NODE;
	}

	/**
	 * Constructs a tree (any tree of characters, not just a BST) with the given
	 * values and number of children, given in a pre-order traversal order. See
	 * the HW spec for more details.
	 * 
	 * @param chars
	 *            One char per node.
	 * @param children
	 *            L,R, 2, or 0.
	 */
	public BinaryTree(String chars, String children) {
		
		// chars and children must be same length
		if (chars.length() != children.length()) {
			return;
		}
		
		// return empty tree
		if (chars.length() == 0) {
			this.root = NULL_NODE;
			return;
		}
		
		Stack<BinaryNode> stack = new Stack<>();
		for (int i = children.length() - 1; i >= 0; i--) {
			if (children.charAt(i) == '0') {
				stack.push(new BinaryNode(chars.charAt(i)));
			}
			if (children.charAt(i) == '2') {
				BinaryNode left = stack.pop();
				BinaryNode right = stack.pop();
				BinaryNode parent = new BinaryNode(chars.charAt(i));
				parent.right = right;
				parent.left = left;
				stack.push(parent);
			}
			if (children.charAt(i) == 'L') {
				BinaryNode parent = new BinaryNode(chars.charAt(i));
				parent.left = stack.pop();
				stack.push(parent);
			}
			if (children.charAt(i) == 'R') {
				BinaryNode parent = new BinaryNode(chars.charAt(i));
				parent.right = stack.pop();
				stack.push(parent);
			}
		}
		this.root = stack.pop();
	}

	/**
	 * In-order traversal of the characters
	 */
	@Override
	public String toString() {
		return root.toString();
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

		public Character data;
		public BinaryNode left;
		public BinaryNode right;

		public BinaryNode(Character element) {
			this.data = element;
			this.left = NULL_NODE;
			this.right = NULL_NODE;
		}

		@Override
		public String toString() {
			if (this == NULL_NODE) {
				return "";
			}
			return left.toString() + data + right.toString();
		}

		public String toStructuredString() {
			if (this == NULL_NODE) {
				return "";
			}
			return "(" + left.toStructuredString() + this.data
					+ right.toStructuredString() + ")";
		}

	}
}