package projectoEntrega1;

import java.util.ArrayList;
import java.util.Stack;

public class MRFT {
	
	Dataset dataset;
	Graph graph;
	int oSpec;
	double delta;
	ArrayList<int[]> lisEdges;
	ArrayList<int[][]> phis;
	
	public MRFT(Dataset dataset, Graph tree) {
		
		this.dataset = dataset;
		this.graph = new Graph(tree.dim);
		this.lisEdges = new ArrayList<int[]>();
		
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
		
		this.phis = new ArrayList<int[][]>();
		for(int i = 0; i<this.lisEdges.size(); i++) {
			
			this.phis.add(new int[this.dataset.domain[lisEdges.get(i)[0]]][this.dataset.domain[lisEdges.get(i)[1]]]);
			
			for(int j = 0; j<this.dataset.domain[lisEdges.get(i)[0]]; j++) {
				for(int k = 0; j<this.dataset.domain[lisEdges.get(i)[1]]; k++) {
					int [][] phi = this.phis.get(i);
					ArrayList<Integer> tempI = new ArrayList<Integer>();
					ArrayList<Integer> tempV = new ArrayList<Integer>();
					tempI.add(lisEdges.get(i)[0]);
					tempI.add(lisEdges.get(i)[1]);
					tempV.add(j);
					tempV.add(k);
					phi[j][k] = dataset.Count(tempI, tempV);
					this.phis.set(i, phi);
				}
			}
		}
	}
	
}
