package projectoEntrega1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Graph {
	int dim;
	ArrayList<ArrayList<Integer>> adj;

	public Graph(int dim) {
		this.dim = dim;
		this.adj = new ArrayList<ArrayList<Integer>>();
		for (int i = 0; i < dim; i++) {
			this.adj.add(new ArrayList<Integer>());
		}
	}

	public int getDim() {
		return this.dim;
	}

	@Override
	public String toString() {
		return "Graph [dim=" + dim + ", adj=" + adj + "]";
	}

	public boolean edgeQ(int o, int d) {
		if (o >= 0 && o < this.dim && d >= 0 && d < this.dim) {
			return this.adj.get(o).contains(d);
		} else {
			throw new AssertionError("Vertice not in graph");
		}
	}

	public void addEdge(int o, int d) {
		if (o >= 0 && o < this.dim && d >= 0 && d < this.dim) {
			if (!this.edgeQ(o, d)) {
				this.adj.get(o).add(d);
			}
		} else {
			throw new AssertionError("Vertice not in graph");
		}
	}

	public void removeEdge(int o, int d) {
		if (o >= 0 && o < this.dim && d >= 0 && d < this.dim) {
			this.adj.get(o).remove((Integer) d); // remove the object (node d) (Integer d)
		} else {
			throw new AssertionError("Vertice not in graph");
		}
	}

	public ArrayList<Integer> offspring(int o) {
		if (o >= 0 && o < this.dim) {
			return adj.get(o);
		} else {
			throw new AssertionError("Vertice not in graph");
		}
	}

}
