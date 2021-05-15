package projectoEntrega1.Models;

import java.util.ArrayList;

import projectoEntrega1.Exceptions.InvalidSizeException;

public class ChowLiu {

	private Dataset data;
	private WeightedGraph graph;
	
	public Dataset getData() {
		return data;
	}

	public void setData(Dataset data) {
		this.data = data;
	}

	public WeightedGraph getGraph() {
		return graph;
	}

	public void setGraph(WeightedGraph graph) {
		this.graph = graph;
	}
	
	public ChowLiu(Dataset data) throws InvalidSizeException {
		this.data = data;
		this.graph = new WeightedGraph(data.getDim());
		for(int i = 0; i<data.getDim(); i++) {
			for(int j = i+1; j<data.getDim(); j++) {
				this.graph.addEdge(i, j, mutualInformation(i, j, this.data));
			}
		}
		ArrayList<WeightedEdge> mst = new ArrayList<WeightedEdge>();
		this.graph.MST(mst);
		this.graph.setEdgeList(mst);
		/*for(int i = 0; i<mst.size(); i++) {
			System.out.println(mst.get(i).d +  ' ' + mst.get(i).d + ' ' + mst.get(i).w );
		}*/
	}
	
	public double mutualInformation(int i, int j, Dataset data) {
		double res = 0;
		for(int k1 = 0; k1<=data.getDomain()[i]; k1++) {
			for(int k2 = 0; k2<=data.getDomain()[j]; k2++) {
				double val = probability(i, j, k1, k2, data);
				if(val!=0) {
					res += val*Math.log(val/(probability(i, k1, data)*probability(j, k2, data)));
				}
			}
		}
		return res;
	}
	
	public double probability(int index, int val, Dataset data) {
		return data.count(new int[] {index}, new int[] {val})/data.getValues().size();
	}
	
	public double probability(int index1, int index2, int val1, int val2, Dataset data) {
		return data.count(new int[] {index1, index2}, new int[] {val1, val2})/data.getValues().size();
	}
}
