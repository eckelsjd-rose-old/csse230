import java.util.PriorityQueue;

public class PriorityQueueChainManager extends ChainManager {

	private PriorityQueue<Entry> pq;
	private String target;
	
	public PriorityQueueChainManager(String target) {
		this.pq = new PriorityQueue<>();
		this.target = target;
	}
	
	@Override
	public void add(Chain chain) {
		Entry toAdd = new Entry(chain);
		this.pq.add(toAdd);
	}

	@Override
	public Chain next() {
		Entry minEntry = this.pq.remove();
		return minEntry.getChain();
	}

	@Override
	public boolean isEmpty() {
		return pq.isEmpty();
	}

	@Override
	public int size() {
		return pq.size();
	}
	
	private int distance(String last) {
		int count = 0;
		for(int i = 0; i < last.length(); i++) {
			if(last.charAt(i) != target.charAt(i)) {
				count++;
			}
		}
		return count;
	}

	private class Entry implements Comparable<Entry> {

		private Chain c;
		private int estimatedLength;
		
		public Entry(Chain c) {
			this.c = c;
			this.estimatedLength = this.c.length() + distance(c.getLast());
		}
		
		@Override
		public int compareTo(Entry o) {
			if(o.getEstimatedLength() > this.estimatedLength) {
				return -1;
			} else if(o.getEstimatedLength() == this.estimatedLength) {
				return 0;
			} else {
				return 1;
			}
		}
		
		public Chain getChain() {
			return this.c;
		}
		
		
		public int getEstimatedLength() {
			return this.estimatedLength;
		}
	}
}
