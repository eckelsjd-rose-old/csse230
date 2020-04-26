import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * 
 * Implementation of most of the Set interface operations using a Binary Search
 * Tree
 *
 * @author Matt Boutell and Joshua Eckels.
 * @param <T>
 */
public class BinarySearchTree<T extends Comparable<T>> implements Iterable<T> {
	private BinaryNode root;
	private int modifyCount;

	// Most of you will prefer to use NULL NODES once you see how to use them.
	private final BinaryNode NULL_NODE = new BinaryNode();

	public BinarySearchTree() {
		root = NULL_NODE;
		this.modifyCount = 0;
	}

	// For manual tests only
	void setRoot(BinaryNode n) {
		this.root = n;
	}

	public int size() {
		return root.size();
	}

	public boolean containsNonBST(T item) {
		return root.containsNonBST(item);
	}

	public int height() {
		return root.height();
	}

	public ArrayList<T> toArrayList() {
		ArrayList<T> list = new ArrayList<>();
		root.toArrayList(list);
		return list;
	}

	public String toString() {
		return this.toArrayList().toString();
	}

	public boolean isEmpty() {
		return (this.root == NULL_NODE);
	}

	public Object[] toArray() {
		ArrayList<T> list = this.toArrayList();
		return list.toArray();
	}

	public boolean insert(T o) throws IllegalArgumentException {
		try {
			MutableBoolean state = new MutableBoolean(false);
			this.root = this.root.insert(o, state);
			if (state.bool) { modifyCount++; }
			return state.bool;
		} catch (NullPointerException e) {
			// passed a null object to insert(T o)
			throw new IllegalArgumentException("Illegal argument");
		}
	}

	public boolean remove(T o) throws IllegalArgumentException {
		try {
			MutableBoolean state = new MutableBoolean(false);
			this.root = this.root.remove(o, state);
			if (state.bool) { modifyCount++; }
			return state.bool;
		} catch (NullPointerException e) {
			// passed a null object to remove(T o)
			throw new IllegalArgumentException("Illegal argument");
		}

	}
	
	public boolean contains(T o) throws IllegalArgumentException {
		try {
			return this.root.contains(o);
		} catch (NullPointerException e) {
			// passed a null object to remove(T o)
			throw new IllegalArgumentException("Illegal argument");
		}
	}

	/**
	 * 
	 * Small subclass to allow passing mutable boolean object between function
	 * calls.
	 *
	 * @author eckelsjd. Created Oct 2, 2019.
	 */
	private class MutableBoolean {
		private boolean bool;

		public MutableBoolean(boolean b) {
			this.bool = b;
		}

		public void setTrue() {
			this.bool = true;
		}

		public void setFalse() {
			this.bool = false;
		}
	}

	/**
	 * 
	 * Class to store data and form the skeletal structure of the BST data structure.
	 *
	 * @author eckelsjd.
	 *         Created Oct 3, 2019.
	 */
	private class BinaryNode {
		private T data;
		private BinaryNode left;
		private BinaryNode right;

		public BinaryNode() {
			this.data = null;
			this.left = null;
			this.right = null;
		}

		public BinaryNode(T element) {
			this.data = element;
			this.left = NULL_NODE;
			this.right = NULL_NODE;
		}

		public BinaryNode insert(T o, MutableBoolean state) {
			if (this == NULL_NODE) {
				state.setTrue();
				return new BinaryNode(o);
			}

			// don't insert new BinaryNode
			if (this.data.equals(o)) {
				state.setFalse();
				return this;
			}

			// insert to the right or left
			if (this.data.compareTo(o) > 0) {
				this.left = this.left.insert(o, state);
			} else {
				this.right = this.right.insert(o, state);
			}
			return this;
		}

		public BinaryNode remove(T o, MutableBoolean state) {
			if (this == NULL_NODE) {
				return NULL_NODE;
			}

			// found a node with the data to remove; check cases
			if (this.data.equals(o)) {
				state.setTrue();
				// leaf case
				if ((this.left == NULL_NODE) && (this.right == NULL_NODE)) {
					return NULL_NODE;
				}

				// right-child only case
				if ((this.left == NULL_NODE) && (this.right != NULL_NODE)) {
					return this.right;
				}

				// left-child only case
				if ((this.left != NULL_NODE) && (this.right == NULL_NODE)) {
					return this.left;
				}

				// two-child parent case
				if ((this.left != NULL_NODE) && (this.right != NULL_NODE)) {
					BinaryNode b = this.left.getMaxElement();
					b.right = this.right;
					if (b == this.left) {
						return b;
					}
					b.left = this.left;
					return b;
				}
			}
			
			// check to the right or left
			if (this.data.compareTo(o) > 0) {
				this.left = this.left.remove(o, state);
			} else {
				this.right = this.right.remove(o, state);
			}
			return this;
		}
		
