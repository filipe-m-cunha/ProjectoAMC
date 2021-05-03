package projectoEntrega1;

import java.util.ArrayList;
import java.util.Stack;

public class MRFT {
	
	Dataset dataset;
	Graph graph;
	int oSpec;
	int dSpec;
	double delta;
	ArrayList<int[]> lisEdges;
	ArrayList<double[][]> phis;
	
	public MRFT(Dataset dataset, Graph tree, int oSpec, double delta) {
		
		this.dataset = dataset;
		this.graph = new Graph(tree.dim);
		this.lisEdges = new ArrayList<int[]>();
		this.oSpec = oSpec;
		this.delta = delta;
		
		Stack<Integer> stack = new Stack<Integer>();
		boolean[] visited = new boolean[tree.dim];
		stack.push(oSpec);
		this.dSpec = tree.offspring(oSpec).get(0);
		while(! stack.isEmpty()) {
			int node = stack.pop();
			if(!visited[node]) {
				visited[node] = true;
				for(int i:tree.offspring(node)) {
					stack.push(i);
					if(!visited[i]) {
						this.graph.addEdge(node, i);
						this.lisEdges.add(new int[] {node, i});}
				}
			}
		}
		
		this.phis = new ArrayList<double[][]>();
		for(int i = 0; i<this.lisEdges.size(); i++) {
			
			this.phis.add(new double[this.dataset.domain[lisEdges.get(i)[0]]+1][this.dataset.domain[lisEdges.get(i)[1]]+1]);
			
			for(int j = 0; j<=this.dataset.domain[lisEdges.get(i)[0]]; j++) {
				for(int k = 0; k<=this.dataset.domain[lisEdges.get(i)[1]]; k++) {
					double [][] phi = this.phis.get(i);
					phi[j][k] = this.calcPhi(this.lisEdges.get(i)[0], this.lisEdges.get(i)[1], j, k);
					this.phis.set(i, phi);
				}
			}
		}
	}

	public double calcPhi(int i1, int i2, int x1, int x2) {
		ArrayList<Integer> temp1I = new ArrayList<Integer>();
		ArrayList<Integer> temp1V = new ArrayList<Integer>();
		ArrayList<Integer> temp2I = new ArrayList<Integer>();
		ArrayList<Integer> temp2V = new ArrayList<Integer>();
		temp1I.add(i1);
		temp1I.add(i2);
		temp1V.add(x1);
		temp1V.add(x2);
		temp2I.add(i1);
		temp2V.add(x1);
		if(i1==this.oSpec && i2==this.dSpec) {
			return (this.dataset.Count(temp1I, temp1V) + this.delta)/(this.dataset.values.size()*this.dataset.dim + this.delta*this.dataset.domain[i1]);
		}
		else {
			return (this.dataset.Count(temp1I, temp1V) + this.delta)/(this.dataset.Count(temp2I, temp2V) + this.delta*this.dataset.domain[i1]);
		}
	}
	
	public double probability(ArrayList<Integer> vector) {
		double prob = 1;
		
		for(int i = 0; i<this.lisEdges.size(); i++) {
			prob = prob*(this.phis.get(i)[vector.get(this.lisEdges.get(i)[0])][vector.get(this.lisEdges.get(i)[1])]);
		}
		
		return prob;
	}
	
}
