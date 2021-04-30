package aula7b;

import java.util.ArrayList;
import java.util.Arrays;

class Edge {
	int s;
	int d;
	int w;
	public Edge(int s, int d, int w) {
		this.s = s;
		this.d = d;
		this.w = w;
	}
	@Override
	public String toString() {
		return "Edge [s=" + s + ", d=" + d + ", w=" + w + "]";
	}
}

public class WeightedGraph extends Graph{
	ArrayList<ArrayList<Edge>> adj;

	public WeightedGraph(int dim) {
		super(dim);
		this.adj = new ArrayList<ArrayList<Edge>>();
		for (int i = 0; i < this.dim; i++) {
			this.adj.add(new ArrayList<Edge>());
		}
	}

	public boolean edgeQ(int orig, int dest) {
		if (orig >= 0 && orig < this.dim && dest >= 0 && dest < this.dim) {
			for (Edge e : this.adj.get(orig)) {
				if (e.d == dest) {
					return true;
				}
			}
			return false;
		} else {
			throw new AssertionError("Vertice not in graph");
		}
	}
	// i
	public void addEdge(int orig, int dest, int weig) {
		if (!this.edgeQ(orig, dest)) {
			this.adj.get(orig).add(new Edge(orig, dest, weig));
		}
	}


	// ii
	public int getWeight(int orig, int dest) {
		if (this.edgeQ(orig, dest)) {
			for (Edge e : this.adj.get(orig)) {
				if (e.d == dest) {
					return e.w;
				}
			}
		}
		return 0;
	}

	// iii
	public ArrayList<Integer> offspring(int orig){
		System.out.println("offspring do wg...");
		if (orig >= 0 && orig < this.dim) {
			ArrayList<Integer> r = new ArrayList<Integer>();
			for (Edge e : this.adj.get(orig)) {
				r.add(e.d);
			}
			return r;
		} else {
			throw new AssertionError("Vertice not in graph");
		}
	}

	// iv
	public int pathCost(ArrayList<Integer> path) {
		int r = 0;
		for (int i = 0; i < path.size() - 1; i++) {
			r += this.getWeight(path.get(i), path.get(i+1));
		}
		return r;
	}

	@Override
	public String toString() {
		return "WeightedGraph [adj=" + adj + "]";
	}

	public static void main(String[] args) {
		WeightedGraph wg = new WeightedGraph(3);
		wg.addEdge(0, 1, 3);
		wg.addEdge(1, 2, 10);
		wg.addEdge(2, 0, 5);
		System.out.println(wg);
		System.out.println(wg.getWeight(1, 2));
		System.out.println(wg.offspring(1));
		Integer[] l = {1,2,0,1};
		ArrayList<Integer> al = new ArrayList<Integer>(Arrays.asList(l));
		System.out.println(wg.pathCost(al));
		System.out.println(wg.BFS(0));
	}
}
