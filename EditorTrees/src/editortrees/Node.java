package editortrees;

import editortrees.EditTree.MutableInt;
import editortrees.EditTree.RotationTracker;

/**
 * 
 * A node in a height-balanced binary tree. Each node contains a character
 * element, a balance code, and a rank. Except for the NULL_NODE, one node
 * cannot belong to two different trees.
 *
 * @author eckelsjd, snidersa Created Oct 9, 2019.
 */
public class Node {
	// Code used to indicate balance orientation of a node
	enum Code {
		SAME, LEFT, RIGHT;

		// Used in the displayer and debug string
		public String toString() {
			switch (this) {
			case LEFT:
				return "/";
			case SAME:
				return "=";
			case RIGHT:
				return "\\";
			default:
				throw new IllegalStateException();
			}
		}
	}

	char element;
	Node left, right; // subtrees
	int rank; // inorder position of this node within its own subtree.
	Code balance;
	Node parent;
	DisplayableNodeWrapper display;

	// create an empty NULL_NODE
	public Node() {
		this.left = null;
		this.right = null;
	}

	// create a Node that contains the given element
	public Node(char element) {
		this.element = element;
		this.parent = null;
		this.left = EditTree.NULL_NODE;
		this.right = EditTree.NULL_NODE;
		this.balance = Code.SAME;
		this.rank = 0;
		this.display = new DisplayableNodeWrapper(this);
	}

	// return a copy of this node and its subtrees
	public Node copy() {
		if (this == EditTree.NULL_NODE) {
			return EditTree.NULL_NODE;
		}
		Node node = new Node();
		node.element = this.element;
		node.balance = this.balance;
		node.rank = this.rank;
		node.left = this.left.copy();
		node.right = this.right.copy();
		node.left.parent = node;
		node.right.parent = node;
		node.display = new DisplayableNodeWrapper(this);
		return node;
	}

	/*
	 * Returns a string from this tree of a given length and starting at the given
	 * in-order position.
	 */
	public String get(int pos, MutableInt length) {
		if (this == EditTree.NULL_NODE) {
			return "";
		}
		// recurse left
		if (pos < this.rank) {
			String l = this.left.get(pos, length);
			if (length.i >= 2) {
				length.i--;
				return l + this.element + this.right.get(pos - (this.rank + 1), length);
			} else if (length.i == 1) {
				length.i--;
				return l + this.element;
			}
			return l;
		} else if (pos > this.rank) {
			// recurse right
			return this.right.get(pos - (this.rank + 1), length);
		} else {
			if (length.i >= 2) {
				length.i--;
				return this.element + this.right.get(pos - (this.rank + 1), length);
			}
			length.i--;
			return Character.toString(this.element);
		}
	}

	// Returns the character at this in-order position "pos" in the tree
	public char get(int pos) {
		if (pos == this.rank) {
			return this.element;
		} else if (pos < this.rank) {
			return this.left.get(pos);
		} else {
			return this.right.get(pos - (this.rank + 1));
		}
	}

	// add a character at the end of the tree (right-most subtree)
	// RotationTracker object is used to count # rotations performed
	public Node add(char c, RotationTracker count) {
		return this.add(c, this.size(), count);
	}

