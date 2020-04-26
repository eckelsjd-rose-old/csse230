import java.util.EmptyStackException;
import java.util.Stack;

/**
 * 
 * A ChainManager class that stores chains in a stack data structure.
 * Travers a 'tree of chains' in "pre-order" fashion.
 *
 * @author Joshua Eckels and Tom Meehan
 *         Created Sep 24, 2019.
 */
public class StackChainManager extends ChainManager {

	private Stack<Chain> s;
	
	public StackChainManager() {
		this.s = new Stack<>();
	}

	@Override
	public void add(Chain chain) {
		this.s.push(chain);
	}

	@Override
	public Chain next() {
		try {
			return this.s.pop();
		} catch (EmptyStackException e) {
			return null;
		}
	}

	@Override
	public boolean isEmpty() {
		return this.s.isEmpty();
	}
	
	@Override
	public int size() {
		return this.s.size();
	}
}
