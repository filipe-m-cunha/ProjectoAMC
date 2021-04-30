package projectoEntrega1;

import java.util.ArrayList;
import java.util.Stack;

public class MRFT {
	
	Dataset dataset;
	Graph graph;
	int oSpec;
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
		while(! stack.isEmpty()) {
			int node = stack.pop();
			if(!visited[node]) {
				visited[node] = true;
				for(int i:tree.offspring(node)) {
					stack.push(i);
					this.graph.addEdge(node, i);
					this.lisEdges.add(new int[] {node, i});
				}
			}
		}
		
		this.phis = new ArrayList<double[][]>();
		for(int i = 0; i<this.lisEdges.size(); i++) {
			
			this.phis.add(new double[this.dataset.domain[lisEdges.get(i)[0]]][this.dataset.domain[lisEdges.get(i)[1]]]);
			
			for(int j = 0; j<this.dataset.domain[lisEdges.get(i)[0]]; j++) {
				ArrayList<Integer> temp1 = new ArrayList<Integer>();
				ArrayList<Integer> temp2 = new ArrayList<Integer>();
				temp1.add(lisEdges.get(i)[0]);
				temp2.add(j);
				for(int k = 0; j<this.dataset.domain[lisEdges.get(i)[1]]; k++) {
					double [][] phi = this.phis.get(i);
					ArrayList<Integer> tempI = new ArrayList<Integer>();
					ArrayList<Integer> tempV = new ArrayList<Integer>();
					tempI.add(lisEdges.get(i)[0]);
					tempI.add(lisEdges.get(i)[1]);
					tempV.add(j);
					tempV.add(k);
					phi[j][k] = (dataset.Count(tempI, tempV) + this.delta)/dataset.Count(temp1, temp2);
					this.phis.set(i, phi);
				}
			}
		}
	}

	public Dataset getDataset() {
		return dataset;
	}

	public void setDataset(Dataset dataset) {
		this.dataset = dataset;
	}

	public Graph getGraph() {
		return graph;
	}

	public void setGraph(Graph graph) {
		this.graph = graph;
	}

	public int getoSpec() {
		return oSpec;
	}

	public void setoSpec(int oSpec) {
		this.oSpec = oSpec;
	}

	public double getDelta() {
		return delta;
	}

	public void setDelta(double delta) {
		this.delta = delta;
	}

	public ArrayList<int[]> getLisEdges() {
		return lisEdges;
	}

	public void setLisEdges(ArrayList<int[]> lisEdges) {
		this.lisEdges = lisEdges;
	}

	public ArrayList<double[][]> getPhis() {
		return phis;
	}

	public void setPhis(ArrayList<double[][]> phis) {
		this.phis = phis;
	}
	
	
	public double probability(ArrayList<Integer> vector) {
		double prob = 1;
		
		for(int i = 0; i<this.lisEdges.size(); i++) {
			prob = prob*(this.phis.get(i)[vector.get(this.lisEdges.get(i)[0])][vector.get(this.lisEdges.get(i)[1])]);
		}
		
		return prob;
	}
	
}
