package tree;

/**
 * An implementation of a binary search tree, containing Integer data.
 */
public class BinarySearchTree {

	private BinaryNode root;

	public BinarySearchTree() {
		root = new ExternalNode(null, null);
	} // BinarySearchTree
		
	/**
	 * Insert the given data into the Extended BST, unless the data already
	 * exists at an internal node of the tree (in which case, there is no 
	 * effect of the method.)
	 * @param data
	 */
	public void insert(Integer data) {
		// Hint: follow the example of toString: use an abstract method
		// in the BinaryNode abstract class that is implemented differently
		// by ExternalNode and InternalNode classes.
		this.root = this.root.insert(data);
	} // insert

	@Override
	public String toString() {
		return root.toString();
	} // toString

	// -----------------------------------------------------------------------
	
	class ExternalNode extends BinaryNode {
		Integer low;
		Integer high;
		
		public ExternalNode (Integer low, Integer high) {
			this.low = low;
			this.high = high;
		} // ExternalNode
		
		@Override
		public String toString() {
		 return "(" + this.low + "," + this.high + ")";
		} // toString	
		
		@Override
		public BinaryNode insert(Integer e) {
			ExternalNode left = new ExternalNode(low, e);
			ExternalNode right = new ExternalNode(e, high);
			BinaryNode node = new InternalNode(e,left,right);
			return node;
		}
	} // end class ExternalNode

	// -----------------------------------------------------------------------

	class InternalNode extends BinaryNode {
		
		public Integer data;
		public BinaryNode left;
		public BinaryNode right;

		public InternalNode(Integer data, BinaryNode left, BinaryNode right) {
			this.data = data;
			this.left = left;
			this.right = right;
		} // InternalNode

		@Override
		public String toString() {
			return left.toString() + this.data.toString() + right.toString();
		} // toString
		
		@Override
		public BinaryNode insert(Integer e) {
			if (e < this.data) {
				this.left = this.left.insert(e);
			} else {
				this.right = this.right.insert(e);
			}
			return this;
		}
	} // end class InternalNode
}