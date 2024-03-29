package projectoEntrega1.Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import projectoEntrega1.Exceptions.InvalidDomainException;
import projectoEntrega1.Exceptions.InvalidSizeException;

public class MRFT implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/*Para maximizar a flexibilidade do algoritmo, para al�m de se considerar como
	//vari�veis o conjunto de dados e o grafo (�rvore) obtida pelo algoritmo de Chow-Liu,
	//consideraram-se ainda o valor de delta (usado nas pseudo-contagens), bem como a origem
	//e destino da aresta que induz a orienta��o da �rvore.
	//Aqui a lista das arestas foi considerada uma vari�vel da MRFT, uma vez que � recorrentemente utilizada,
	logo n�o se justificava o seu c�lculo ser realizado no grafo.*/
	private int oSpec;
	private int dSpec;
	private double delta;
	private List<int[]> lisEdges;
	private List<double[][]> phis;
	
	//Construtor, dado um dataset, uma �rvore, um v�rtice de origem para a aresta especial e um valor de delta
	public MRFT(Dataset dataset, WeightedGraph tree, int oSpec, double delta) throws InvalidDomainException, InvalidSizeException {
		
		if(dataset.getDim() == tree.getDim()) {
			if(oSpec <= tree.getDim() ) {
				//Come�a por inicializar todas as vari�veis da classe, o dataset, origem da aresta especial e o delta
				//como os valores dados, a lista das arestas como uma lista vazia e o grafo como um grafo vazio.
				WeightedGraph graph = new WeightedGraph(tree.getDim());
				this.lisEdges = new ArrayList<int[]>();
				this.oSpec = oSpec;
				this.delta = delta;
				
				//Posteriormente, � constru�da a �rvore do algoritmo: � feita uma pesquisa em largura a partir
				//do v�rtice oSpec, e � constru�da uma �rvore (j� garantidamente unidirecional, assumindo que o 
				//grafo inicial � uma �rvore, e ent�o n�o tem ciclos) seguindo esta pesquisa.
				Stack<Integer> stack = new Stack<Integer>();
				boolean[] visited = new boolean[tree.getDim()];
				stack.push(oSpec);
				this.dSpec = tree.offspring(oSpec).get(0);
				while(!stack.isEmpty()) {
					int node = stack.pop();
					if(!visited[node]) {
						visited[node] = true;
						for(int i:tree.offspring(node)) {
							stack.push(i);
							if(!visited[i]) {
								graph.addEdge(node, i, 1);
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
					this.phis.add(new double[dataset.getDomain()[lisEdges.get(i)[0]]+1][dataset.getDomain()[lisEdges.get(i)[1]]+1]);
					
					//Popula��o das matrizes, com os valores dados pelas f�rmulas apresentadas no enunciado.
					for(int j = 0; j<=dataset.getDomain()[lisEdges.get(i)[0]]; j++) {
						for(int k = 0; k<=dataset.getDomain()[lisEdges.get(i)[1]]; k++) {
							double [][] phi = this.phis.get(i).clone();
							phi[j][k] = this.calcPhi(dataset, this.lisEdges.get(i)[0], this.lisEdges.get(i)[1], j, k);
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
	public double calcPhi(Dataset dataset, int i1, int i2, int x1, int x2) {
		int[] temp1I = {i1, i2};
		int[] temp1V = {x1, x2};
		int[] temp2I = {i1};
		int[] temp2V = {x1};
		if(i1==this.oSpec && i2==this.dSpec) {
			return (dataset.count(temp1I, temp1V) + this.delta)/(dataset.getValues().size() + this.delta*(dataset.getDomain()[i1]+1)*(dataset.getDomain()[i2] +1));
		}
		else {
			return (dataset.count(temp1I, temp1V) + this.delta)/(dataset.count(temp2I, temp2V) + this.delta*(dataset.getDomain()[i2]+1));
		}
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
	public double prob(int[] dom, int[] vector) throws InvalidSizeException, InvalidDomainException {
		//try {
		if(vector.length == dom.length-1) {
			boolean inDomain = true;
			int failedAt = 0;
			for(int i = 0; i<vector.length-1 && inDomain; i++) {
				if(vector[i] > dom[i]) {
					System.out.println(dom[i]);
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
				throw new InvalidDomainException("<html> O vetor n�o se encontra no dom�nio pedido. <br> Em particular, a vari�vel " + String.valueOf(failedAt+1) + "<br> n�o � v�lida para o dom�nio do conjunto de dados fornecido");
			}
		} else {
			throw new InvalidSizeException("Vector size must be the same as the dataset dimension!");
		}
	}
	
}