	// add new node at the given index 'pos'
	// RotationTracker object is used to count # rotations performed
	public Node add(char ch, int pos, RotationTracker rotTracker) {
		String direction = "";

		// decide direction of recursion
		if (this == EditTree.NULL_NODE) {
			return new Node(ch);
		} else if (pos <= this.rank) {
			this.left = this.left.add(ch, pos, rotTracker);
			this.left.parent = this;
			this.rank++;
			direction = "left";
		} else if (pos > this.rank) {
			this.right = this.right.add(ch, pos - (this.rank + 1), rotTracker);
			this.right.parent = this;
			direction = "right";
		}

		// check balance codes
		if (rotTracker.needsBalancing) {

			// inserted in left subtree
			if (direction.equals("left")) {
				if (this.balance == Code.SAME) {
					this.balance = Code.LEFT;
				} else if (this.balance == Code.RIGHT) {
					this.balance = Code.SAME;
					rotTracker.needsBalancing = false;
					rotTracker.climb = true;
				} else if (this.balance == Code.LEFT) {
					// rebalance needed
					rotTracker.needsBalancing = false;
					rotTracker.climb = true;
					if (this.left.balance == Code.LEFT) {
						this.rotateRight(rotTracker);
						return this.parent;
					} else if (this.left.balance == Code.RIGHT) {
						// double right
						this.doubleRotateRight(rotTracker);
						return this.parent;
					}
				}
			}

			// inserted into right subtree
			else if (direction.equals("right")) {
				if (this.balance == Code.SAME) {
					this.balance = Code.RIGHT;
				} else if (this.balance == Code.LEFT) {
					this.balance = Code.SAME;
					rotTracker.needsBalancing = false;
					rotTracker.climb = true;
				} else if (this.balance == Code.RIGHT) {
					// rebalance needed
					rotTracker.needsBalancing = false;
					rotTracker.climb = true;
					if (this.right.balance == Code.LEFT) {
						// double left
						this.doubleRotateLeft(rotTracker);
						return this.parent;
					} else if (this.right.balance == Code.RIGHT) {
						this.rotateLeft(rotTracker);
						return this.parent;
					}
					// Do nothing if right balance code is SAME. Height did not change.
				}

			} else {
				// Great job, you broke it. Do nothing.
			}
		}

		// occurs only when at the bottom of a tree
		else {

			// decide if height changed after inserting a leaf
			if (this.left == EditTree.NULL_NODE || this.right == EditTree.NULL_NODE) {
				rotTracker.needsBalancing = true;
				if (this.left == EditTree.NULL_NODE) {
					this.balance = Code.RIGHT;
				} else if (this.right == EditTree.NULL_NODE) {
					this.balance = Code.LEFT;
				}
			}

			// only if we are not climbing back up the tree
			else if (!rotTracker.climb) {
				this.balance = Code.SAME;
				rotTracker.climb = true;
			}

		}

		return this;
	}

