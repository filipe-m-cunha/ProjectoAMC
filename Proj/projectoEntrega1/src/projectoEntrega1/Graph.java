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

	public ArrayList<Integer> BFS(int o){
		if (o >= 0 && o < this.dim) {
			ArrayList<Integer> r = new ArrayList<Integer>();
			Queue<Integer> q = new LinkedList<Integer>();
			boolean[] visited = new boolean[this.dim];
			q.add(o);
			while(! q.isEmpty() ) {
				int node = q.remove(); // remove o mais antigo
				if (!visited[node]) {
					r.add(node);
					visited[node] = true;
					for(int i : this.offspring(node)) {
						q.add(i);
					}
				}
			}
			return r;
		} else {
			throw new AssertionError("Vertice not in graph");
		}
	}

	public ArrayList<Integer> DFS(int o) {
		if (o >= 0 && o < this.dim) {
			ArrayList<Integer> r = new ArrayList<Integer>();
			Stack<Integer> stack = new Stack<Integer>();
			boolean[] visited = new boolean[this.dim];
			stack.push(o);
			while(! stack.isEmpty() ) {
				int node = stack.pop(); // remove o mais recente
				if (!visited[node]) {
					r.add(node);
					visited[node] = true;
					for(int i : this.offspring(node)) {
						stack.push(i);
					}
				}
			}
			return r;
		} else {
			throw new AssertionError("Vertice not in graph");
		}
	}

	public boolean pathQ(int o, int d) {
		if (o >= 0 && o < this.dim && d >= 0 && d < this.dim) {
			boolean[] visited = new boolean[this.dim];
			Queue<Integer> q = new LinkedList<Integer>();
			boolean found = false;
			q.add(o);
			while(!q.isEmpty() && !found) {
				int node = q.remove();
				if (!visited[node]) {
					found = (node == d);
					visited[node] = true;
					for (int i : this.offspring(node)) {
						q.add(i);
					}
				}
			}
			return found;
		} else {
			throw new AssertionError("Vertice not in graph");
		}
	}

	// i
	public Graph coGraph() {
		Graph coG = new Graph(this.dim);
		for (int orig = 0; orig < this.dim; orig++) {
			for (Integer dest : this.adj.get(orig)) {
				coG.addEdge(dest, orig);
			}
		}
		return coG;
	}

	// ii
	public boolean stronglyConnected() {
		return this.BFS(0).size() == this.dim && 		  // vértices com origem em 0
				this.coGraph().BFS(0).size() == this.dim; // vértices com destino em 0
	}

	// iii
	public int[] SCC() {
		int[] sccs = new int[this.dim]; // 0 significa "não visitado"
		int currentV = 0;
		int id = 1;
		int nrVisited = 0;
		Graph coG = this.coGraph();

		while (nrVisited < this.dim) {
			// vértices acessíveis a partir de currentV
			ArrayList<Integer> dests = this.BFS(currentV);

			// vértices que chegam até currentV
			ArrayList<Integer> origs = coG.BFS(currentV);

			// intersectar dá a SCC
			dests.retainAll(origs);

			for (Integer i : dests) {
				sccs[i] = id;
				nrVisited++;
			}

			for (currentV = currentV + 1; currentV < this.dim && sccs[currentV] != 0; currentV++);

			id++;
		}
		return sccs;
	}

	// iv
	public ArrayList<Integer> shortestPath(int orig, int dest){
		if (orig >= 0 && orig < this.dim && dest >= 0 && dest < this.dim) {
			ArrayList<Integer> path = new ArrayList<Integer>();
			Queue<Integer> q = new LinkedList<Integer>();
			boolean[] visited = new boolean[this.dim]; // inicializada a false (JAVA)
			boolean found = false;
			int[] parents = new int[this.dim];
			for(int i = 0; i < parents.length; i++) {
				parents[i] = -1;
			}
			q.add(orig);
			while (!q.isEmpty() && !found) {
				int v = q.remove();
				if (!visited[v]) {
					visited[v] = true;
					found = (v == dest);
					for (int i : this.offspring(v)) {
						q.add(i);
						if (parents[i] == -1) { // se i ainda não tem pai
							parents[i] = v;
						}
					}
				}
			}
			// calcular o caminho de trás para a frente
			if (found) {
				path.add(dest);
				int v = dest;
				while (v != orig) {
					v = parents[v];
					path.add(0, v); // insere novo vértice ao início do caminho (até orig)
				}
			}
			return path;
		} else {
			throw new AssertionError("Vertice not in graph");
		}
	}

	public static void main(String[] args) {
		Graph g = new Graph(8);
		int[][] edges = {{0,4}, {0,6}, {1,2}, {2,1}, {2,3}, {2,6}, {2,7}, {3,5}, {4,3}, {4,5}, {5,3}, {6,3}, {6,5}, {7,2}, {7,5}};
		for(int[] e : edges) {
			g.addEdge(e[0], e[1]);
		}

//		System.out.println(g);
//		//g.removeEdge(1, 2);
//		//System.out.println(g);
//		System.out.println("edgeQ: " + g.edgeQ(0, 3));
//		System.out.println("offspring: " + g.offspring(0));
//		System.out.println("BFS: "+g.BFS(0));
//		System.out.println("DFS: "+g.DFS(0));
//		System.out.println("pathQ1: "+g.pathQ(0,4));
//		System.out.println("pathQ2: "+g.pathQ(4,0));
//		System.out.println("pathQ3: "+g.pathQ(4,1));

		System.out.println(g);
		System.out.println(g.coGraph());
		System.out.println(g.stronglyConnected());
		System.out.println(Arrays.toString(g.SCC()));
		System.out.println(g.shortestPath(1, 5));
	}
}