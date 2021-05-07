package projectoEntrega1.Models;

import java.util.ArrayList;
import java.util.List;

import projectoEntrega1.Exceptions.InvalidSizeException;

public class Graph {
	private int dim;
	private List<ArrayList<Integer>> adj;
	

	public int getDim() {
		return dim;
	}

	public void setDim(int dim) {
		this.dim = dim;
	}

	public List<ArrayList<Integer>> getAdj() {
		return adj;
	}

	public void setAdj(List<ArrayList<Integer>> adj) {
		this.adj = adj;
	}

	public Graph(int dim) {
		this.dim = dim;
		this.adj = new ArrayList<ArrayList<Integer>>();
		for (int i = 0; i < dim; i++) {
			this.adj.add(new ArrayList<Integer>());
		}
	}

	public boolean edgeQ(int o, int d) throws InvalidSizeException {
		if (o >= 0 && o < this.dim && d >= 0 && d < this.dim) {
			return this.adj.get(o).contains(d);
		} else {
			throw new InvalidSizeException("Vertice not in graph");
		}
	}

	public void addEdge(int o, int d) throws InvalidSizeException {
		if (o >= 0 && o < this.dim && d >= 0 && d < this.dim) {
			if (!this.edgeQ(o, d)) {
				this.adj.get(o).add(d);
			}
		} else {
			throw new InvalidSizeException("Vertice not in graph");
		}
	}

	public void removeEdge(int o, int d) throws InvalidSizeException {
		if (o >= 0 && o < this.dim && d >= 0 && d < this.dim) {
			this.adj.get(o).remove((Integer) d); // remove the object (node d) (Integer d)
		} else {
			throw new InvalidSizeException("Vertice not in graph");
		}
	}

	public ArrayList<Integer> offspring(int o) throws InvalidSizeException {
		if (o >= 0 && o < this.dim) {
			return adj.get(o);
		} else {
			throw new InvalidSizeException("Vertice not in graph");
		}
	}

}
