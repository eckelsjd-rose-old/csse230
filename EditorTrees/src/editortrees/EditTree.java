package editortrees;

/**
 * 
 * A height-balanced binary tree (AVL). Could be basis for a text editor.
 * Implements common List ADT methods.
 *
 * @author eckelsjd, snidersa Created Oct 9, 2019.
 */
public class EditTree {

	private Node root;
	static final Node NULL_NODE = new Node();
	private int rotations = 0;
	private int size;
	DisplayableBinaryTree display;

	/**
	 * MILESTONE 1 Construct an empty tree
	 * 
	 * DONE
	 */
	public EditTree() {
		this.root = NULL_NODE;
		this.size = 0;
	}

	/**
	 * MILESTONE 1 Construct a single-node tree whose element is ch
	 * 
	 * @param ch
	 * 
	 *           DONE
	 */
	public EditTree(char ch) {
		this.root = new Node(ch);
		this.size = 1;
	}

	/**
	 * MILESTONE 2 Make this tree be a copy of e, with all new nodes, but the same
	 * shape and contents.
	 * 
	 * @param e
	 */
	public EditTree(EditTree e) {
		Node newRoot = e.root.copy();
		this.root = newRoot;
		this.size = e.size();
	}

	/**
	 * MILESTONE 3 Create an EditTree whose toString is s. This can be done in O(N)
	 * time, where N is the size of the tree (note that repeatedly calling insert()
	 * would be O(N log N), so you need to find a more efficient way to do this.
	 * 
	 * @param s
	 */
	public EditTree(String s) {
		this.root = NULL_NODE;
		MutableInt num = new MutableInt();
		this.root = this.root.treeFromString(s, num, 0, s.length() - 1);
		this.size = s.length();
	}

	/**
	 * MILESTONE 1 returns the total number of rotations done in this tree since it
	 * was created. A double rotation counts as two.
	 *
	 * @return number of rotations since this tree was created.
	 * 
	 *         DONE
	 */
	public int totalRotationCount() {
		return this.rotations;
	}

	/**
	 * MILESTONE 1 return the string produced by an inorder traversal of this tree
	 * 
	 * DONE
	 */
	@Override
	public String toString() {
		return this.root.toString();
	}

	/**
	 * MILESTONE 1 This one asks for more info from each node. You can write it like
	 * the arraylist-based toString() method from the BinarySearchTree assignment.
	 * However, the output isn't just the elements, but the elements, ranks, and
	 * balance codes. Former CSSE230 students recommended that this method, while
	 * making it harder to pass tests initially, saves them time later since it
	 * catches weird errors that occur when you don't update ranks and balance codes
	 * correctly. For the tree with root b and children a and c, it should return
	 * the string: [b1=, a0=, c0=] There are many more examples in the unit tests.
	 * 
	 * @return The string of elements, ranks, and balance codes, given in a
	 *         pre-order traversal of the tree.
	 * 
	 *         DONE
	 */
	public String toDebugString() {
		String string = this.root.toDebugString();
		if (string != "") {
			string = string.substring(0, string.length() - 2);
		}
		string = "[" + string + "]";

		return string;
	}

	/**
	 * MILESTONE 1
	 * 
	 * @param ch character to add to the end of this tree.
	 * 
	 *           DONE
	 */
	public void add(char ch) {
		RotationTracker count = new RotationTracker(0);
		this.root = this.root.add(ch, this.size(), count);
		this.size++;
		this.rotations += count.numRotations;

		// handle case where the root was rotated left or right
		// root.parent should point at null always
		if (this.root.parent != null) {
			this.root = this.root.parent;
		}

	}

	/**
	 * MILESTONE 1
	 * 
	 * @param ch  character to add
	 * @param pos character added in this inorder position
	 * @throws IndexOutOfBoundsException if pos is negative or too large for this
	 *                                   tree
	 */
	public void add(char ch, int pos) throws IndexOutOfBoundsException {
		if (pos < 0 || pos > this.size()) {
			throw new IndexOutOfBoundsException();
		}

		RotationTracker count = new RotationTracker(0);
		this.root = this.root.add(ch, pos, count);
		this.size++;
		this.rotations += count.numRotations;
	}

	/**
	 * MILESTONE 1
	 * 
	 * @param pos position in the tree
	 * @return the character at that position
	 * @throws IndexOutOfBoundsException
	 */
	public char get(int pos) throws IndexOutOfBoundsException {
		if (pos < 0 || pos >= this.size()) {
			throw new IndexOutOfBoundsException();
		}
		return this.root.get(pos);
	}

