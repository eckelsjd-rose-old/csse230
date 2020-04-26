import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Set;

/**
 * A class to find a chain of words between a pair of words (a doublet) that
 * successfully solves the Doublet puzzle.
 * 
 * @author Joshua Eckels and Tom Meehan
 * 		   9/24/2019
 */
public class Doublets {
	private LinksInterface links;
	
	public Doublets(LinksInterface links) {
		this.links = links;
	}

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Welcome to Doublets, a game of 'verbal torture'");
		LinksInterface link = new Links("../DoubletsData/english.cleaned.all.10.txt");
		Doublets doublet = new Doublets(link);
		
		while(true) {
			System.out.println("Enter starting word:");
			String first = scanner.nextLine();
			System.out.println("Enter ending word:");
			String end = scanner.nextLine();
			if(!link.exists(first) || !link.exists(end)) {
				System.out.println("Invalid word");
			} else {
				boolean validManager = false;
				//necesary for initializing, cannot move past loop until valid manager is selected
				ChainManager manager = new StackChainManager();
				while(!validManager) {
					System.out.println("Enter chain manager (s: stack, q: queue, p: priorityqueue, x: exit):");
					String type = scanner.nextLine();
					switch(type) {
					case "s":
						manager = new StackChainManager();
						validManager = true;
						break;
					case "q":
						manager = new QueueChainManager();
						validManager = true;
						break;
					case "p":
						manager = new PriorityQueueChainManager(end);
						validManager = true;
						break;
					case "x":
						System.out.println("Goodbye!");
						return;
					default:
						System.out.println("Invalid manager selection, try again");
						break;
					}
				}
				Chain foundChain = doublet.findChain(first, end, manager);
				if(foundChain != null) {
					//print completed chain
					System.out.println(foundChain.toString());
					System.out.println("Length: " + foundChain.length());
					System.out.println("Candidates: " + manager.getNumberOfNexts());
					System.out.println("Max Size: " + manager.maxSize());
				} else {
					System.out.println("No doublet chain exists from " + first + " to " + end);
				}
			}
			
		}
	}

	/**
	 * 
	 * Starts at beginning string "start" and returns a valid chain of words to "end", such that 
	 * the chain follows the rules of the Doublets puzzle.
	 *
	 * @param start
	 * @param end
	 * @param manager
	 * @return a Chain from "start" to "end" if it exists; else: null
	 */
	public Chain findChain(String start, String end, ChainManager manager) {
		
		// start and end words have to be same length
		if (end.length() != start.length()) { return null; }
		
		Chain initial = new Chain();
		Chain newChain = initial.addLast(start);
	
		manager.add(newChain);
		
		//only run while there are chains left to check
		while(!manager.isEmpty()) {
			//get next chain
			Chain current = manager.next();
			manager.incrementNumNexts();
			//get the last word from that chain
			String last = current.getLast();
			
			//this if executes if the last word of the chain does not equal the target word
			if(!last.equals(end)) {
				//check if the word exists in our link map
				if(this.links.exists(last)) {
					//get the candidates for the last word in the chain
					Set<String> candidates = this.links.getCandidates(last);
					if (candidates == null) {
						continue;
					}
					for(String s : candidates) {
						Chain chainToAdd = current.addLast(s);
						// only adds chain if this is a non-repeated string 
						if (!(chainToAdd == null)) {
							manager.add(chainToAdd);
							manager.updateMax(manager.size());
						}	
					}
				}
			//if last does equal the target string this will execute
			} else {
				return current;
			}
		}
		return null;
	}

}
