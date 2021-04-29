package projectoEntrega1;

import java.util.ArrayList;
import java.util.Arrays;

public class MRFT {
	
	Dataset dataset;
	WeightedGraph wGraph;
	int oSpec;
	int dSpec;
	
	
	public double Phi(int i, int j) {
		
		//Muito provavelmente n�o � isto, tenho que confirmar com o prof
		
		double phi = 0;
		
		for(double k1:dataset.distinctValues.get(i)) {
			for(double k2:dataset.distinctValues.get(i)) {
				phi += dataset.Count(new ArrayList<>(Arrays.asList(i, j)), new ArrayList<>(Arrays.asList(k1, k2)))/dataset.Count(new ArrayList<>(Arrays.asList(i)), new ArrayList<>(Arrays.asList(k1)));
			}
		}
		return phi;
	}
	
	public MRFT(Dataset dataset, int oSpec, int dSpec) {
		
		this.dataset = dataset;
		this.oSpec = oSpec;
		this.dSpec = dSpec;
		
		this.wGraph = new WeightedGraph(this.dataset.dim);
		for(int i=0; i<this.dataset.dim; i++) {
			for(int j=0; j<this.dataset.dim; j++) {
				this.wGraph.m[i][j] = Phi(i,j);
			}
		}
		
	}

	public double Prob(ArrayList<Integer> vector) {
		double prob = 1;
		for (int i=0; i<vector.size(); i++) {
			for(int j=i+1; j<vector.size(); j++) {
				//Ainda n�o percebi exatamente o que s�o os x_i, x_j (TODO)
				prob = prob*this.wGraph.m[vector.get(i)][vector.get(j)];
			}
		}
		return prob;
	}
}