	/*
	 * Removes a node at the in-order position 'pos'. Tracks rotation info, and
	 * handles balance codes on way back up the tree.
	 */
	public Node delete(int pos, RotationTracker thing) {
		String direction; // used to determine which tree was deleted from

		// found node to be deleted
		if (this.rank == pos) {
			thing.deletedItem = this.element;
			// no children
			if (this.left == EditTree.NULL_NODE && this.right == EditTree.NULL_NODE) {
				return EditTree.NULL_NODE;
			}

			// left child only
			else if (this.left != EditTree.NULL_NODE && this.right == EditTree.NULL_NODE) {
				this.left.parent = this.parent;
				return this.left;
			}

			// right child only
			else if (this.left == EditTree.NULL_NODE && this.right != EditTree.NULL_NODE) {
				this.right.parent = this.parent;
				return this.right;
			}

			// two children case
			// we need to replace the node with its immediate successor
			else {

				// Find and stitch in successor node
				Node successor = new Node('X');
				this.right = this.right.getSuccessor(thing, successor);
				successor.right = this.right;
				successor.left = this.left;
				successor.parent = this.parent;
				successor.rank = this.rank;
				successor.balance = this.balance;
				if (successor.left != EditTree.NULL_NODE) {
					successor.left.parent = successor;
				}
				if (successor.right != EditTree.NULL_NODE) {
					successor.right.parent = successor;
				}

				// Deleted node replaced with successor. Check balance codes.
				if (thing.climb) {
					if (successor.balance == Code.RIGHT) {
						successor.balance = Code.SAME;
					} else if (successor.balance == Code.SAME) {
						successor.balance = Code.LEFT;
						thing.needsBalancing = false;
					} else if (successor.balance == Code.LEFT) {
						if (successor.left.balance == Code.RIGHT) {
							successor.doubleRotateRight(thing);
						} else {
							successor.rotateRight(thing);
						}
						return successor.parent;
					}
				}
				return successor;
			}
		}

		// recurse left
		else if (this.rank > pos) {
			this.rank--;
			this.left = this.left.delete(pos, thing);
			direction = "left";
		}

		// recurse right
		else {
			this.right = this.right.delete(pos - (this.rank + 1), thing);
			direction = "right";
		}

		// handle balance codes

		// needs rebalancing
		if (thing.needsBalancing) {

			// deleted in left subtree
			if (direction.equals("left")) {
				if (this.balance == Code.SAME) {
					this.balance = Code.RIGHT;
					thing.needsBalancing = false;
				} else if (this.balance == Code.LEFT) {
					this.balance = Code.SAME;
				} else if (this.balance == Code.RIGHT) {
					if (this.right.balance == Code.LEFT) {
						this.doubleRotateLeft(thing);
					} else if (this.right.balance == Code.RIGHT) {
						this.rotateLeft(thing);
					} else {
						this.rotateLeft(thing);
						this.balance = Code.RIGHT;
						this.parent.balance = Code.LEFT;
						thing.needsBalancing = false;
					}
					return this.parent;
				}
			}

			// deleted in right subtree
			else if (direction.equals("right")) {
				if (this.balance == Code.SAME) {
					this.balance = Code.LEFT;
					thing.needsBalancing = false;
				} else if (this.balance == Code.RIGHT) {
					this.balance = Code.SAME;
				} else if (this.balance == Code.LEFT) {
					if (this.left.balance == Code.RIGHT) {
						this.doubleRotateRight(thing);
					} else if (this.left.balance == Code.LEFT) {
						this.rotateRight(thing);
					} else {
						this.rotateRight(thing);
						this.balance = Code.LEFT;
						this.parent.balance = Code.RIGHT;
						thing.needsBalancing = false;
					}
					return this.parent;
				}
			}

		}
		return this;
	}

	/*
	 * We chose to implement a getSuccessor method to easily find a node's immediate
	 * successor and rebalance its right subtree separate from the delete method. We
	 * passed a mutable node into the function to make it easier to rebalance,
	 * rather than trying to return the pointer to the actual successor node.
	 * 
	 * Returns left-most element of a subtree and re-balances.
	 */
	public Node getSuccessor(RotationTracker thing, Node node) {
		if (this.left == EditTree.NULL_NODE) {
			// set passed node to the successor
			node.element = this.element;
			if (this.right != EditTree.NULL_NODE) {
				this.right.parent = this.parent;
			}
			thing.climb = true;
			return this.right;
		}
		this.left = this.left.getSuccessor(thing, node);
		this.rank--;

		// check balance codes
		if (thing.climb == true) {
			if (this.balance == Code.SAME) {
				this.balance = Code.RIGHT;
				thing.climb = false;
				thing.needsBalancing = false;
			} else if (this.balance == Code.LEFT) {
				this.balance = Code.SAME;
			} else if (this.balance == Code.RIGHT) {
				// needs balancing
				if (this.right.balance == Code.LEFT) {
					this.doubleRotateLeft(thing);
				} else {
					this.rotateLeft(thing);
				}
				return this.parent;
			}
		}
		return this;
	}

	// single left rotation
	private void rotateLeft(RotationTracker rotTracker) {
		if (this.parent == null) {
			// corner case
		} else if (this.parent.left == this) {
			this.parent.left = this.right;
		} else if (this.parent.right == this) {
			this.parent.right = this.right;
		}
		this.right.parent = this.parent;

		// top node
		this.parent = this.right;
		this.right = this.parent.left;
		this.right.parent = this;

		// right node
		this.parent.left = this;

		// balance codes
		this.balance = Code.SAME;
		this.parent.balance = Code.SAME;

		// ranks
		this.parent.rank += this.rank + 1;

		// increment number of rotations
		rotTracker.numRotations++;
	}

