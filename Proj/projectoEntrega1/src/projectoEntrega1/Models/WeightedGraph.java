package projectoEntrega1.Models;

import java.util.ArrayList;
import java.util.List;

import projectoEntrega1.Exceptions.InvalidSizeException;

public class WeightedGraph{
	
	private int dim;
	private List<WeightedEdge> edgeList;
	private List<Integer> parents;
	private List<Integer> rank;

	public WeightedGraph(int dim) {
		this.dim = dim;
		this.edgeList = new ArrayList<WeightedEdge>();
		this.parents = new ArrayList<Integer>(this.dim);
		this.rank = new ArrayList<Integer>(this.dim);
	}


	public int getDim() {
		return dim;
	}



	public void setDim(int dim) {
		this.dim = dim;
	}



	public List<WeightedEdge> getEdgeList() {
		return edgeList;
	}



	public void setEdgeList(List<WeightedEdge> edgeList) {
		this.edgeList = edgeList;
	}



	public List<Integer> getParents() {
		return parents;
	}



	public void setParents(List<Integer> parents) {
		this.parents = parents;
	}



	public List<Integer> getRank() {
		return rank;
	}



	public void setRank(List<Integer> rank) {
		this.rank = rank;
	}



	public boolean edgeQ(int o, int d) throws InvalidSizeException {
		if (o >= 0 && o < this.getDim() && d >= 0 && d < this.getDim()) {
			boolean found = false;
			for (int i = 0; i<this.edgeList.size() && !found; i++) {
				if (this.edgeList.get(i).o == o && this.edgeList.get(i).d == d) {
					found = true;
				}
			}
			return found;
		} else {
			throw new InvalidSizeException("Vertice not in graph");
		}
	}

	public void addEdge(int o, int d, double w) throws InvalidSizeException {
		if (!this.edgeQ(o, d)) {
			this.edgeList.add(new WeightedEdge(o, d, w));
		}
	}

	public void addEdge(WeightedEdge e) throws InvalidSizeException {
		if (!this.edgeQ(e.o, e.d)) {
			this.edgeList.add(e);
		}
	}

	public double getWeight(int o, int d) throws InvalidSizeException {
		if (this.edgeQ(o, d)) {
			for (int i = 0; i<this.edgeList.size(); i++) {
				if (this.edgeList.get(i).o == o && this.edgeList.get(i).d == d) {
					return this.edgeList.get(i).w;
				}
			}
		}
		return 0;
	}

	public ArrayList<Integer> offspring(int o) throws InvalidSizeException{
		if (o >= 0 && o < this.getDim()) {
			ArrayList<Integer> r = new ArrayList<Integer>();
			for (int i =0; i<this.edgeList.size(); i++) {
				if(this.edgeList.get(i).o == o) {
					r.add(this.edgeList.get(i).d);
				}
			}
			return r;
		} else {
			throw new InvalidSizeException("Vertice not in graph");
		}
	}
	
	public int getParent(int d) {
		if (this.parents.get(d) == d) {
			return d;
		}
		else {
			return getParent(this.parents.get(d));
		}
	}
	
	public void MST(ArrayList<WeightedEdge> result) throws InvalidSizeException {
		for(int i = 0; i<this.dim; i++) {
			this.parents.add(i, i);
			this.rank.add(i, 0);
		}
		this.edgeList.sort((WeightedEdge o, WeightedEdge d) -> d.w - o.w);
		for(WeightedEdge e: this.getEdgeList()) {
			int root1 = getParent(e.o);
			int root2 = getParent(e.d);
			if (root1 != root2) {
				result.add(e);
				if(rank.get(root1) > rank.get(root2)) {
					this.parents.set(root2, root1);
					this.rank.set(root1, rank.get(1)+1);
				}
				else {
					this.parents.set(root1, root2);
					this.rank.set(root2, rank.get(root2)+1);
					

				}
			}
		}
	}
	
	public void DisplayEdges(List<WeightedEdge> result) {
        int cost = 0;
        System.out.print( "\nEdges of maximum spanning tree : ");
        for (WeightedEdge edge : result) {
            System.out.print( "[" + edge.o+ "-" + edge.d+ "]-(" + edge.w + ") ");
            cost += edge.w;
        }
        System.out.println("\nCost of maximum spanning tree : " + cost);
    }
	
	public static void main (String args[]) throws InvalidSizeException {

        int num_nodes = 6; 
        WeightedEdge e1 = new WeightedEdge(0, 1, 4);
        WeightedEdge e2 = new WeightedEdge(0, 2, 1);
        WeightedEdge e3 = new WeightedEdge(0, 3, 5);
        WeightedEdge e4 = new WeightedEdge(1, 3, 2);
        WeightedEdge e5 = new WeightedEdge(1, 4, 3);
        WeightedEdge e6 = new WeightedEdge(1, 5, 3);
        WeightedEdge e7 = new WeightedEdge(2, 3, 2);
        WeightedEdge e8 = new WeightedEdge(2, 4, 8);
        WeightedEdge e9 = new WeightedEdge(3, 4, 1);
        WeightedEdge e10 = new WeightedEdge(4, 5, 3);

        WeightedGraph g1 = new WeightedGraph(num_nodes);

        g1.addEdge(e1);
        g1.addEdge(e2);
        g1.addEdge(e3);
        g1.addEdge(e4);
        g1.addEdge(e5);
        g1.addEdge(e6);
        g1.addEdge(e7);
        g1.addEdge(e8);
        g1.addEdge(e9);
        g1.addEdge(e10);

        ArrayList<WeightedEdge> mst = new ArrayList<WeightedEdge>();
        g1.MST(mst);
        g1.DisplayEdges(mst);
        
        num_nodes = 7;

        WeightedEdge a = new WeightedEdge(0, 1, 1);
        WeightedEdge b = new WeightedEdge(0, 2, 2);
        WeightedEdge c = new WeightedEdge(0, 3, 1);
        WeightedEdge d = new WeightedEdge(0, 4, 1);
        WeightedEdge e = new WeightedEdge(0, 5, 2);
        WeightedEdge f = new WeightedEdge(0, 6, 1);
        WeightedEdge g = new WeightedEdge(1, 2, 2);
        WeightedEdge h = new WeightedEdge(1, 6, 2);
        WeightedEdge i = new WeightedEdge(2, 3, 1);
        WeightedEdge j = new WeightedEdge(3, 4, 2);
        WeightedEdge k = new WeightedEdge(4, 5, 2);
        WeightedEdge l = new WeightedEdge(5, 6, 1);

        WeightedGraph g2 = new WeightedGraph(num_nodes);
        g2.addEdge(a);
        g2.addEdge(b);
        g2.addEdge(c);
        g2.addEdge(d);
        g2.addEdge(e);
        g2.addEdge(f);
        g2.addEdge(g);
        g2.addEdge(h);
        g2.addEdge(i);
        g2.addEdge(j);
        g2.addEdge(k);
        g2.addEdge(l);

        mst.clear();
        g2.MST(mst);
        g2.DisplayEdges(mst);
    }
}
