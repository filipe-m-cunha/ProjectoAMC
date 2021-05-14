package projectoEntrega1.Models;

import java.util.ArrayList;

import projectoEntrega1.Exceptions.InvalidSizeException;

public class WeightedGraph extends Graph{
	ArrayList<ArrayList<WeightedEdge>> adj;

	public WeightedGraph(int dim) {
		
		super(dim);
		this.adj = new ArrayList<ArrayList<WeightedEdge>>();
		for (int i = 0; i < this.getDim(); i++) {
			this.adj.add(new ArrayList<WeightedEdge>());
		}
	}

	public boolean edgeQ(int o, int d) throws InvalidSizeException {
		if (o >= 0 && o < this.getDim() && d >= 0 && d < this.getDim()) {
			for (WeightedEdge e : this.adj.get(o)) {
				if (e.d == d) {
					return true;
				}
			}
			return false;
		} else {
			throw new InvalidSizeException("Vertice not in graph");
		}
	}

	public void addEdge(int o, int d, int w) throws InvalidSizeException {
		if (!this.edgeQ(o, d)) {
			this.adj.get(o).add(new WeightedEdge(o, d, w));
			//Se o grafo for direcionado, comentar a linha abaixo.
			this.adj.get(d).add(new WeightedEdge(d, o, w));
		}
	}


	public int getWeight(int o, int d) throws InvalidSizeException {
		if (this.edgeQ(o, d)) {
			for (WeightedEdge e : this.adj.get(o)) {
				if (e.d == d) {
					return e.w;
				}
			}
		}
		return 0;
	}

	public ArrayList<Integer> offspring(int o) throws InvalidSizeException{
		if (o >= 0 && o < this.getDim()) {
			ArrayList<Integer> r = new ArrayList<Integer>();
			for (WeightedEdge e : this.adj.get(o)) {
				r.add(e.d);
			}
			return r;
		} else {
			throw new InvalidSizeException("Vertice not in graph");
		}
	}
}
