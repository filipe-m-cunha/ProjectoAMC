package projectoEntrega1;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.jupiter.api.Test;


class GeneralTest {
	
	private Dataset df1;
	private Dataset df2;
	private Graph g1;
	private MRFT m1;
	private MRFT m2;
	
	@Before
	void init() {
		this.df1 = new Dataset(3);
		this.df2 = new Dataset(8);
		this.g1 = new Graph(8);
		int[][] edges = {{0,4}, {0,6}, {1,2}, {2,1}, {2,3}, {2,6}, {2,7}, {3,5}, {4,3}, {4,5}, {5,3}, {6,3}, {6,5}, {7,2}, {7,5}};
		for(int[] e : edges) {
			g1.addEdge(e[0], e[1]);
		}
	}
	

	@Test
	void testAddDatasetTest() throws Exception {
		this.df1 = new Dataset(3);
		this.df2 = new Dataset(8);
		this.df1.Add(new int[] {1, 2, 3});
		this.df1.Add(new int[] {2, 3, 5});
		this.df1.Add(new int[] {2, 3, 6});
		this.df1.Add(new int[] {7, 8, 10});
		assertEquals(this.df1.values.size(), 4);
		this.df2.Add(new int[] {1, 2, 3, 4, 5, 6, 7, 8});
		assertEquals(this.df2.values.size(), 1);
	}
	
	@Test
	void countTest() throws Exception {
		this.df1 = new Dataset(3);
		this.df2 = new Dataset(8);
		this.df1.Add(new int[] {1, 2, 3});
		this.df1.Add(new int[] {2, 3, 5});
		this.df1.Add(new int[] {2, 3, 6});
		this.df1.Add(new int[] {7, 8, 10});
		this.df2.Add(new int[] {1, 2, 3, 4, 5, 6, 7, 8});
		this.df2.Add(new int[] {1, 2, 3, 4, 5, 6, 7, 8});
		this.df2.Add(new int[] {1, 2, 3, 4, 5, 6, 7, 8});
		this.df2.Add(new int[] {1, 2, 3, 4, 5, 6, 7, 8});
		ArrayList<Integer> testI1 = new ArrayList<Integer>();
		ArrayList<Integer> testV1 = new ArrayList<Integer>();
		testI1.add(0);
		testI1.add(1);
		testV1.add(2);
		testV1.add(3);
		assertEquals(this.df1.Count(testI1, testV1), 2);
		assertEquals(this.df2.Count(testI1, testV1), 0);
		ArrayList<Integer> testI2 = new ArrayList<Integer>();
		ArrayList<Integer> testV2 = new ArrayList<Integer>();
		testI2.add(2);
		testV2.add(3);
		assertEquals(this.df1.Count(testI2, testV2), 1);
		assertEquals(this.df2.Count(testI2, testV2), 4);
		ArrayList<Integer> testI3 = new ArrayList<Integer>();
		ArrayList<Integer> testV3 = new ArrayList<Integer>();
		testI3.add(0);
		testI3.add(1);
		testI3.add(2);
		testI3.add(3);
		testI3.add(4);
		testI3.add(5);
		testI3.add(6);
		testI3.add(7);
		testV3.add(1);
		testV3.add(2);
		testV3.add(3);
		testV3.add(4);
		testV3.add(5);
		testV3.add(6);
		testV3.add(7);
		testV3.add(8);
		assertEquals(this.df2.Count(testI3, testV3), 4);
	}
	
	@Test
	void testMRFT() throws Exception {
		this.df2 = new Dataset(8);
		this.df2.Add(new int[] {1, 2, 3, 4, 5, 6, 7, 8});
		this.df2.Add(new int[] {1, 2, 3, 4, 5, 6, 7, 8});
		this.df2.Add(new int[] {1, 2, 3, 4, 5, 6, 7, 8});
		this.df2.Add(new int[] {1, 2, 3, 4, 5, 6, 7, 8});
		System.out.println(Arrays.toString(this.df2.domain));
		this.g1 = new Graph(8);
		int[][] edges = {{0,4}, {0,6}, {1,2}, {2,1}, {2,3}, {2,6}, {2,7}, {3,5}, {4,3}, {4,5}, {5,3}, {6,3}, {6,5}, {7,2}, {7,5}};
		for(int[] e : edges) {
			g1.addEdge(e[0], e[1]);
		}
		this.m1 = new MRFT(df2, g1, 0, 0.2);
		ArrayList<Integer> testV3 = new ArrayList<Integer>();
		testV3.add(1);
		testV3.add(2);
		testV3.add(3);
		testV3.add(4);
		testV3.add(5);
		testV3.add(6);
		testV3.add(7);
		testV3.add(8);
		System.out.println(m1.probability(testV3));
	}

}
