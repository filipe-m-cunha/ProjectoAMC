package Projecto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

public class Graph {
	int dim;
	boolean m[][];
	
	public Graph(int dim) {//cria o graph como matrizes
		super();
		this.dim = dim;
		this.m= new boolean [dim][dim];
		for (int i=0; i<m.length; i++) {
			for (int j=0; j<m.length;j++) {
				m[i][j]=false;
			}
		}
	}
	
	LinkedList<String> lisEdges(){//passa o grafo a uma lista com os pares de nos
		LinkedList<String> le = new LinkedList<String>();
		for (int i=0; i<dim; i++) {
			for (int j=0; j<dim;j++) {
				if(m[i][j]) le.add("("+i+","+j+")");
			}
		}
		return le;
	}
	
	@Override
	public String toString() {
		return "Graph [dim=" + dim + ", m=" + Arrays.toString(m) + "]";
	}
	public void addEdge(int o, int d) throws Exception{
		if (0<=o && o<dim && 0<=d && d<dim)
			m[o][d]=true;
		else
			throw new Exception("Error: invalid origin or destiny");
	}
	
	public boolean edgeQ(int o, int d) {
		return m[o][d];
	}
	
	public ArrayList<Integer> offspring (int o){
		ArrayList<Integer> off  = new ArrayList<Integer>();
		for (int j = 0; j<m.length; j++) {
			if (m[o][j]) off.add(j);
		}
		return off;
	}

	static LinkedList<Integer> reverse( ArrayList<Integer> li){
		LinkedList<Integer> lr =new LinkedList<Integer>();
		for (int i = 0; i < li.size(); i++) {
			lr.addFirst(li.get(i));
		}
		
		return lr;
	}
	
	static LinkedList<Integer> reverse2( ArrayList<Integer> li){
		LinkedList<Integer> lr =new LinkedList<Integer>();
		for (Integer integer: li) {
			lr.addFirst(integer);
		}
		
		return lr;
	}
	
	static LinkedList<Integer> reverse3( ArrayList<Integer> li){
		LinkedList<Integer> lr =new LinkedList<Integer>();
		for (Iterator<Integer> iterator = li.iterator(); iterator.hasNext();) {
			Integer integer = iterator.next();
			lr.addFirst(integer);
		}
		
		return lr;
	}
	
	static LinkedList<Integer> reverse4( ArrayList<Integer> li){//no esta a funcionar
		LinkedList<Integer> lr =new LinkedList<Integer>();
		for (ListIterator<Integer> iterator = li.listIterator(li.size()); iterator.hasPrevious();) {
			Integer integer = iterator.previous();
			lr.add(integer);
		}
		return lr;
	}
	
	public boolean pathQ(int o, int d) {
		return BFS(o).contains(d);
	}
	 
	public Graph coGraph() throws Exception {
		Graph cg= new Graph(dim);
		for (int i = 0; i < dim; i++) {
			for (int j = 0; j < dim; j++) {
				if(edgeQ(i,j)) cg.addEdge(j, i);
			}
			
		}
		return cg;
	}
	
	public boolean  stronglyConnectedQ() throws Exception {
		return BFS(0).size()==dim && coGraph().BFS(0).size()==dim;
	}
	
	public ArrayList<Integer> BFS(int o){//breath first
		ArrayList<Integer> visited = new ArrayList<Integer>();
		LinkedList<Integer> queue = new LinkedList<Integer>();
		queue.add(o);
		while(!queue.isEmpty()) {
			int tp=queue.removeFirst(); //2 em 1, processa e tira
			if(!visited.contains(tp)) {
				visited.add(tp);
				queue.addAll(offspring(tp));
			}
		}
		return visited;
	}
	
	public ArrayList<Integer> DFS(int o){
		ArrayList<Integer> visited = new ArrayList<Integer>();
		LinkedList<Integer> stack = new LinkedList<Integer>();
		stack.add(o);
		while(!stack.isEmpty()) {
			int tp=stack.pop(); //2 em 1, processa e tira
			if(!visited.contains(tp)) {
				visited.add(tp);
				stack.addAll(reverse(offspring(tp)));
			}
		}
		return visited;
	}
	
	public int[] SCC()  throws Exception {
		int n=1; //componente que estamos a pross agora
		int v=0;
		int visited=0; //numero de vertices visitados
		int[] sccs = new int[dim];
		while(visited<dim) {
			ArrayList<Integer> bfs = BFS(v);
			ArrayList<Integer> cbfs = coGraph().BFS(v);
			bfs.retainAll(cbfs);
			for (Integer integer : bfs ) {
				sccs[integer]=n;
				visited++;
			}
			n++;
			for(v=v+1; v<dim && sccs[v]!=0; v++);
			
		}
		return sccs;
	}
	
	public static void main(String[] args) {
		try {
		Graph g = new Graph(7);
		g.addEdge(0, 1);
		g.addEdge(0, 2);
		g.addEdge(2, 3);
		g.addEdge(3, 4);
		g.addEdge(3, 5);
		g.addEdge(5, 2);
		g.addEdge(4, 6);
		g.addEdge(6, 5);
		g.addEdge(1, 4);
		System.out.println(g.BFS(0));
		System.out.println(g.DFS(0));//stor diz que esta mal
		ArrayList<Integer> ll=new ArrayList<Integer>();
		for (int i = 0; i < 10; i++) {
			ll.add(i+1);
		}
		System.out.println(g.stronglyConnectedQ());
		System.out.println(ll);
		System.out.println(reverse(ll));
		System.out.println(Arrays.toString(g.SCC()));
		
	}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
