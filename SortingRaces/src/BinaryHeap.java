import java.lang.reflect.Array;
import java.util.Comparator;

public class BinaryHeap<T extends Comparable<? super T>> {

	private static final int INITIAL_CAPACITY = 5;

	private T[] heap;
	private Class<T> type;
	private int size;
	private Comparator<T> comp; // will allow us to switch between NaturalComparator
								// and ReverseComparator, enabling min-heap or
								// max-heap respectively
	
	@SuppressWarnings("unchecked")
	public BinaryHeap(Class<T> type) {
		this.type = type;
		this.size = 0;
		this.heap = (T[]) Array.newInstance(this.type, INITIAL_CAPACITY);
		this.comp = new NaturalComparator();
	}

	public BinaryHeap(T[] array, Class<T> type, boolean isMinHeap) {
		this.type = type;
		this.heap = array;
		this.size = array.length - 1; // assume full array passed in
		
		if (isMinHeap) { this.comp = new NaturalComparator(); }
		else { this.comp = new ReverseComparator(); }

		this.buildHeap(); // by default will create a min-heap
		
		// at this point, we should have a max heap (isMinHeap=false)
		
	}
	
	public void buildHeap() {
		for (int i = this.size / 2; i > 0; i--) {
			this.percolateDown(i);
		}
	}

	public T deleteMin() {
		T returnValue = this.findMin();
		if (returnValue == null) { return null; }
		
		// put last item of heap into the hole
		int hole = 1;
		this.heap[hole] = this.heap[this.size--];
		this.percolateDown(hole);
		this.heap[this.size + 1] = null;
		
		// shrink if necessary
//		if ((this.size < this.length()/2) && (this.length() != INITIAL_CAPACITY)) {
//			this.shrink();
//		}
		
		return returnValue;
	}
	
	public void percolateDown(int hole) {
		int child;
		//T tmp = this.heap[hole]; // last heap item stored at the hole
		
		for( ; hole * 2 <= this.size; hole = child) {
			// start at left child
			child = 2 * hole;
			
			// check and move to right child if right < left
			if (child != this.size && this.comp.compare(this.heap[child+1], this.heap[child]) < 0) {
				child++;
			} 
			// compare child to parent
			if (this.comp.compare(this.heap[child], this.heap[hole]) < 0) {
				T parent = this.heap[hole];
				this.heap[hole] = this.heap[child];
				this.heap[child] = parent;
			} else {
				break;
			}
		}
		// this.heap[this.size + 1] = null; // clear last element
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[null");
		for (int i = 1; i <= this.size; i++) {
			sb.append(", ");
			sb.append(heap[i].toString());
		}
		sb.append("]");
		return sb.toString();
	}
	
	public int size() {
		return this.size;
	}
	
	public int length() {
		return this.heap.length;
	}

	public void insert(T item) {
		// resize
		if (this.size == this.heap.length - 1) {
			this.resize();
		}
		// add at end
		int i = this.size+1;
		this.heap[i] = item;
		this.percolateUp(i);
		this.size++;
	}
	
	public void percolateUp(int i) {
		while (i>1) {
			int parent = i/2;
			if (this.comp.compare(this.heap[parent], this.heap[i]) > 0) {
				T temp = this.heap[parent];
				this.heap[parent] = this.heap[i];
				this.heap[i] = temp;
			}
			i /= 2;
		}
	}
	
	public T findMin() {
		return this.heap[1];
	}
	
	@SuppressWarnings("unchecked")
	public void resize() {
		T[] bigArray = (T[]) Array.newInstance(this.type, this.heap.length * 2);
		for (int i = 1; i <= this.size; i++) {
			bigArray[i] = this.heap[i];
		}
		this.heap = bigArray;
	}
	
	@SuppressWarnings("unchecked")
	public void shrink() {
		T[] smallArray = (T[]) Array.newInstance(this.type, this.heap.length / 2);
		for (int i = 1; i <= this.size; i++) {
			smallArray[i] = this.heap[i];
		}
		this.heap = smallArray;
	}

	public static<T extends Comparable<? super T>> void sort(T[] array, Class<T> type) {
		// Before creating heap, swap min element into 0th entry so we can safely ignore it in the heap steps
		// In other words, do one basic step of selection sort. (adds an O(n) step which is negligible)
		int minIdx = 0;
		T min = array[minIdx];
		for (int i = 0; i < array.length; i++) {
			if (array[i].compareTo(min) < 0) {
				minIdx = i;
				min = array[minIdx];
			}
		}
		T temp = array[0];
		array[0] = min;
		array[minIdx] = temp;
		
		BinaryHeap<T> heap = new BinaryHeap<>(array,type, false);
		
		// Run N deleteMax's (syntax will read deleteMin and put results at just-vacated
		// entries of array
		while (heap.size > 1) {
			T max = heap.deleteMin();
			heap.heap[heap.size+1] = max;
		}
	}
	
	// for min-heap
	class NaturalComparator implements Comparator<T> {

		@Override
		public int compare(T o1, T o2) {
			return o1.compareTo(o2);
		}
	}
	
	// for max-heap
	class ReverseComparator implements Comparator<T> {

		@Override
		public int compare(T o1, T o2) {
			return o2.compareTo(o1);
		}
	}
}
