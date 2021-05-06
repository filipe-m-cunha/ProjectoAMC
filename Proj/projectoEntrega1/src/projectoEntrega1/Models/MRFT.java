package projectoEntrega1.Models;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import projectoEntrega1.Exceptions.InvalidDomainException;
import projectoEntrega1.Exceptions.InvalidSizeException;

public class MRFT {
	
	//Para maximizar a flexibilidade do algoritmo, para al�m de se considerar como
	//vari�veis o conjunto de dados e o grafo (�rvore) obtida pelo algoritmo de Chow-Liu,
	//consideraram-se ainda o valor de delta (usado nas pseudo-contagens), bem como a origem
	//e destino da aresta que induz a orienta��o da �rvore.
	//Aqui a lista das arestas foi considerada uma vari�vel da MRFT, uma vez que � recorrentemente utilizada,
	//logo n�o se justificava o seu c�lculo ser realizado no grafo.
	private Dataset dataset;
	private Graph graph;
	private int oSpec;
	private int dSpec;
	private double delta;
	private List<int[]> lisEdges;
	private List<double[][]> phis;
	
	//Construtor, dado um dataset, uma �rvore, um v�rtice de origem para a aresta especial e um valor de delta
	public MRFT(Dataset dataset, Graph tree, int oSpec, double delta) throws InvalidDomainException, InvalidSizeException {
		super();
		
		if(dataset.getDim() == tree.dim) {
			if(oSpec <= tree.dim ) {
				//Come�a por inicializar todas as vari�veis da classe, o dataset, origem da aresta especial e o delta
				//como os valores dados, a lista das arestas como uma lista vazia e o grafo como um grafo vazio.
				this.dataset = dataset;
				this.graph = new Graph(tree.dim);
				this.lisEdges = new ArrayList<int[]>();
				this.oSpec = oSpec;
				this.delta = delta;
				
				//Posteriormente, � constru�da a �rvore do algoritmo: � feita uma pesquisa em largura a partir
				//do v�rtice oSpec, e � constru�da uma �rvore (j� garantidamente unidirecional, assumindo que o 
				//grafo inicial � uma �rvore, e ent�o n�o tem ciclos) seguindo esta pesquisa.
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
				
				//Inicializa��o dos valores dos phis. � cridada uma lista de matrizes (inicialmente vazias)
				this.phis = new ArrayList<double[][]>();
				for(int i = 0; i<this.lisEdges.size(); i++) {
					
					//Percorre cada aresta do grafo, e adiciona uma matriz vazia � lista dos phis.
					//As dimens�es da matriz inerente � aresta que liga os n�s i e j dever� ter dimens�es
					//domain[i] + 1 * domain[j] + 1, uma vez que dever� armazenar todas as combina��es poss�veis
					//que os n�s i e j podem tomar.
					this.phis.add(new double[this.dataset.getDomain()[lisEdges.get(i)[0]]+1][this.dataset.getDomain()[lisEdges.get(i)[1]]+1]);
					
					//Popula��o das matrizes, com os valores dados pelas f�rmulas apresentadas no enunciado.
					for(int j = 0; j<=this.dataset.getDomain()[lisEdges.get(i)[0]]; j++) {
						for(int k = 0; k<=this.dataset.getDomain()[lisEdges.get(i)[1]]; k++) {
							double [][] phi = this.phis.get(i).clone();
							phi[j][k] = this.calcPhi(this.lisEdges.get(i)[0], this.lisEdges.get(i)[1], j, k);
							this.phis.set(i, phi);
						}
					}
				}
			} else {
				throw new InvalidDomainException("Special root node not in tree!");
			}
		} else {
			throw new InvalidSizeException("Number of variables in the dataset and number of nodes in the tree should be the same!");
		}
		
	}
	
	
	//Fun��o auxiliar para calcular o valor de phi(x1, x2), segundo a f�rmula apresentada no enunciado.
	//Note-se que a inicializa��o das ArrayLists � necess�ria uma vez que a fun��o Count toma ArrayLists como inputs.
	public double calcPhi(int i1, int i2, int x1, int x2) {
		int[] temp1I = {i1, i2};
		int[] temp1V = {x1, x2};
		int[] temp2I = {i1};
		int[] temp2V = {x1};
		if(i1==this.oSpec && i2==this.dSpec) {
			return (this.dataset.count(temp1I, temp1V) + this.delta)/(this.dataset.getValues().size()*this.dataset.getDim() + this.delta*this.dataset.getDomain()[i1]*this.dataset.getDomain()[i2]);
		}
		else {
			return (this.dataset.count(temp1I, temp1V) + this.delta)/(this.dataset.count(temp2I, temp2V) + this.delta*this.dataset.getDomain()[i1]);
		}
	}
	
	
	
	public Dataset getDataset() {
		return dataset;
	}


	public void setDataset(Dataset dataset) {
		this.dataset = dataset;
	}


	public Graph getGraph() {
		return graph;
	}


	public void setGraph(Graph graph) {
		this.graph = graph;
	}


	public int getoSpec() {
		return oSpec;
	}


	public void setoSpec(int oSpec) {
		this.oSpec = oSpec;
	}


	public int getdSpec() {
		return dSpec;
	}


	public void setdSpec(int dSpec) {
		this.dSpec = dSpec;
	}


	public double getDelta() {
		return delta;
	}


	public void setDelta(double delta) {
		this.delta = delta;
	}


	public List<int[]> getLisEdges() {
		return lisEdges;
	}


	public void setLisEdges(List<int[]> lisEdges) {
		this.lisEdges = lisEdges;
	}


	public List<double[][]> getPhis() {
		return phis;
	}


	public void setPhis(List<double[][]> phis) {
		this.phis = phis;
	}


	//Fun��o que recebe um vetor e retorna a probabilidade de ele se encontrar na MRFT, novamente segundo a f�rmula apresentada no enunciado.
	public double probability(int[] vector) throws InvalidSizeException, InvalidDomainException {
		if(vector.length == this.dataset.getDim()) {
			boolean inDomain = true;
			int failedAt = 0;
			for(int i = 0; i<vector.length && inDomain; i++) {
				if(vector[i] > this.dataset.getDomain()[i]) {
					inDomain = false;
					failedAt = i;
				}
			}
			if(inDomain) {
				double prob = 1;
				
				for(int i = 0; i<this.lisEdges.size(); i++) {
					prob = prob*(this.phis.get(i)[vector[this.lisEdges.get(i)[0]]][vector[this.lisEdges.get(i)[1]]]);
				}
				
				return prob;
			}
			else {
				throw new InvalidDomainException("Vector is not in the required domain for classification. In particular, variable" + String.valueOf(failedAt+1) + "is not valid for the current domain");
			}
		} else {
			throw new InvalidSizeException("Vector size must be the same as the dataset dimension!");
		}
		
	}
	
}
