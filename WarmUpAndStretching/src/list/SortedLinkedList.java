package list;

/**
 * 
 * @author anderson
 * 
 * @param <T>
 *            Any Comparable type
 * 
 *            A linked list whose elements are kept in sorted order.
 */
public class SortedLinkedList<T extends Comparable<T>> extends
		DoublyLinkedList<T> {

	/**
	 * Create an empty list
	 * 
	 */
	public SortedLinkedList() {
		super();
	}

	/**
	 * Creates a sorted list containing the same elements as the parameter
	 * 
	 * @param list
	 *            the input list
	 */
	public SortedLinkedList(DoublyLinkedList<T> list) {
		super();
		DLLIterator<T> i = list.iterator();
		while (i.hasNext()) {
			this.add(i.next());
		}
	}

	/**
	 * Adds the given element to the list, keeping it sorted.
	 */
	@Override
	public void add(T element) {
		// DONE: implement this method
		if (this.head.next == this.tail) {
			super.addFirst(element);
			return;
		}
		
		Node current = this.head.next;
		while(element.compareTo(current.data) >= 0) {
			// handle tail case
			if (current.next == this.tail) {
				current.addAfter(element);
				return;
			}
			current = current.next;
		}
		current = current.prev;
		current.addAfter(element);
	}

	@Override
	public void addFirst(T element) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void addLast(T element) {
		throw new UnsupportedOperationException();
	}
}
