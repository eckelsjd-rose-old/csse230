package bst;

import java.util.ConcurrentModificationException;
import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.Stack;

/**
 *
 * Exam 2. Tree methods.
 * 
 * @author
 */

/*
 * TODO: Directions: Implement the methods below. See the paper for details.
 */
public class BinarySearchTree implements Iterable<Integer> {

	BinaryNode root;
	private int modifyCount = 0;

	// The -17 is arbitrary -any int would be fine since we never refer to it.
	final BinaryNode NULL_NODE = new BinaryNode(-17);

	public BinarySearchTree() {
		root = NULL_NODE;
	}

	int countNumberInSet(Set<Integer> set) {
		MutableInteger c = new MutableInteger();
		this.root.countNumberinSet(set, c);
		return c.getCount();
	}

	void removeSmallestDescendent(int target) {
		this.root = this.root.findTarget(target);
	}

	@SuppressWarnings("boxing")
	int sumOfKSmallest(int k) {
		Iterator<Integer> iter = this.iterator();
		int sum = 0;
		for (int i = 0; i < k; i++) {
			Integer temp = iter.next();
			sum += temp;
		}
		return sum;
	}

	// The next methods are used by the unit tests
	public void insert(Integer e) {
		root = root.insert(e);
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

	@Override
	public String toString() {
		return root.toString();
	}

	class MutableInteger {
		private int count = 0;

		MutableInteger() {
		}

		void incrementCount() {
			this.count++;
		}

		int getCount() {
			return this.count;
		}
	}

	// /////////////// BinaryNode
	public class BinaryNode {

		public int data;
		public BinaryNode left;
		public BinaryNode right;

		public BinaryNode(int element) {
			this.data = element;
			this.left = NULL_NODE;
			this.right = NULL_NODE;
		}

		public BinaryNode findTarget(int target) {
			if (this == NULL_NODE) {
				return NULL_NODE;
			}
			// traverse left or right
			if (this.data > target) {
				this.left = this.left.findTarget(target);
			} else if (this.data < target) {
				this.right = this.right.findTarget(target);
			}

			// found target
			else {
				// target is smallest descendant case
				if (this.left == NULL_NODE) {
					return this.right;
				}
				this.left = this.removeSmallestDescendant();
			}
			return this;
		}

		private BinaryNode removeSmallestDescendant() {
			if (this == NULL_NODE) {
				return NULL_NODE;
			}
			if (this.left == NULL_NODE) {
				return NULL_NODE;
			}
			// Was getting stack overflow error; I think the issue is right here.
			//this.left = this.left.removeSmallestDescendant();
			return this.left.removeSmallestDescendant();

		}

		@SuppressWarnings("boxing")
		public void countNumberinSet(Set<Integer> set, MutableInteger count) {
			if (this == NULL_NODE) {
				return;
			}
			if (set.contains(this.data)) {
				count.incrementCount();
			}
			this.left.countNumberinSet(set, count);
			this.right.countNumberinSet(set, count);
			return;
		}

		// The rest of the methods are used by the unit tests and for debugging

		public BinaryNode insert(int e) {
			if (this == NULL_NODE) {
				return new BinaryNode(e);
			} else if (e < data) {
				left = left.insert(e);
			} else if (e > data) {
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
			return left.toString() + this.data + right.toString();
		}

		public String toIndentString(String indent) {
			if (this == NULL_NODE) {
				return indent + "NULL\n";
			}
			String myInfo = indent + String.format("%c\n", this.data);
			return myInfo + this.left.toIndentString(indent + "  ") + this.right.toIndentString(indent + "  ");
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
	private class InOrderIterator implements Iterator<Integer> {

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
		public Integer next() throws NoSuchElementException, ConcurrentModificationException {
			try {
				// break if tree has been modified since iterator's creation
				if (this.startCount != BinarySearchTree.this.modifyCount) {
					throw new ConcurrentModificationException("Tree has been modified.");
				}

				boolean foundNext = false;

				while (!foundNext) {
					// pull next node off the stack
					TaggedNode temp = this.stack.pop();

					// check if left child is null; in which case, visit
					// this
					// node on this iteration
					if (temp.getNode().left == NULL_NODE) {
						temp.incrementTag();
					}

					// tag and put right children on stack first
					// only if the parent has already been visited (tag ==
					// 1)
					if ((temp.getNode().right != NULL_NODE) && (temp.getTag() == 1)) {
						this.stack.push(new TaggedNode(temp.getNode().right));
					}

					// check current node if its already been visited, if
					// so,
					// break from while loop;
					// if not, tag and put back on stack and continue loop
					if (temp.getTag() == 1) {
						temp.incrementTag();
						this.next = temp; // store this tagged node to
											// return
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
				return this.next.getNode().data;

			} catch (EmptyStackException e) {
				throw new NoSuchElementException("Nothing left to iterate over.");
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

	@Override
	public Iterator<Integer> iterator() {
		return new InOrderIterator();
	}
}