package projectoEntrega1;

import java.util.ArrayList;

public class MRFT {
	
	Dataset dataset;
	WeightedGraph wGraph;
	int oSpec;
	int dSpec;
	
	
	public MRFT(Dataset dataset, int oSpec, int dSpec) {
		
		this.dataset = dataset;
		this.oSpec = oSpec;
		this.dSpec = dSpec;
		
		this.wGraph = new WeightedGraph(this.dataset.totalSize());
		//TODO: Perceber exatamente o que é dito por x_i, x_j
		
	}

	public double Prob(ArrayList<Integer> vector) {
		double prob = 0;
		for (int i=0; i<vector.size(); i++) {
			for(int j=i+1; j<vector.size(); j++) {
				//Ainda não percebi exatamente o que são os x_i, x_j (TODO)
				prob = prob*this.wGraph.m[vector.get(i)][vector.get(j)];
			}
		}
		return prob;
	}
}
