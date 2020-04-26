package redblack;

/**
 * @author TODO: your name here.
 */
public class BinarySearchTreeWithColor {

	RedBlackNode root;

	private final RedBlackNode NULL_NODE = new RedBlackNode(null,Color.BLACK);

	public BinarySearchTreeWithColor() {
		root = NULL_NODE;
	}
	

	/**
	 * Returns true if the BST satisfies the color properties of a red-black tree:
	 *  - the root node is black;
	 *  - the are no two consecutive reds;
	 *  - every path from the root node to a NULL_NODE has the same number of black nodes.
	 */
	public boolean isRedBlack() {
		// TODO: write me!

		return false;
	}




	// Below used for testing purposes.

	public void insert(Integer val,boolean paintItBlack) {
		root = root.insert(val,paintItBlack);
	}
	

	/**
	 * @return A string showing a traversal of the nodes where children are
	 *         indented so that the structure of the tree can be determined.
	 */
	public String toIndentString() {
		return root.toIndentString("");
	}

	@Override
	public String toString() {
		return root.toString();
	}

	

	public enum Color {
		
		RED,BLACK;
		
		public String toString() {
			switch(this) {
			case RED: return "RED";
			default: return "BLACK";
			}
		}
	}
	
	// /////////////// RedBlackNode
	public class RedBlackNode {

		public Integer data;
		public RedBlackNode left;
		public RedBlackNode right;
		public Color color;

		public RedBlackNode(Integer element, Color col) {
			this.data = element;
			this.left = NULL_NODE;
			this.right = NULL_NODE;
			this.color = col;
		}

		
		
		// Below used for testing purposes

		public RedBlackNode insert(Integer val, boolean paintItBlack) {
			if (this == NULL_NODE) {
				return new RedBlackNode(val, (paintItBlack? Color.BLACK : Color.RED));
			}
			if (val.compareTo(this.data) <= 0) {
				left = left.insert(val, paintItBlack);
			}
			else {
				right = right.insert(val, paintItBlack);
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
				return "";
			}

			String myInfo = indent + String.format("%d %s\n", this.data,this.color.toString());

			return myInfo + this.left.toIndentString(indent + "  ")
					+ this.right.toIndentString(indent + "  ");
		}
	}


}