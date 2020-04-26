package tree;

import static org.junit.Assert.assertEquals;

import org.junit.AfterClass;
import org.junit.Test;

public class TreeTest {
	private static int Points = 0;

	// -----------------------------------------------------------------------

	@Test
	public void testEmptyAndRootOnly() {
		BinarySearchTree t = new BinarySearchTree();
		assertEquals("(null,null)", t.toString());
		
		t.insert(17);
		assertEquals("(null,17)17(17,null)", t.toString());
		
		t = new BinarySearchTree();
		t.insert(18);
		assertEquals("(null,18)18(18,null)", t.toString());
		Points += 2;
	}

	// -----------------------------------------------------------------------

	@Test
	public void testSmall() {
		BinarySearchTree t = new BinarySearchTree();
		assertEquals("(null,null)", t.toString());
		t.insert(10);
		assertEquals("(null,10)10(10,null)", t.toString());
		t.insert(15);
		// At this point, both the tree rooted at 15 (just a leaf) 
		// and the whole tree have odd sums.
		assertEquals("(null,10)10(10,15)15(15,null)", t.toString());
		t.insert(8);
		assertEquals("(null,8)8(8,10)10(10,15)15(15,null)", t.toString());
		t.insert(21);
		// Now, both of the previous trees have even sums, 
		// but the leaf at 21 has an odd sum.
		assertEquals("(null,8)8(8,10)10(10,15)15(15,21)21(21,null)", t.toString());
		Points += 4;
	}
	
	// -----------------------------------------------------------------------

	@Test
	public void testExampleFromSpecsAsTreeBuiltUsingToString() {
		BinarySearchTree t = new BinarySearchTree();
		t.insert(25);
		assertEquals("(null,25)25(25,null)", t.toString());
		
		// Add level 2 to tree
		t.insert(12);
		assertEquals("(null,12)12(12,25)25(25,null)", t.toString());
		t.insert(39);
		assertEquals("(null,12)12(12,25)25(25,39)39(39,null)", t.toString());
		Points += 2;

		// Add level 3 to tree
		t.insert(8);
		assertEquals("(null,8)8(8,12)12(12,25)25(25,39)39(39,null)", t.toString());
		t.insert(16);		
		assertEquals("(null,8)8(8,12)12(12,16)16(16,25)25(25,39)39(39,null)", t.toString());
		t.insert(31);
		assertEquals("(null,8)8(8,12)12(12,16)16(16,25)25(25,31)31(31,39)39(39,null)", t.toString());
		t.insert(41);
		assertEquals("(null,8)8(8,12)12(12,16)16(16,25)25(25,31)31(31,39)39(39,41)41(41,null)", t.toString());
		Points += 2;
		
		// Add most of level 4 to tree
		t.insert(5);
		assertEquals("(null,5)5(5,8)8(8,12)12(12,16)16(16,25)25(25,31)31(31,39)39(39,41)41(41,null)", t.toString());
		Points += 2;
		t.insert(10);
		assertEquals("(null,5)5(5,8)8(8,10)10(10,12)12(12,16)16(16,25)25(25,31)31(31,39)39(39,41)41(41,null)", t.toString());
		t.insert(13);
		assertEquals("(null,5)5(5,8)8(8,10)10(10,12)12(12,13)13(13,16)16(16,25)25(25,31)31(31,39)39(39,41)41(41,null)", t.toString());
		t.insert(18);
		assertEquals("(null,5)5(5,8)8(8,10)10(10,12)12(12,13)13(13,16)16(16,18)18(18,25)25(25,31)31(31,39)39(39,41)41(41,null)", t.toString());
		t.insert(35);
		assertEquals("(null,5)5(5,8)8(8,10)10(10,12)12(12,13)13(13,16)16(16,18)18(18,25)25(25,31)31(31,35)35(35,39)39(39,41)41(41,null)", t.toString());
		Points += 2;
		
		// Add a couple nodes to level 5 of tree
		t.insert(33);
		assertEquals("(null,5)5(5,8)8(8,10)10(10,12)12(12,13)13(13,16)16(16,18)18(18,25)25(25,31)31(31,33)33(33,35)35(35,39)39(39,41)41(41,null)", t.toString());		
		Points += 2;
		t.insert(37);
		assertEquals("(null,5)5(5,8)8(8,10)10(10,12)12(12,13)13(13,16)16(16,18)18(18,25)25(25,31)31(31,33)33(33,35)35(35,37)37(37,39)39(39,41)41(41,null)", t.toString());
		Points += 2;
	}
	
	// -----------------------------------------------------------------------

	@AfterClass
	public static void displayPoints() {
		System.out.println("----------------------------------------------------------------------");
		System.out.printf("1. %2d/20  EBT insert points                              \n", Points);
		System.out.println("----------------------------------------------------------------------");
	}
}