		// binary search algorithm
		public boolean contains(T o) {
			if (this == NULL_NODE) {return false; }
			if (this.data.equals(o)) { return true; }
			if (this.data.compareTo(o) > 0) {
				return this.left.contains(o);
			}
			if (this.data.compareTo(o) < 0) {
				return this.right.contains(o);
			}
			return false;
		}

		// returns right-most element of a subtree
		public BinaryNode getMaxElement() {
			if (this.right == NULL_NODE) {
				return this;
			}
			if (this.right.right == NULL_NODE) {
				BinaryNode b = this.right;
				this.right = NULL_NODE;
				return b;
			}

			return this.right.getMaxElement();
		}

		public int size() {
			if (this == NULL_NODE) {
				return 0;
			}
			return (1 + this.left.size() + this.right.size());
		}

		public boolean containsNonBST(T item) {
			if (this == NULL_NODE) {
				return false;
			}
			return (this.data.equals(item)) || this.left.containsNonBST(item) || this.right.containsNonBST(item);
		}

		public int height() {
			if (this == NULL_NODE) {
				return -1;
			}
			return Math.max(this.left.height(), this.right.height()) + 1;
		}

		public void toArrayList(ArrayList<T> list) {
			if (this == NULL_NODE) {
				return;
			}
			this.left.toArrayList(list);
			list.add(this.data);
			this.right.toArrayList(list);
		}

		public T getData() {
			return this.data;
		}

		public BinaryNode getLeft() {
			return this.left;
		}

		public BinaryNode getRight() {
			return this.right;
		}

		// For manual testing
		public void setLeft(BinaryNode left) {
			this.left = left;
		}

		public void setRight(BinaryNode right) {
			this.right = right;
		}

	}
	// Implement your 3 iterator classes here, plus any other inner helper
	// classes you'd like.

	// Factory method for creating ArrayListIterator
	public Iterator<T> inefficientIterator() {
		return new ArrayListIterator();
	}

	// Factory method for creating PreOrderIterator
	public Iterator<T> preOrderIterator() {
		return new PreOrderIterator();
	}

	// Default method to construct an InOrderIterator for the BinarySearchTree class.
	@Override
	public Iterator<T> iterator() {
		return new InOrderIterator();
	}

	/**
	 * 
	 * Inner class to traverse binary search tree in a pre-order fashion
	 *
	 * @author eckelsjd. Created Sep 29, 2019.
	 */
	private class PreOrderIterator implements Iterator<T> {

		Stack<BinaryNode> stack;
		BinaryNode next;
		int startCount; // for tracking modifications
		boolean removeReady; // for tracking .remove() function calls

		PreOrderIterator() {
			this.stack = new Stack<>();
			this.next = null;
			this.removeReady = false;
			this.startCount = BinarySearchTree.this.modifyCount; // track changes
			if (BinarySearchTree.this.root == NULL_NODE) {
				// don't add to stack
			} else {
				this.stack.push(BinarySearchTree.this.root);
			}

		}

		@Override
		public boolean hasNext() {
			return !this.stack.isEmpty();
		}

		// Move to next item on the stack. Push the right and left children to
		// stack.
		// Return element data of next node. Don't put NULL_NODE on stack.
		@Override
		public T next() throws NoSuchElementException, ConcurrentModificationException {
			try {
				// break if tree has been modified since iterator's creation
				if (this.startCount != BinarySearchTree.this.modifyCount) {
					throw new ConcurrentModificationException("Tree has been modified.");
				}
				// visit the next node immediately (pre-order)
				BinaryNode temp = this.stack.pop();
				if (temp.right != NULL_NODE) {
					this.stack.push(temp.right); // push right children first
				}
				if (temp.left != NULL_NODE) {
					this.stack.push(temp.left); // then push left children
				}
				this.next = temp;
				this.removeReady = true; // can only remove an item after next has been called
				return temp.getData();

			} catch (EmptyStackException e) {
				throw new NoSuchElementException("Nothing left to iterate over.");
			}
		}
		
		@Override
		public void remove() throws IllegalStateException {
			if (this.removeReady) {
				BinarySearchTree.this.remove(this.next.data);
				this.removeReady = false;
			} else {
				throw new IllegalStateException("Illegal state");
			}
		}
	}

	/**
	 * 
	 * Inner class to iterate binary search tree by first converting it to an
	 * array list.
	 *
	 * @author eckelsjd. Created Sep 29, 2019.
	 */
	private class ArrayListIterator implements Iterator<T> {

