package mirror;
import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.Test;



/**
 * Tests the BinaryNode mirror() static method. 
 *
 * @author Matt Boutell.
 *         Created May 24, 2011.
 */
public class MirrorTest {

	private static int maxPoints = 0;
	private static int points = 0;
	
	/**
	 * Test method for {@link BinaryNode#mirror(BinaryNode)}.
	 */
	@Test(timeout=1000)
	public void testMirrorEmpty() {
		BinaryNode t = null;
		testMirror("EmptyTree", t, 2);
	}
	
	/**
	 * Test method for {@link BinaryNode#mirror(BinaryNode)}.
	 */
	@Test(timeout=1000)
	public void testMirrorOneElement() {
		BinaryNode leaf = new BinaryNode(5, null, null);
		testMirror("OneElement", leaf, 3);
	}
	
	/**
	 * Test method for {@link BinaryNode#mirror(BinaryNode)}.
	 */
	@Test(timeout=1000)
	public void testMirrorHeightIs1() {
		BinaryNode leaf1 = new BinaryNode(4, null, null);
		BinaryNode leaf2 = new BinaryNode(6, null, null);
		BinaryNode root = new BinaryNode(5, leaf1, leaf2);
		testMirror("HeightIs1", root, 4);
	}

	
	/**
	 * Test method for {@link BinaryNode#mirror(BinaryNode)}.
	 */
	@Test(timeout=1000)
	public void testMirrorHeightIs2() {
		BinaryNode leaf2 = new BinaryNode(2, null, null);
		BinaryNode leaf4 = new BinaryNode(4, null, null);
		BinaryNode leaf6 = new BinaryNode(6, null, null);
		BinaryNode leaf8 = new BinaryNode(8, null, null);
		BinaryNode inner3 = new BinaryNode(3, leaf2, leaf4);
		BinaryNode inner7 = new BinaryNode(7, leaf6, leaf8);
		BinaryNode root = new BinaryNode(5, inner3, inner7);
		testMirror("HeightIs2", root, 7);
	}

	/**
	 * Test method for {@link BinaryNode#mirror(BinaryNode)}.
	 */
	@Test(timeout=1000)
	public void testMirrorDifferentDepths() {
		BinaryNode leaf2 = new BinaryNode(2, null, null);
		BinaryNode leaf4 = new BinaryNode(4, null, null);
		BinaryNode leaf6 = new BinaryNode(6, null, null);
		BinaryNode inner1 = new BinaryNode(1, null, leaf2);
		BinaryNode inner3 = new BinaryNode(3, inner1, leaf4);
		BinaryNode inner7 = new BinaryNode(7, leaf6, null);
		BinaryNode root = new BinaryNode(5, inner3, inner7);
		testMirror("DifferentDepths", root, 8);
	}
	
	/** Displays points */
	@AfterClass
	public static void displayPoints() {
		System.out.println("------------------------------------------------------");
		System.out.printf("#1 MIRROR earned %d/%d points\n", points, maxPoints);
		System.out.println("------------------------------------------------------");
		System.out.println();
	}
		
	/**
	 * Helper method for our tests.
	 * 
	 * @param s
	 * @return The input string s in reverse order ("4751"-->"1574")
	 */
	private String reverse(String s) {
		String result = "";
		for (int i = 0; i < s.length(); i++) {
			result = s.charAt(i) + result;
		}
		return result;
	}
	
	private void testMirror(String testName, BinaryNode original, int possiblePoints){
		maxPoints += possiblePoints;
		BinaryNode purportedMirror = BinaryNode.mirror(original);
		String  io = inOrderTraversalString(original),
				ip = inOrderTraversalString(purportedMirror),
				ro = preOrderTraversalString(original),
				rp = preOrderTraversalString(purportedMirror),
				oo = postOrderTraversalString(original),
				op = postOrderTraversalString(purportedMirror);
		int		ho = height(original),
				hp = height(purportedMirror);
		int pointsThisTest = possiblePoints;
		System.out.print("Test " + testName + ":   ");
		if (!ip.equals(reverse(io))) {
			System.out.print("\n  error in inorder test: " + io + " " + ip + "\n  ");
			pointsThisTest = 0;
		}
		if (!op.equals(reverse(ro))) {
			System.out.print("\n  error in pre/post test: " + ro + " " + op + "\n  ");
			pointsThisTest = 0;
		}
		if (!rp.equals(reverse(oo))) {
			System.out.print("\n  error in post/pre test: " + oo + " " + rp + "\n  ");
			pointsThisTest = 0;
		}
		if (ho != hp) {
			System.out.print("\n  error in height test: " + ho + " " + hp + "\n  ");
			pointsThisTest = 0;
		}
		System.out.println("Points from this test: " + pointsThisTest + "/" + possiblePoints);
		if (pointsThisTest == 0) {
			assertTrue(false);
		}
		points += pointsThisTest;

	}
	
	private String preOrderTraversalString(BinaryNode t) {
		if (t == null) return "";
		
		return t.element + preOrderTraversalString(t.left) + preOrderTraversalString(t.right);
	}
	
	private String inOrderTraversalString(BinaryNode t) {
		if (t == null) return "";
		return inOrderTraversalString(t.left) + t.element + inOrderTraversalString(t.right);
	}

	private String postOrderTraversalString(BinaryNode t) {
		if (t == null) return "";
		
		return postOrderTraversalString(t.left) + postOrderTraversalString(t.right) + t.element;
	}
	
	private int height(BinaryNode t) {
		return t==null ? -1 : 1 + Math.max(height(t.left),  height(t.right));
	}
	
	public static void main(String [] args) {
		new MirrorTest().testMirrorOneElement();
		
	}


}
