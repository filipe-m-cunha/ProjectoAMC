package projectoEntrega1;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Before;
import org.junit.jupiter.api.Test;

import projectoEntrega1.Exceptions.InvalidDomainException;
import projectoEntrega1.Exceptions.InvalidSizeException;
import projectoEntrega1.Models.Dataset;
import projectoEntrega1.Models.Graph;
import projectoEntrega1.Models.MRFT;


class GeneralTest {
	
	private Dataset df1;
	private Dataset df2;
	private Dataset df3;
	private Graph g1;
	private MRFT m1;
	
	@Before
	void init() {
		this.df1 = new Dataset();
		this.df2 = new Dataset();
		this.g1 = new Graph(8);
		int[][] edges = {{0,4}, {0,6}, {1,2}, {2,1}, {2,3}, {2,6}, {2,7}, {3,5}, {4,3}, {4,5}, {5,3}, {6,3}, {6,5}, {7,2}, {7,5}};
		for(int[] e : edges) {
			g1.addEdge(e[0], e[1]);
		}
	}
	

	@Test
	void testAddDatasetTest() throws Exception {
		this.df1 = new Dataset();
		this.df2 = new Dataset();
		this.df1.add(new int[] {1, 2, 3});
		this.df1.add(new int[] {2, 3, 5});
		this.df1.add(new int[] {2, 3, 6});
		this.df1.add(new int[] {7, 8, 10});
		assertEquals(this.df1.getValues().size(), 4);
		this.df2.add(new int[] {1, 2, 3, 4, 5, 6, 7, 8});
		assertEquals(this.df2.getValues().size(), 1);
	}
	
	@Test
	void countTest() throws Exception {
		this.df1 = new Dataset();
		this.df2 = new Dataset();
		this.df1.add(new int[] {1, 2, 3});
		this.df1.add(new int[] {2, 3, 5});
		this.df1.add(new int[] {2, 3, 6});
		this.df1.add(new int[] {7, 8, 10});
		this.df2.add(new int[] {1, 2, 3, 4, 5, 6, 7, 8});
		this.df2.add(new int[] {1, 2, 3, 4, 5, 6, 7, 8});
		this.df2.add(new int[] {1, 2, 3, 4, 5, 6, 7, 8});
		this.df2.add(new int[] {1, 2, 3, 4, 5, 6, 7, 8});
		int[] testI1 = {0,1};
		int[] testV1 = {2,3};
		assertEquals(this.df1.count(testI1, testV1), 2);
		assertEquals(this.df2.count(testI1, testV1), 0);
		int[] testI2 = {2};
		int[] testV2 = {3};
		assertEquals(this.df1.count(testI2, testV2), 1);
		assertEquals(this.df2.count(testI2, testV2), 4);
		int[] testI3 = {0,1,2,3,4,5,6,7};
		int[] testV3 = {1,2,3,4,5,6,7,8};
		assertEquals(this.df2.count(testI3, testV3), 4);
	}
	
	@Test
	void fiberTest() throws Exception {
		this.df1 = new Dataset();
		this.df2 = new Dataset();
		this.df3 = new Dataset();
		this.df1.add(new int[] {1, 2, 0});
		this.df1.add(new int[] {2, 3, 1});
		this.df1.add(new int[] {2, 3, 0});
		this.df1.add(new int[] {7, 8, 1});
		this.df2.add(new int[] {2, 3, 1});
		this.df2.add(new int[] {7, 8, 1});
		this.df3.add(new int[] {1, 2, 0});
		this.df3.add(new int[] {2, 3, 0});
		assertEquals(this.df1.fiber(0).getValues().get(0)[0], this.df3.getValues().get(0)[0]);
		assertEquals(this.df1.fiber(1).getValues().get(0)[0], this.df2.getValues().get(0)[0]);
		assertEquals(this.df1.fiber(0).getDomain()[0], this.df3.getDomain()[0]);
		assertEquals(this.df1.fiber(0).getDim(), this.df3.getDim());
	}
	
	@Test
	void testMRFT() throws Exception {
		this.df2 = new Dataset();
		this.df2.add(new int[] {1, 2, 3, 4, 5, 6, 7, 8});
		this.df2.add(new int[] {1, 2, 3, 4, 5, 6, 7, 8});
		this.df2.add(new int[] {1, 2, 3, 4, 5, 6, 7, 8});
		this.df2.add(new int[] {1, 2, 3, 4, 5, 6, 7, 8});
		this.g1 = new Graph(8);
		int[][] edges = {{0,1}, {1,0}, {0,2}, {2,0}, {0,3}, {3,0}, {0,4}, {4,0}, {0,5}, {5,0}, {0,7}, {7,0}, {0,6}, {6,0}};
		for(int[] e : edges) {
			g1.addEdge(e[0], e[1]);
		}
		this.m1 = new MRFT(df2, g1, 0, 0.2);
		int[] testV3 = new int[] {1,2,3,4,5,6,7,8};
		int[] testV4 = new int[] {1,1,1,1,1,1,1,1};
		int[] testV5 = new int[] {1,2,2,2,2,2,2,2};
		System.out.println(this.m1.probability(testV3));
		System.out.println(this.m1.probability(testV4));
		System.out.println(this.m1.probability(testV5));
		System.out.println(this.m1.getGraph());
	}
	
	@Test
	void testErrorsMRFT() throws Exception {
		this.df2 = new Dataset();
		this.df2.add(new int[] {1, 2, 3, 4, 5, 6, 7, 8});
		this.df2.add(new int[] {1, 2, 3, 4, 5, 6, 7, 8});
		this.df2.add(new int[] {1, 2, 3, 4, 5, 6, 7, 8});
		this.df2.add(new int[] {1, 2, 3, 4, 5, 6, 7, 8});
		this.g1 = new Graph(8);
		int[][] edges = {{0,1}, {1,0}, {0,2}, {2,0}, {0,3}, {3,0}, {0,4}, {4,0}, {0,5}, {5,0}, {0,7}, {7,0}, {0,6}, {6,0}};
		for(int[] e : edges) {
			g1.addEdge(e[0], e[1]);
		}
		this.m1 = new MRFT(df2, g1, 0, 0.2);
		int[] testV6 = new int[] {2,2,2,2,2,2,2,2};
		int[] testV7 = new int[] {1};
		assertThrows(InvalidSizeException.class, () -> {this.m1.probability(testV7);});
		assertThrows(InvalidDomainException.class, () -> {this.m1.probability(testV6);});
	}

}