		ArrayList<T> list;
		int index;
		int startCount;
		boolean removeReady;
		T next;

		ArrayListIterator() {
			this.list = BinarySearchTree.this.toArrayList();
			index = 0;
			this.startCount = BinarySearchTree.this.modifyCount;
			this.removeReady = false;
			this.next = null;
		}

		@Override
		public boolean hasNext() {
			return (index < list.size());
		}

		@Override
		public T next() throws NoSuchElementException, ConcurrentModificationException {
			// break if tree has been modified since iterator's creation
			if (this.startCount != BinarySearchTree.this.modifyCount) {
				throw new ConcurrentModificationException("Tree has been modified.");
			}
			if (!this.hasNext()) {
				throw new NoSuchElementException("nothing left to iterate over.");
			}
			this.removeReady = true;
			this.next = list.get(index);
			return list.get(index++);
		}
		
		@Override
		public void remove() throws IllegalStateException {
			if (this.removeReady) {
				BinarySearchTree.this.remove(this.next);
				this.removeReady = false;
			} else {
				throw new IllegalStateException("Illegal state");
			}
		}
	}

	/**
	 * 
	 * Inner class to traverse binary search tree in an in-order fashion.
	 * Default iterator for BinarySearchTree class. Uses the following tags to
	 * iterate tree:
	 * 
	 * Tags: 0 - never been visited 1 - visit now 2 - don't visit again
	 *
	 * @author eckelsjd. Created Sep 29, 2019.
	 */
	private class InOrderIterator implements Iterator<T> {

		Stack<TaggedNode> stack;
		TaggedNode next;
		int startCount;
		boolean removeReady;

		InOrderIterator() {
			this.stack = new Stack<>();
			this.removeReady = false;
			this.startCount = BinarySearchTree.this.modifyCount;
			if (BinarySearchTree.this.root == NULL_NODE) {
				// don't add to stack
			} else {
				this.stack.push(new TaggedNode(BinarySearchTree.this.root));
			}

		}

		@Override
		public boolean hasNext() {
			return !this.stack.isEmpty();
		}

		/*
		 * Move to next item on the stack. Push the right child to stack if
		 * parent has been visited. Mark current node as ready to visit and put
		 * back on stack. Push left child to stack. Don't put NULL_NODE on
		 * stack.
		 */
		@Override
		public T next() throws NoSuchElementException, ConcurrentModificationException {
			try {
				// break if tree has been modified since iterator's creation
				if (this.startCount != BinarySearchTree.this.modifyCount) {
					throw new ConcurrentModificationException("Tree has been modified.");
				}
				
				boolean foundNext = false;

				while (!foundNext) {
					// pull next node off the stack
					TaggedNode temp = this.stack.pop();

					// check if left child is null; in which case, visit this
					// node on this iteration
					if (temp.getNode().left == NULL_NODE) {
						temp.incrementTag();
					}

					// tag and put right children on stack first
					// only if the parent has already been visited (tag == 1)
					if ((temp.getNode().right != NULL_NODE) && (temp.getTag() == 1)) {
						this.stack.push(new TaggedNode(temp.getNode().right));
					}

					// check current node if its already been visited, if so,
					// break from while loop;
					// if not, tag and put back on stack and continue loop
					if (temp.getTag() == 1) {
						temp.incrementTag();
						this.next = temp; // store this tagged node to return
											// later
						foundNext = true; // break from loop after this
											// iteration finishes
					} else {
						temp.incrementTag();
						this.stack.push(temp);
					}

					// tag and put left children on stack last
					if ((temp.getNode().left != NULL_NODE) && (temp.getTag() < 2)) {
						this.stack.push(new TaggedNode(temp.getNode().left));
					}
				}
				this.removeReady = true;
				return this.next.getNode().getData();

			} catch (EmptyStackException e) {
				throw new NoSuchElementException("Nothing left to iterate over.");
			}
		}
		
		@Override
		public void remove() throws IllegalStateException {
			if (this.removeReady) {
				BinarySearchTree.this.remove(this.next.getNode().getData());
				this.removeReady = false;
			} else {
				throw new IllegalStateException("Illegal state");
			}
		}
	}

	/**
	 * 
	 * Helper class for storing nodes and tag data for in-order traversal.
	 *
	 * @author eckelsjd. Created Sep 29, 2019.
	 */
	private class TaggedNode {

		private BinaryNode b;
		private int tag;

		TaggedNode(BinaryNode b) {
			this.b = b;
			this.tag = 0;
		}

		public void incrementTag() {
			this.tag++;
		}

		public int getTag() {
			return this.tag;
		}

		public BinaryNode getNode() {
			return this.b;
		}
	}

}