	// single right rotation
	private void rotateRight(RotationTracker rotTracker) {
		if (this.parent == null) {
			// corner case
		} else if (this.parent.left == this) {
			this.parent.left = this.left;
		} else if (this.parent.right == this) {
			this.parent.right = this.left;
		}
		this.left.parent = this.parent;

		// top node
		this.parent = this.left;
		this.left = this.parent.right;
		this.left.parent = this;

		// right node
		this.parent.right = this;

		// balance codes
		this.balance = Code.SAME;
		this.parent.balance = Code.SAME;

		// ranks
		this.rank -= (this.parent.rank + 1);

		// increment number of rotations
		rotTracker.numRotations++;
	}

	// Calls single right rotation and single left rotation; handles
	// balance codes for double left rotation
	public void doubleRotateLeft(RotationTracker rotTracker) {
		Code bottomBalance = this.right.left.balance;
		this.right.rotateRight(rotTracker);
		this.rotateLeft(rotTracker);
		if (bottomBalance == Code.RIGHT) {
			this.balance = Code.LEFT;
			this.parent.right.balance = Code.SAME;
		} else if (bottomBalance == Code.LEFT) {
			this.balance = Code.SAME;
			this.parent.right.balance = Code.RIGHT;
		} else {
			this.balance = Code.SAME;
			this.parent.right.balance = Code.SAME;
		}
	}

	// Calls single left rotation and single right rotation; handles
	// balance codes for double right rotation
	public void doubleRotateRight(RotationTracker rotTracker) {
		Code bottomBalance = this.left.right.balance;
		this.left.rotateLeft(rotTracker);
		this.rotateRight(rotTracker);
		if (bottomBalance == Code.RIGHT) {
			this.balance = Code.SAME;
			this.parent.left.balance = Code.LEFT;
		} else if (bottomBalance == Code.LEFT) {
			this.balance = Code.RIGHT;
			this.parent.left.balance = Code.SAME;
		} else {
			this.balance = Code.SAME;
			this.parent.left.balance = Code.SAME;
		}
	}

	// returns the height of this node in O(logN)
	public int height() {
		if (this == EditTree.NULL_NODE) {
			return -1;
		}
		if (this.balance == Code.LEFT) {
			return 1 + this.left.height();
		}
		return 1 + this.right.height();
	}

	// returns the size of this node's subtrees
	public int size() {
		if (this == EditTree.NULL_NODE) {
			return 0;
		}
		return this.rank + 1 + this.right.size();
	}

	// In-order traversal toString()
	@Override
	public String toString() {
		if (this == EditTree.NULL_NODE) {
			return "";
		}
		return this.left.toString() + this.element + this.right.toString();
	}

	// Pre-order traversal toString() with rank and balance info
	public String toDebugString() {
		if (this == EditTree.NULL_NODE) {
			return "";
		}
		return this.element + Integer.toString(this.rank) + this.balance.toString() + ", " + this.left.toDebugString()
				+ this.right.toDebugString();
	}

	// Create a height-balanced tree from a given input String "s"
	// MutableInteger used to store height info between each recursive call in
	// order to properly set balance codes
	public Node treeFromString(String s, MutableInt num, int left1, int right1) {
		if (left1 <= right1) {
			int index = (left1 + right1) / 2;
			char c = s.charAt(index);
			Node newNode = new Node(c);
			newNode.rank = index - left1;

			newNode.left = newNode.left.treeFromString(s, num, left1, index - 1);
			int leftHeight = num.i;
			newNode.right = newNode.right.treeFromString(s, num, index + 1, right1);
			int rightHeight = num.i;
			if (leftHeight == rightHeight) {
				newNode.balance = Code.SAME;
			} else if (leftHeight > rightHeight) {
				newNode.balance = Code.LEFT;
			} else {
				newNode.balance = Code.RIGHT;
			}
			num.i = Math.max(leftHeight, rightHeight) + 1;
			return newNode;
		}
		num.i = -1;
		return EditTree.NULL_NODE;

	}

