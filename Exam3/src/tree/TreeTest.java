package tree;

import static org.junit.Assert.assertEquals;

import org.junit.AfterClass;
import org.junit.Test;

public class TreeTest {

	private static int netSizeDiffPoints = 0;
	private static int netBalancePoints = 0;
	
	
	@Test
	public void testNetSizeDiffHeightAtMost1() {
		BinarySearchTree t = new BinarySearchTree();
		assertEquals(0, t.netSizeDiff());
		t = new BinarySearchTree();
		t.insert('a');
		assertEquals(0, t.netSizeDiff());
		t = new BinarySearchTree();
		t.insert('b');
		t.insert('a');
		assertEquals(-1, t.netSizeDiff());
		t = new BinarySearchTree();
		t.insert('a');
		t.insert('b');
		assertEquals(1, t.netSizeDiff());
		t = new BinarySearchTree();
		t.insert('b');
		t.insert('a');
		t.insert('c');		
		assertEquals(0, t.netSizeDiff());
		netSizeDiffPoints += 3;
	} // testNetSizeDiffHeightAtMost1
	
	
	@Test
	public void testNetSizeDiffAlmostFullTreeAndFullTree() {
		BinarySearchTree t = new BinarySearchTree();
		t.insert('d');		//           d(+1)
		t.insert('b');		//         /   \
		t.insert('c');		//        /     \
		t.insert('f');		//      b(+1)    f
		t.insert('e');		//       \      / \
		t.insert('g');		//        c    e   g 
		assertEquals(2, t.netSizeDiff());
		t.insert('a');		//     Now full, all size diffs 0 
		assertEquals(0, t.netSizeDiff());  

		netSizeDiffPoints += 3;
	} // testNetSizeDiffAlmostFullTreeAndFullTree
	
	@Test
	public void testNetSizeDiffNonFullTreesOfHeight3() {
		BinarySearchTree t = new BinarySearchTree();
		t.insert('e');		//           e(-1)
		t.insert('b');		//         /   \
		t.insert('a');		//        /     \
		t.insert('c');		//       /       \
		t.insert('d');		//     b(+1)      h(-2)
		t.insert('h');		//   /   \       / 
		t.insert('g');		//  a     c(+1) g(-1)
		t.insert('f');		//         \   /  
                            //          d f

		// Sum = 1 + 1 - 1 - 2 - 1 = -2
		
		assertEquals(-2, t.netSizeDiff());

		t = new BinarySearchTree();
		t.insert('e');		//           e
		t.insert('b');		//         /   \
	                        //        /     \
		t.insert('c');		//       /       \
		t.insert('d');		//     b(+2)      h(-2)
		t.insert('h');		//       \       / 
		t.insert('g');		//        c(+1) g(-1)
		t.insert('f');		//         \   /  
                            //          d f

		// Sum = 2 + 1 - 1 - 2 = 0
		
		assertEquals(0, t.netSizeDiff());

		t = new BinarySearchTree();
		t.insert('e');		//           e(+3)
		t.insert('b');		//         /   \
		t.insert('a');		//        /     \
		t.insert('c');		//       /       \
		t.insert('d');		//     b(+1)       i
		t.insert('i');		//   /   \       /   \
		t.insert('g');		//  a     c(+1) g     k
		t.insert('k');      //         \   / \   / \
		t.insert('f');		//          d f   h j   l
		t.insert('h');
		t.insert('j');
		t.insert('l');
		
		// Sum = 3 + 1 + 1 = 5
		
		assertEquals(5, t.netSizeDiff());
		netSizeDiffPoints += 4;
	} // testNetSizeDiffNonFullTreesOfHeight3

	
	
	@Test
	public void testNetBalanceHeightAtMost1() {
		BinarySearchTree t = new BinarySearchTree();
		assertEquals(0, t.netBalance());
		t = new BinarySearchTree();
		t.insert('a');
		assertEquals(0, t.netBalance());
		t = new BinarySearchTree();
		t.insert('b');
		t.insert('a');
		assertEquals(-1, t.netBalance());
		t = new BinarySearchTree();
		t.insert('a');
		t.insert('b');
		assertEquals(1, t.netBalance());
		t = new BinarySearchTree();
		t.insert('b');
		t.insert('a');
		t.insert('c');		
		assertEquals(0, t.netBalance());
		netBalancePoints += 3;
	} // testNetBalanceHeightAtMost1
	
	
	@Test
	public void testNetBalanceAlmostFullTreeAndFullTree() {
		BinarySearchTree t = new BinarySearchTree();
		t.insert('d');		//           d
		t.insert('b');		//         /   \
		t.insert('c');		//        /     \
		t.insert('f');		//      b(+1)    f
		t.insert('e');		//       \      / \
		t.insert('g');		//        c    e   g 
		assertEquals(1, t.netBalance());
		t.insert('a');		//     Now full, all balances 0 
		assertEquals(0, t.netBalance());  

		netBalancePoints += 3;
	} // testNetBalanceAlmostFullTreeAndFullTree
	
	@Test
	public void testNetBalanceNonFullTreesOfHeight3() {
		BinarySearchTree t = new BinarySearchTree();
		t.insert('e');		//           e
		t.insert('b');		//         /   \
		t.insert('a');		//        /     \
		t.insert('c');		//       /       \
		t.insert('d');		//     b(+1)      h(-2)
		t.insert('h');		//   /   \       / 
		t.insert('g');		//  a     c(+1) g(-1)
		t.insert('f');		//         \   /  
                            //          d f

		// Sum = 1 + 1 - 1 - 2 = -1
		
		assertEquals(-1, t.netBalance());

		t = new BinarySearchTree();
		t.insert('e');		//           e
		t.insert('b');		//         /   \
	                        //        /     \
		t.insert('c');		//       /       \
		t.insert('d');		//     b(+2)      h(-2)
		t.insert('h');		//       \       / 
		t.insert('g');		//        c(+1) g(-1)
		t.insert('f');		//         \   /  
                            //          d f

		// Sum = 2 + 1 - 1 - 2 = 0
		
		assertEquals(0, t.netBalance());

		t = new BinarySearchTree();
		t.insert('e');		//           e
		t.insert('b');		//         /   \
		t.insert('a');		//        /     \
		t.insert('c');		//       /       \
		t.insert('d');		//     b(+1)       i
		t.insert('i');		//   /   \       /   \
		t.insert('g');		//  a     c(+1) g     k
		t.insert('k');      //         \   / \   / \
		t.insert('f');		//          d f   h j   l
		t.insert('h');
		t.insert('j');
		t.insert('l');
		 
		// Sum = 1 + 1 = 2
		
		assertEquals(2, t.netBalance());
		netBalancePoints += 4;
	} // testNetBalanceNonFullTreesOfHeight3
	
	@AfterClass
	public static void displayPoints() {
		System.out.printf("***  netSizeDiff unit test points:                       " +
				"%2d / 10\n", netSizeDiffPoints);
		System.out.printf("   The 10 additional efficiency points will be checked by the grader.\n");
		System.out.printf("***   netBalance unit test points:                       " +
							"%2d / 10\n", netBalancePoints);
		System.out.printf("   The 10 additional efficiency points will be checked by the grader.\n");
	}
}