	/**
	 * MILESTONE 1
	 * 
	 * @return the height of this tree
	 * 
	 *         DONE
	 */
	public int height() {
		return this.root.height();
	}

	/**
	 * MILESTONE 2
	 * 
	 * @return the number of nodes in this tree, not counting the NULL_NODE if you
	 *         have one.
	 */
	public int size() {
		return this.size;
	}

	/**
	 * MILESTONE 2
	 * 
	 * @param pos position of character to delete from this tree
	 * @return the character that is deleted
	 * @throws IndexOutOfBoundsException
	 */
	public char delete(int pos) throws IndexOutOfBoundsException {
		// Implementation requirement:
		// When deleting a node with two children, you normally replace the
		// node to be deleted with either its in-order successor or predecessor.
		// The tests assume assume that you will replace it with the
		// *successor*.
		if (pos < 0 || pos >= this.size()) {
			throw new IndexOutOfBoundsException();
		}
		RotationTracker thing = new RotationTracker(0);
		thing.needsBalancing = true;
		this.root = this.root.delete(pos, thing);
		this.size--;
		this.rotations += thing.numRotations;
		return thing.deletedItem;
	}

	/**
	 * MILESTONE 3, EASY This method operates in O(length*log N), where N is the
	 * size of this tree.
	 * 
	 * @param pos    location of the beginning of the string to retrieve
	 * @param length length of the string to retrieve
	 * @return string of length that starts in position pos
	 * @throws IndexOutOfBoundsException unless both pos and pos+length-1 are
	 *                                   legitimate indexes within this tree.
	 */
	public String get(int pos, int length) throws IndexOutOfBoundsException {
		if (pos < 0 || pos + length > this.size) {
			throw new IndexOutOfBoundsException();
		}
		MutableInt num = new MutableInt(length);
		String sol = this.root.get(pos, num);
		return sol;
	}

	/**
	 * MILESTONE 3, MEDIUM - SEE THE PAPER REFERENCED IN THE SPEC FOR ALGORITHM!
	 * Append (in time proportional to the log of the size of the larger tree) the
	 * contents of the other tree to this one. Other should be made empty after this
	 * operation.
	 * 
	 * @param other
	 * @throws IllegalArgumentException if this == other
	 * 
	 *                                  link to article that details how concatenate
	 *                                  should work
	 *                                  https://www.rose-hulman.edu/class/csse/csse230/202010/Programs/EditorTrees/ReingoldHansenDataStructuresInPascal.pdf
	 */
	public void concatenate(EditTree other) throws IllegalArgumentException {
		RotationTracker rotTracker = new RotationTracker(0);
		if (this == other) {
			throw new IllegalArgumentException();
		}
		if (this.root == NULL_NODE) {
			// if this tree is empty, then simply make it equal to other
			this.root = other.root;
		} else if (other.root != NULL_NODE) {
			rotTracker.needsBalancing = true;
			// concatenate into this.right
			if (this.height() > other.height()) {
				char leftMost = other.delete(0);
				Node q = new Node(leftMost);
				q.rank = this.size();
				// recursively finds the location to append other and makes the change
				this.root = this.root.concatenateRight(rotTracker, q, other.root, this.height(), other.height());
			}

			// concatenate into other.left
			else {
				char rightMost = this.delete(this.size() - 1);
				Node q = new Node(rightMost);
				q.rank = this.size();

				this.root = other.root.concatenateLeft(rotTracker, q, this.root, this.height(), other.height());
			}
		}
		// increase this size and make other empty
		this.rotations += rotTracker.numRotations;
		this.size += other.size() + 1;
		other.clear();
	}

	/**
	 * MILESTONE 3: DIFFICULT This operation must be done in time proportional to
	 * the height of this tree.
	 * 
	 * As of 10/28/19, this method not fully debugged. Passes few of test cases.
	 * 
	 * @param pos where to split this tree
	 * @return a new tree containing all of the elements of this tree whose
	 *         positions are >= position. Their nodes are removed from this tree.
	 * @throws IndexOutOfBoundsException
	 */
	public EditTree split(int pos) throws IndexOutOfBoundsException {
		if (pos < 0 || pos >= this.size()) {
			throw new IndexOutOfBoundsException();
		}
		RotationTracker rotTracker = new RotationTracker(0);
		EditTree T = new EditTree(); // T will be the right half of tree
		EditTree S = new EditTree(); // S will be the left half of tree
		this.root.split(S, T, pos, rotTracker);
		T.size = this.size - pos;
		S.size = this.size - T.size;
		this.root = S.root;
		this.size = S.size;
		return T;
	}

