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
				for(int k = 0; k<this.dataset.domain[lisEdges.get(i)[1]]; k++) {
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
		return (this.dataset.Count(temp1I, temp1V) + this.delta)/(this.dataset.Count(temp2I, temp2V) + this.delta);
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
			prob = prob*(this.phis.get(i)[vector.get(this.lisEdges.get(i)[0])-1][vector.get(this.lisEdges.get(i)[1])-1]);
		}
		
		return prob;
	}
	
}
