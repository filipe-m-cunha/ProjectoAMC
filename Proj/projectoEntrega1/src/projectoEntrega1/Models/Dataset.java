package projectoEntrega1.Models;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import projectoEntrega1.Exceptions.InvalidSizeException;

public class Dataset implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int dim;
	private List<int[]> values;
	private int domain[];
	
	public int getDim() {
		return dim;
	}

	public void setDim(int dim) {
		this.dim = dim;
	}

	public List<int[]> getValues() {
		return values;
	}

	public void setValues(List<int[]> values) {
		this.values = values;
	}

	public int[] getDomain() {
		return domain;
	}

	public void setDomain(int[] domain) {
		this.domain = domain;
	}

	//Construtor: Inicializa o dataset (vazio ao �nicio), com dimens�o (n�mero de vari�veis) 0, 
	//e com dom�nio nulo;
	public Dataset() {
		this.dim = 0;
		this.values = new ArrayList<int[]>();
		this.domain = null;
	}
	
	//Construtor Alternativo: Ler um ficheiro .csv e inserir cada linha uma a uma num dataset
	//acabado de criar;
	public Dataset(String file) throws Exception {
		this.dim = 0;
		this.values = new ArrayList<int[]>();
		this.domain = null;
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line;
		while((line=reader.readLine())!= null) {
			String [] sl = line.split(",");
			int a[] = new int[sl.length];
			for(int i = 0; i<sl.length; i++) {
				a[i] = Integer.parseInt(sl[i]);
			}
			add(a);
		}
		reader.close();
	}
	
	// Fun��o que deve adicionar um vetor ao dataset. O seu funcionamento � como se segue:
	// -> Recebe um vetor de inteiros (linha a adicionar);
	// -> Verifica se o dataset j� tem algum valor (se n�o tiver, ent�o a sua dimens�o ser� zero);
	// -> Se o dataset ainda n�o tiver valores, ent�o a sua dimens�o ser� o comprimento do vetor de input, 
	//este � armazenado, e o novo dominion ser� precisamente o vetor;
	// -> Se o dataset j� tiver valores, confirma se o novo vetor tem o tamanho certo (dim), se n�o tiver � lan�ada uma excep��o;
	// -> Se a dimens�o estiver certa, o vetor � adicionado � ArrayList dos valores do conjunto de dados.
	
	public void add(int[] vector) throws Exception{
		if(this.dim == 0) {
			this.domain = vector.clone();
			this.values.add(vector);
			this.dim = vector.length;
		}
		else{
			if(vector.length == this.dim) {
				this.values.add(vector);
				for(int i = 0; i<dim; i++) {
					if(vector[i]>this.domain[i]) {
						this.domain[i] = vector[i];
					}
				}
			}
			else {
				throw new InvalidSizeException("vector size does not match dataset size");
			}
		}
	}
	
	//A seguinte fun��o deve receber uma lista de ind�ces, bem como um vetor de valores,
	//e dever� retornar o n�mero de vezes que cada um dos valores aparece no ind�ce respetivo para no dataset.
	
	public int count(int[] indices, int[] vector) {
		int count = 0;
		for(int i = 0; i<values.size(); i++) {
			boolean found = true;
			for(int j=0; j<indices.length && found; j++) {
				if(vector[j] != values.get(i)[indices[j]]) {
					found = false;
				}
			}
			if(found) {count+=1;}
		}
		return count;
	}
	
	//Recebe um inteiro e retorna a parti��o do dataset que tem esse valor como classe-objetivo
	//Deve-se notar que se assume a classe objetivo como sendo a �ltima.
	
	public Dataset fiber(int classe) throws Exception{
		Dataset fiber = new Dataset();
		for(int i = 0; i<this.values.size(); i++) {
			if(this.values.get(i)[this.dim-1] == classe) {
				fiber.add(removeLast(this.values.get(i)).clone());
			}
		}
		fiber.setDomain(removeLast(this.domain.clone()));
		return fiber;
	}
	
	//Fun��o que apenas auxilia a correta visualiza��o do dataset
	public String toStringaux() {
		String s= "";
		for (int[] a:values) {
			s = s+"\n" + Arrays.toString(a);
		}
		return s;
	}
		
	//Fun��o para visualizar o dataset
	@Override
	public String toString() {
		return "Dataset [dim=" + dim + ", values=" + toStringaux() + ", domain=" + Arrays.toString(domain) + "]";
	}
	
	public int[] removeLast(int[] vec) {
		int[] vecCopy = vec.clone();
		int[] res = new int[vecCopy.length-1];
		for(int i=0; i<vecCopy.length-1; i++) {
			res[i] = vecCopy[i];
		}
		return res;
	}
	
	public ArrayList<Dataset> datasetInicialization() throws Exception{
		ArrayList<Dataset> res = new ArrayList<Dataset>();
		for(int i = 0; i<=this.domain[this.dim-1]; i++) {
			res.add(this.fiber(i));
		}
		return res;
	}
	
	public double[] getFrequencies() {
		double[] frequencies = new double[this.domain[this.dim - 1]+1];
		double size = (double) this.values.size();
		for(int i = 0; i<=this.domain[this.dim - 1]; i++) {
			double dom = (double) this.count(new int [] {this.dim -1},new int[] {i});
			frequencies[i] = dom/size;
		}
		return frequencies;
	}

}
