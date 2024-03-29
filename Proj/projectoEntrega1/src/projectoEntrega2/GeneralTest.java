package projectoEntrega2;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Before;
import org.junit.jupiter.api.Test;

import projectoEntrega1.Exceptions.InvalidDomainException;
import projectoEntrega1.Exceptions.InvalidSizeException;
import projectoEntrega1.Models.Classifier;
import projectoEntrega1.Models.Dataset;
import projectoEntrega1.Models.MRFT;
import projectoEntrega1.Models.WeightedGraph;


class GeneralTest {
	
	private Dataset df1;
	private Dataset df2;
	private Dataset df3;
	private WeightedGraph g1;
	private MRFT m1;
	
	@Before
	void init() throws InvalidSizeException {
		this.df1 = new Dataset();
		this.df2 = new Dataset();
		this.g1 = new WeightedGraph(8);
		int[][] edges = {{0,4, 1}, {0,6,1}, {1,2, 1}, {2,1, 1}, {2,3, 1}, {2,6, 1}, {2,7, 1}, {3,5, 1}, {4,3, 1}, {4,5, 1}, {5,3, 1}, {6,3, 1}, {6,5, 1}, {7,2, 1}, {7,5, 1}};
		for(int[] e : edges) {
			g1.addEdge(e[0], e[1], e[2]);
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
		assertEquals(this.df1.fiber(0).getDomain()[0], 7);
		assertEquals(this.df1.fiber(0).getDim(), this.df3.getDim()-1);
	}
	
	@Test
	void testMRFT() throws Exception {
		this.df2 = new Dataset();
		this.df2.add(new int[] {1, 2, 3, 4, 5, 6, 7, 8});
		this.df2.add(new int[] {1, 2, 3, 4, 5, 6, 7, 8});
		this.df2.add(new int[] {1, 2, 3, 4, 5, 6, 7, 8});
		this.df2.add(new int[] {1, 2, 3, 4, 5, 6, 7, 8});
		//System.out.println(this.df2.getDim());
		this.g1 = new WeightedGraph(8);
		int[][] edges = {{0,4, 1}, {0,6,1}, {1,2, 1}, {2,1, 1}, {2,3, 1}, {2,6, 1}, {2,7, 1}, {3,5, 1}, {4,3, 1}, {4,5, 1}, {5,3, 1}, {6,3, 1}, {6,5, 1}, {7,2, 1}, {7,5, 1}};
		for(int[] e : edges) {
			g1.addEdge(e[0], e[1], e[2]);
		}
		this.m1 = new MRFT(df2, g1, 0, 0.2);
		int[] testV3 = new int[] {1,2,3,4,5,6,7, 8};
		int[] testV4 = new int[] {1,1,1,1,1,1,1, 1};
		int[] testV5 = new int[] {1,2,2,2,2,2,2, 2};
	}
	
	@Test
	void testErrorsMRFT() throws Exception {
		this.df2 = new Dataset();
		this.df2.add(new int[] {1, 2, 3, 4, 5, 6, 7, 8});
		this.df2.add(new int[] {1, 2, 3, 4, 5, 6, 7, 8});
		this.df2.add(new int[] {1, 2, 3, 4, 5, 6, 7, 8});
		this.df2.add(new int[] {1, 2, 3, 4, 5, 6, 7, 8});
		this.g1 = new WeightedGraph(8);
		int[][] edges = {{0,4, 1}, {0,6,1}, {1,2, 1}, {2,1, 1}, {2,3, 1}, {2,6, 1}, {2,7, 1}, {3,5, 1}, {4,3, 1}, {4,5, 1}, {5,3, 1}, {6,3, 1}, {6,5, 1}, {7,2, 1}, {7,5, 1}};
		for(int[] e : edges) {
			g1.addEdge(e[0], e[1], e[2]);
		}
		this.m1 = new MRFT(df2, g1, 0, 0.2);
		int[] testV6 = new int[] {2,2,2,2,2,2,2};
		int[] testV7 = new int[] {1};
		assertThrows(InvalidSizeException.class, () -> {this.m1.prob(df2.getDomain(), testV7);});
		assertThrows(InvalidDomainException.class, () -> {this.m1.prob(df2.getDomain(), testV6);});
	}

	
	@Test
	void testActualData() throws Exception {
		Dataset data = new Dataset("////data////bcancer.csv");
		//for(int i=0; i<1; i++) {
			//double[] deltas = new double[] {0, 0.000001, 0.00005, 0.00001, 0.0005, 0.001, 0.005, 0.01, 0.05, 0.1, 0.2, 0.3, 0.5, 0.75, 1, 2, 3, 4, 5, 10};
			//for(double j:deltas) {
			Classifier classifier = new Classifier(data, 0.2, true);
			System.out.println(classifier.getFrequence()[0]);
			System.out.println(classifier.getFrequence()[1]);
			classifier.getAccuracyBin(data);
			//}
		//}
	}
	
	
	@Test
	void testProfData() throws Exception{
		WeightedGraph t = new WeightedGraph(10);
		t.addEdge(0, 1,1);
		t.addEdge(0, 2,1);
		t.addEdge(0, 3,1);
		t.addEdge(1, 4,1);
		t.addEdge(1, 5,1);
		t.addEdge(2, 6,1);
		t.addEdge(2, 7,1);
		t.addEdge(3, 8,1);
		t.addEdge(3, 9,1);
		Dataset data = new Dataset("////data////bcancer.csv");
		int[] vv= {1, 0, 2, 3, 2, 0, 1, 1, 1, 1};
		MRFT mrft = new MRFT(data.datasetInicialization().get(1), t, 0, 0.2);
		mrft.setdSpec(1);
		System.out.println(mrft.prob(data.getDomain(), vv));
	}
}
