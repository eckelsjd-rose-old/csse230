package heightbalance;

/**
 * @author You!
 * @param <T>
 */
public class BinarySearchTree {

	BinaryNode root;

	private final BinaryNode NULL_NODE = new BinaryNode(null);

	public BinarySearchTree() {
		root = NULL_NODE;
	}

	/**
	 * Determines if the given tree is height-balanced. (You can refer to Day 12
	 * slides or section 19.4 of the text if you need a refresher on the
	 * definition.) I use character as the data type here for convenience in
	 * writing tests, but your method shouldn't need to look at the node data at
	 * all.
	 * 
	 * You earn the efficiency points by making sure that your method runs in
	 * O(n) time, where n is the size of the tree, by using a single recursive
	 * traversal that does not (i) visit any node more than once, or (ii) use
	 * any extra data structure whose size depends on the size of the tree
	 * (other than the stack that Java uses to keep track of recursive calls, of
	 * course). Focus on correctness first. 
	 * 
	 * You may not include any other fields like balance codes in the BinaryTree
	 * or BinaryNode classes. You also may not modify the given insert() method
	 * in any way.
	 * 
	 * However, you may add helper methods with parameters and return values of
	 * any type, including a class that you define.
	 * 
	 * @return True if the tree is height balanced; false otherwise.
	 */
	public boolean isHeightBalanced() {
		// TODO: write me.
		return false;
	}

	/**
	 * Feel free to call from tests to use to verify the shapes of your trees
	 * while debugging. Just remove the calls you are done so the output isn't
	 * cluttered.
	 * 
	 * @return A string showing a traversal of the nodes where children are
	 *         indented so that the structure of the tree can be determined.
	 * 
	 */
	public String toIndentString() {
		return root.toIndentString("");
	}

	// The next methods are used by the unit tests
	public void insert(Character e) {
		root = root.insert(e);
	}

	@Override
	public String toString() {
		return root.toString();
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

		// The next methods are used by the unit tests
		public BinaryNode insert(Character e) {
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

		@Override
		public String toString() {
			if (this == NULL_NODE) {
				return "";
			}
			return left.toString() + this.data.toString() + right.toString();
		}

		public String toIndentString(String indent) {
			if (this == NULL_NODE) {
				return indent + "NULL\n";
			}

			String myInfo = indent + String.format("%c\n", this.data);

			return myInfo + this.left.toIndentString(indent + "  ") + this.right.toIndentString(indent + "  ");
		}
	}
}