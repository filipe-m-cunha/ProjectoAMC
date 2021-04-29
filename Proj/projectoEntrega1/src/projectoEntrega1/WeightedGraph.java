package projectoEntrega1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class WeightedGraph {
	
	int dim;
	double m[][];
	
	//Cria o grafo como uma matriz, em que se a entrada M_{i,j} for -1 (assumo que todos os pesos sejam psoitivos
	//n�o h� aresta a ligar i e j, caso contr�rio h� uma aresta com peso i,j
	public WeightedGraph(int dim) {
		
		super();
		this.dim = dim;
		this.m = new double[dim][dim];
		
		for(int i = 0; i<m.length; i++) {
			for(int j=0; j<m.length; j++) {
				this.m[i][j]=-1;
			}
		}
	}
	
	//Dever� listar todas as arestas do grafo
	LinkedList<String> lisEdges(){
		LinkedList<String> le = new LinkedList<String>();
		for (int i=0; i<dim; i++) {
			for (int j=0; j<dim;j++) {
				if(m[i][j] != -1) le.add("("+i+","+j+","+m[i][j]+")");
			}
		}
		return le;
	}
	
	//S� para ver o que � que se est� a passar
	@Override
	public String toString() {
		return "Graph [dim=" + dim + ", m=" + lisEdges() + "]";
	}
	
	//Cria uma aresta entre o n� o e o n� d com custo c
	public void addEdge(int o, int d, double c) throws Exception{
		if(0<=o && o<this.dim && 0<=d && d<dim && c>=0) {
			m[o][d] = c;
		}
		else {
			throw new Exception("Error: invalid origin, destiny or cost");
		}
		
	}
	public ArrayList<Integer> offspring (int o){
		ArrayList<Integer> off  = new ArrayList<Integer>();
		for (int j = 0; j<m.length; j++) {
			if (m[o][j] != -1) off.add(j);
		}
		return off;
	}
	
	public static void main(String[] args) {
		try {
		WeightedGraph g = new WeightedGraph(7);
		g.addEdge(0, 1, 0.5);
		g.addEdge(0, 2, 0.1);
		g.addEdge(2, 3, 0.2);
		g.addEdge(3, 4, 0.4);
		g.addEdge(3, 5, 0.10);
		g.addEdge(5, 2, 0.45);
		g.addEdge(4, 6, 4);
		g.addEdge(6, 5, 55);
		g.addEdge(1, 4, 10);
		System.out.println(g);
		System.out.println(g.offspring(3));
		
	}
		catch (Exception e) {
			e.printStackTrace();

		}
	}
}