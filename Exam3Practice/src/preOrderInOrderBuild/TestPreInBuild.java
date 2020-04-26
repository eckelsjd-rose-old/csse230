package preOrderInOrderBuild;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests the pre-order in-order build method.
 * 
 * @author boutell. Created Jan 29, 2014.
 */
public class TestPreInBuild {

	static int points = 0;
	
	
	/**
	 * Test method for
	 * {@link preOrderInOrderBuild.PreorderInorderBuild#buildFromPreOrderInorder(java.lang.String, java.lang.String)}
	 * .
	 */
	@Test
	public void testNull() {
		BinaryNode t = PreorderInorderBuild.buildFromPreOrderInorder("", "");
		assertNull(t);
		points += 1;
	}
	
	@Test
	public void testPreIn2() {
		BinaryNode t = PreorderInorderBuild.buildFromPreOrderInorder("A", "A");
		assertEquals(1, BinaryNode.size(t));
		assertEquals(0, BinaryNode.height(t));
		points += 1;
	}

	@Test
	public void testPreIn3() {
		BinaryNode t = PreorderInorderBuild
				.buildFromPreOrderInorder("AB", "AB");
		assertEquals(2, BinaryNode.size(t));
		assertEquals(1, BinaryNode.height(t));
		assertNull(t.left);
		assertNotNull(t.right);
		assertEquals(new Character('B'), (Character) (t.right.element));
		points += 2;
	}

	@Test
	public void testPreIn4() {
		BinaryNode t = PreorderInorderBuild
				.buildFromPreOrderInorder("AB", "BA");
		assertEquals(2, BinaryNode.size(t));
		assertEquals(1, BinaryNode.height(t));
		assertNull(t.right);
		assertNotNull(t.left);
		assertEquals(new Character('B'), (Character) (t.left.element));
		assertNull(t.left.right);
		points += 3;
	}

	@Test
	public void testPreIn5() {
		BinaryNode t = PreorderInorderBuild.buildFromPreOrderInorder("ABC",
				"BAC");
		assertEquals(3, BinaryNode.size(t));
		assertEquals(1, BinaryNode.height(t));
		assertNotNull(t.right);
		assertNull(t.right.right);
		assertNotNull(t.left);
		assertEquals(new Character('B'), (Character) (t.left.element));
		assertNull(t.left.right);
		points += 4;
	}

	@Test
	public void testPreIn6() {
		BinaryNode t = PreorderInorderBuild.buildFromPreOrderInorder(
				"ABCDEFGHI", "BDCFEAIHG");
		assertEquals(9, BinaryNode.size(t));
		assertEquals(4, BinaryNode.height(t));
		assertNotNull(t.right);
		assertEquals(new Character('F'),
				(Character) (t.left.right.right.left.element));
		assertNull(t.left.left);
		points += 6;
	}

	@Test
	public void testPreIn7() {
		BinaryNode t = PreorderInorderBuild.buildFromPreOrderInorder(
				"ABCDEFGHI", "IHGFEDCBA");
		assertEquals(9, BinaryNode.size(t));
		assertEquals(8, BinaryNode.height(t));
		assertNull(t.right);
		assertEquals(new Character('E'),
				(Character) (t.left.left.left.left.element));
		assertNull(t.left.right);
		points += 7;
	}

	@AfterClass
	public static void displayPoints() {
		System.out.println("------------------------------------------------------");
		System.out.printf("#2 PREORDER/INORDER BUILD earned %d/24 points\n", points);
		System.out.println("------------------------------------------------------");
		System.out.println();
		
	}
}
