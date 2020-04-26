package hash;
import static org.junit.Assert.*;
import org.junit.*;

// Test class
public class StringHashSetTest {

	private static int sPoints = 0;


	@Test
	public void testLoadFactor() {
		StringHashSet set = new StringHashSet();
		String[] toAdd = {"one","two","three","four","five","six","seven",
				"eight","nine","ten","eleven","twelve","thirteen","fourteen",
				"fifteen","sixteen"};
		for (String item : toAdd) {
			set.add(item);
		}
			//		0: fourteen 
			//		1: twelve eleven 
			//		2: nine five six 
			//		3: 
			//		4: sixteen two 
			//		5: fifteen ten seven 
			//		6: one three four 
			//		7: thirteen eight 
		assertEquals(2,set.loadFactor(),0.01);
		set = new StringHashSet();
		for (int i = 0; i < 96; i++) {
			set.add(Integer.toString(201*i));
		}
		// Comment out this next line if you'd like to see the hash table
		// System.out.println(set.toRawString());
		assertEquals(1.5,set.loadFactor(),0.01);
		sPoints += 5;
	}
	
	
	@Test
	public void testAverageLengthOfNonemptyChains() {
		StringHashSet set = new StringHashSet();
		String[] toAdd = {"one","two","three","four","five","six","seven",
				"eight","nine","ten","eleven","twelve","thirteen","fourteen",
				"fifteen","sixteen"};
		for (String item : toAdd) {
			set.add(item);
		}
			//		0: fourteen 
			//		1: twelve eleven 
			//		2: nine five six 
			//		3: 
			//		4: sixteen two 
			//		5: fifteen ten seven 
			//		6: one three four 
			//		7: thirteen eight 
		assertEquals(2.285,set.averageLengthOfNonemptyChains(),0.01);
		set = new StringHashSet();
		for (int i = 0; i < 96; i++) {
			set.add(Integer.toString(201*i));
		}
		// Comment out this next line if you'd like to see the hash table
		// System.out.println(set.toRawString());
		assertEquals(2.043,set.averageLengthOfNonemptyChains(),0.01);
		sPoints += 5;
	}
	
	
	@Test
	public void testWorstCaseNumComparisonsInSearch() {
		StringHashSet set = new StringHashSet();
		String[] toAdd = {"one","two","three","four","five","six","seven",
				"eight","nine","ten","eleven","twelve","thirteen","fourteen",
				"fifteen","sixteen"};
		for (String item : toAdd) {
			set.add(item);
		}
			//		0: fourteen 
			//		1: twelve eleven 
			//		2: nine five six 
			//		3: 
			//		4: sixteen two 
			//		5: fifteen ten seven 
			//		6: one three four 
			//		7: thirteen eight 
		assertEquals(3,set.worstCaseNumComparisonsInSearch());
		set = new StringHashSet();
		for (int i = 0; i < 96; i++) {
			set.add(Integer.toString(201*i));
		}
		// Comment out this next line if you'd like to see the hash table
		// System.out.println(set.toRawString());
		assertEquals(4,set.worstCaseNumComparisonsInSearch());
		sPoints += 5;
	}
	

	@AfterClass
	public static void displayPoints() {
		System.out.printf("***   StringHashSetProperties unit test points:          %2d / 15\n", sPoints);
	}
	

	
}