	/*
	 * This function will recurse down the right branch of the tree until the height
	 * of the node it is at (p) minus the height of v is less than 1 and greater
	 * than 0, then it makes q the parent of both p and v and appends it to the
	 * tree, fixing balance codes on way back up.
	 * 
	 * Effectively performs concatenate function when the current tree is bigger
	 * than the tree to concatenate.
	 * 
	 * RotationTracker object used to track number of rotations and other
	 * rebalancing info.
	 */
	public Node concatenateRight(RotationTracker rotTracker, Node q, Node v, int pHeight, int vHeight) {
		int dif = pHeight - vHeight;
		// perform the concatenation at paste node q
		if (dif >= 0 && dif <= 1) {
			q.left = this;
			q.right = v;
			q.parent = this.parent;
			this.parent = q;
			v.parent = q;
			if (dif == 0) {
				q.balance = Code.SAME;
			} else if (dif == 1) {
				q.balance = Code.LEFT;
			}
			return q;
		} else if (dif > 1) {
			// determine direction of recursion
			q.rank -= (this.rank + 1);
			if (this.balance == Code.LEFT) {
				this.right = this.right.concatenateRight(rotTracker, q, v, pHeight - 2, vHeight);
			} else {
				this.right = this.right.concatenateRight(rotTracker, q, v, pHeight - 1, vHeight);
			}
		} else {
			// dif < 0 case handled in EditTree.concantenate(EditTree other)
		}

		// change balance codes
		if (rotTracker.needsBalancing) {
			if (this.balance == Code.SAME) {
				this.balance = Code.RIGHT;
			} else if (this.balance == Code.LEFT) {
				this.balance = Code.SAME;
				rotTracker.needsBalancing = false;
			} else if (this.balance == Code.RIGHT) {
				// rebalance needed
				rotTracker.needsBalancing = false;
				if (this.right.balance == Code.LEFT) {
					// double left
					this.doubleRotateLeft(rotTracker);
					return this.parent;
				}
				// single left
				this.rotateLeft(rotTracker);
				return this.parent;
			}
		}

		return this;
	}

	/*
	 * This function will recurse down the left branch of the input tree in the same
	 * manner as concatenateRight (described above).
	 * 
	 * Effectively performs concatenate function when the current tree is smaller
	 * than the tree to concatenate.
	 * 
	 * RotationTracker object used to track number of rotations and other
	 * rebalancing info.
	 */
	public Node concatenateLeft(RotationTracker rotTracker, Node q, Node v, int vHeight, int pHeight) {
		int dif = pHeight - vHeight;
		// perform concatenation
		if (dif >= 0 && dif <= 1) {
			q.right = this;
			q.left = v;
			q.parent = this.parent;
			this.parent = q;
			v.parent = q;
			if (dif == 0) {
				q.balance = Code.SAME;
			} else if (dif == 1) {
				q.balance = Code.RIGHT;
			}
			return q;
		} else if (dif > 1) {
			// determine direction of recursion
			if (this.balance == Code.RIGHT) {
				this.left = this.left.concatenateLeft(rotTracker, q, v, vHeight, pHeight - 2);
			} else {
				this.left = this.left.concatenateLeft(rotTracker, q, v, vHeight, pHeight - 1);
			}
		} else {
			// dif < 0 case handled in EditTree.concantenate(EditTree other)
		}

		if (rotTracker.needsBalancing) {
			if (this.balance == Code.SAME) {
				this.balance = Code.LEFT;
			} else if (this.balance == Code.RIGHT) {
				this.balance = Code.SAME;
				rotTracker.needsBalancing = false;
			} else if (this.balance == Code.LEFT) {
				// rebalance needed
				rotTracker.needsBalancing = false;
				if (this.left.balance == Code.LEFT) {
					this.rotateRight(rotTracker);
					return this.parent;
				} else if (this.left.balance == Code.RIGHT) {
					// double right
					this.doubleRotateRight(rotTracker);
					return this.parent;
				}
			}
		}

		return this;
	}

