package projectoEntrega1;

import java.util.ArrayList;
import java.util.Stack;

public class MRFT {
	
	//Para maximizar a flexibilidade do algoritmo, para além de se considerar como
	//variáveis o conjunto de dados e o grafo (árvore) obtida pelo algoritmo de Chow-Liu,
	//consideraram-se ainda o valor de delta (usado nas pseudo-contagens), bem como a origem
	//e destino da aresta que induz a orientação da árvore.
	//Aqui a lista das arestas foi considerada uma variável da MRFT, uma vez que é recorrentemente utilizada,
	//logo não se justificava o seu cálculo ser realizado no grafo.
	Dataset dataset;
	Graph graph;
	int oSpec;
	int dSpec;
	double delta;
	ArrayList<int[]> lisEdges;
	ArrayList<double[][]> phis;
	
	//Construtor, dado um dataset, uma árvore, um vértice de origem para a aresta especial e um valor de delta
	public MRFT(Dataset dataset, Graph tree, int oSpec, double delta) {
		super();
		
		//Começa por inicializar todas as variáveis da classe, o dataset, origem da aresta especial e o delta
		//como os valores dados, a lista das arestas como uma lista vazia e o grafo como um grafo vazio.
		this.dataset = dataset;
		this.graph = new Graph(tree.dim);
		this.lisEdges = new ArrayList<int[]>();
		this.oSpec = oSpec;
		this.delta = delta;
		
		//Posteriormente, é construída a árvore do algoritmo: é feita uma pesquisa em largura a partir
		//do vértice oSpec, e é construída uma árvore (já garantidamente unidirecional, assumindo que o 
		//grafo inicial é uma árvore, e então não tem ciclos) seguindo esta pesquisa.
		Stack<Integer> stack = new Stack<Integer>();
		boolean[] visited = new boolean[tree.dim];
		stack.push(oSpec);
		this.dSpec = tree.offspring(oSpec).get(0);
		while(! stack.isEmpty()) {
			int node = stack.pop();
			if(!visited[node]) {
				visited[node] = true;
				for(int i:tree.offspring(node)) {
					stack.push(i);
					if(!visited[i]) {
						this.graph.addEdge(node, i);
						this.lisEdges.add(new int[] {node, i});}
				}
			}
		}
		
		//Inicialização dos valores dos phis. É cridada uma lista de matrizes (inicialmente vazias)
		this.phis = new ArrayList<double[][]>();
		for(int i = 0; i<this.lisEdges.size(); i++) {
			
			//Percorre cada aresta do grafo, e adiciona uma matriz vazia à lista dos phis.
			//As dimensões da matriz inerente à aresta que liga os nós i e j deverá ter dimensões
			//domain[i] + 1 * domain[j] + 1, uma vez que deverá armazenar todas as combinações possíveis
			//que os nós i e j podem tomar.
			this.phis.add(new double[this.dataset.domain[lisEdges.get(i)[0]]+1][this.dataset.domain[lisEdges.get(i)[1]]+1]);
			
			//População das matrizes, com os valores dados pelas fórmulas apresentadas no enunciado.
			for(int j = 0; j<=this.dataset.domain[lisEdges.get(i)[0]]; j++) {
				for(int k = 0; k<=this.dataset.domain[lisEdges.get(i)[1]]; k++) {
					double [][] phi = this.phis.get(i);
					phi[j][k] = this.calcPhi(this.lisEdges.get(i)[0], this.lisEdges.get(i)[1], j, k);
					this.phis.set(i, phi);
				}
			}
		}
	}
	
	
	//Função auxiliar para calcular o valor de phi(x1, x2), segundo a fórmula apresentada no enunciado.
	//Note-se que a inicialização das ArrayLists é necessária uma vez que a função Count toma ArrayLists como inputs.
	public double calcPhi(int i1, int i2, int x1, int x2) {
		int[] temp1I = {i1, i2};
		int[] temp1V = {x1, x2};
		int[] temp2I = {i1};
		int[] temp2V = {x1};
		if(i1==this.oSpec && i2==this.dSpec) {
			return (this.dataset.Count(temp1I, temp1V) + this.delta)/(this.dataset.values.size()*this.dataset.dim + this.delta*this.dataset.domain[i1]*this.dataset.domain[i2]);
		}
		else {
			return (this.dataset.Count(temp1I, temp1V) + this.delta)/(this.dataset.Count(temp2I, temp2V) + this.delta*this.dataset.domain[i1]);
		}
	}
	
	//Função que recebe um vetor e retorna a probabilidade de ele se encontrar na MRFT, novamente segundo a fórmula apresentada no enunciado.
	public double probability(ArrayList<Integer> vector) {
		
		double prob = 1;
		
		for(int i = 0; i<this.lisEdges.size(); i++) {
			prob = prob*(this.phis.get(i)[vector.get(this.lisEdges.get(i)[0])][vector.get(this.lisEdges.get(i)[1])]);
		}
		
		return prob;
	}
	
}
