import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * 
 * A ChainManager class that stores chains in a queue data structure.
 * Traverses a 'tree of chains' in "breadth-order".
 *
 * @author Joshua Eckels and Tom Meehan
 *         Created Sep 24, 2019.
 */
public class QueueChainManager extends ChainManager{

	private LinkedList<Chain> ls;
	
	public QueueChainManager() {
		this.ls = new LinkedList<>();
	}

	@Override
	public void add(Chain chain) {
		this.ls.add(chain);
	}

	@Override
	public Chain next() {
		try {
			return this.ls.removeFirst();
		} catch (NoSuchElementException e) {
			return null;
		}
	}

	@Override
	public boolean isEmpty() {
		
		return this.ls.isEmpty();
	}
	
	@Override
	public int size() {
		return this.ls.size();
	}

}