	/*
	 * This method is used to split the current tree at the indicated position. The
	 * left half of the original tree will be built in the EditTree object "S". The
	 * right half of the original tree is built in the object "T".
	 * 
	 * A RotationTracker object is used for counting rotations and other rotation
	 * tracking information.
	 */
	public void split(EditTree S, EditTree T, int pos, RotationTracker rotTracker) {
		String direction = "";
		// base case: found the position; begin process of splitting here.
		// This node will be added to its right subtree and stored in "T".
		// The "S" tree will be initialized to the left subtree of this node.
		if (this.rank == pos) {
			S.setRoot(this.left.copy());
			S.getRoot().parent = null;
			S.setSize(this.rank);
			T.setRoot(this.right.copy());
			T.getRoot().parent = null;
			T.setSize(this.size() - (this.rank + 1));
			T.add(this.element, 0);
			return;
		}

		// recurse left
		else if (this.rank > pos) {
			this.left.split(S, T, pos, rotTracker);
			direction = "left";
		}

		// recurse right
		else {
			this.right.split(S, T, pos - (this.rank + 1), rotTracker);
			direction = "right";
		}

		// If we returned from our right subtree, concatenate our left tree
		// to "S", using the current node as the "paste" node q.
		if (direction.equals("right")) {
			// The "p" subtree is defined as our left subtree
			// The "v" subtree is defined as the S tree
			// The result of (pHeight - vHeight) decides which direction to
			// concatenate
			int pHeight = this.left.height();
			int vHeight = S.height();
			if (pHeight > vHeight) {
				Node q = new Node(this.element);
				Node result = this.left.concatenateRight(rotTracker, q, S.getRoot(), pHeight, vHeight);
				S.setRoot(result);
			} else {
				Node q = new Node(this.element);
				Node result = S.getRoot().concatenateLeft(rotTracker, q, this.left, pHeight, vHeight);
				S.setRoot(result);
			}

		}

		// If we returned from our left subtree, concatenate our right tree
		// to "T", using the current node as the "paste" node q.
		else if (direction.equals("left")) {
			// The "p" subtree is defined as the T tree
			// The "v" subtree is defined as the our right subtree
			// The result of (pHeight - vHeight) decides which direction to
			// concatenate
			int pHeight = T.height();
			int vHeight = this.right.height();
			if (pHeight > vHeight) {
				Node q = new Node(this.element);
				Node result = T.getRoot().concatenateRight(rotTracker, q, this.right, pHeight, vHeight);
				T.setRoot(result);
			} else {
				Node q = new Node(this.element);
				Node result = this.right.concatenateLeft(rotTracker, q, T.getRoot(), pHeight, vHeight);
				T.setRoot(result);
			}
		}
	}

	// These methods are for use with the debughelp editor tree displayer/debugger.

	public int slowSize() {
		if (this == EditTree.NULL_NODE) {
			return 0;
		}
		return (1 + this.left.slowSize() + this.right.slowSize());
	}

	public int slowHeight() {
		if (this == EditTree.NULL_NODE) {
			return -1;
		}
		return Math.max(this.left.slowHeight(), this.right.slowHeight()) + 1;
	}

	public DisplayableNodeWrapper getDisplayableNodePart() {
		return this.display;
	}

	public boolean hasLeft() {
		return (this.left != EditTree.NULL_NODE);
	}

	public boolean hasRight() {
		return (this.right != EditTree.NULL_NODE);
	}

	public boolean hasParent() {
		return (this.parent != null);
	}

	public int getRank() {
		return this.rank;
	}

	public Code getBalance() {
		return this.balance;
	}

	public char getElement() {
		return this.element;
	}

	public Node getParent() {
		return this.parent;
	}

	public Node getLeft() {
		return this.left;
	}

	public Node getRight() {
		return this.right;
	}

}