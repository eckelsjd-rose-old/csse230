package deduplicatingQueue;

public interface SimpleQueue<T> {
	boolean isEmpty();
	void enqueue(T item);
	T dequeue();
}