	/**
	 * MILESTONE 3: JUST READ IT FOR USE OF SPLIT/CONCATENATE This method is
	 * provided for you, and should not need to be changed. If split() and
	 * concatenate() are O(log N) operations as required, delete should also be
	 * O(log N)
	 * 
	 * @param start  position of beginning of string to delete
	 * 
	 * @param length length of string to delete
	 * @return an EditTree containing the deleted string
	 * @throws IndexOutOfBoundsException unless both start and start+length-1 are in
	 *                                   range for this tree.
	 */
	public EditTree delete(int start, int length) throws IndexOutOfBoundsException {
		if (start < 0 || start + length >= this.size())
			throw new IndexOutOfBoundsException(
					(start < 0) ? "negative first argument to delete" : "delete range extends past end of string");
		EditTree t2 = this.split(start);
		EditTree t3 = t2.split(length);
		this.concatenate(t3);
		return t2;
	}

	/**
	 * MILESTONE 3 Don't worry if you can't do this one efficiently.
	 * 
	 * @param s the string to look for
	 * @return the position in this tree of the first occurrence of s; -1 if s does
	 *         not occur
	 */
	public int find(String s) {
		return this.find(s, 0);
	}

	/**
	 * MILESTONE 3
	 * 
	 * @param s   the string to search for
	 * @param pos the position in the tree to begin the search
	 * @return the position in this tree of the first occurrence of s that does not
	 *         occur before position pos; -1 if s does not occur
	 */
	public int find(String s, int pos) {
		// emptry string case
		if (s.length() == 0) {
			return 0;
		}

		// empty tree case
		if (this.root == EditTree.NULL_NODE) {
			return -1;
		}

		// check corner cases
		if (pos < 0 || pos + s.length() > this.size()) {
			throw new IndexOutOfBoundsException(
					(pos < 0) ? "negative starting position" : "search range extends past tree size");
		}

		int i = pos;
		int max = this.size() - s.length();
		while (i <= max) {
			if (this.get(i) == s.charAt(0)) {
				if (s.equals(this.get(i, s.length()))) {
					return i;
				}
			}
			i++;
		}
		return -1;
	}

	public void clear() {
		this.root = NULL_NODE;
		this.size = 0;
		this.rotations = 0;
	}

	// The following functions are for use in debughelp GUI.

	/**
	 * @return The root of this tree.
	 */
	public Node getRoot() {
		return this.root;
	}

	public int slowHeight() {
		return this.root.slowHeight();
	}

	public int slowSize() {
		return this.root.slowSize();
	}

	public void show() {
		if (this.display == null) {
			this.display = new DisplayableBinaryTree(this, 960, 1080, true);
		} else {
			this.display.show(true);
		}
	}

	public void close() {
		if (this.display != null) {
			this.display.close();
		}
	}

	// Two functions for use during split() method.

	public void setRoot(Node n) {
		this.root = n;
	}

	public void setSize(int i) {
		this.size = i;
	}

	/**
	 * 
	 * RotationTracker class created to store many values needed by various
	 * EditorTree recursive function calls:
	 * 
	 * numRotations - track number of rotations performed during a method
	 * needsBalancing - boolean passed up recursive calls to inform parent nodes
	 * about height changes in its subtrees climb - boolean used for special case
	 * switching: true when returning up a tree; false otherwise deletedItem -
	 * stores the character deleted during EditTree.delete()
	 *
	 * @author eckelsjd, snidersa Created Oct 28, 2019.
	 */
	class RotationTracker {
		int numRotations;
		boolean needsBalancing;
		boolean climb;
		char deletedItem;

		RotationTracker(int i) {
			this.numRotations = i;
			this.needsBalancing = false;
			this.climb = false;
		}
	}

	/**
	 * 
	 * General Mutable Integer class for passing an integer through recursive
	 * function calls.
	 *
	 * @author eckelsjd snidersa Created Oct 8, 2019.
	 */
	class MutableInt {
		int i;

		MutableInt() {
			this.i = 0;
		}

		MutableInt(int num) {
			this.i = num;
		}
	}
}
