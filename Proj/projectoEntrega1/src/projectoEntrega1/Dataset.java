package projectoEntrega1;

import java.util.ArrayList;

public class Dataset {
	
	int dim;
	int indices[];
	ArrayList<ArrayList<Double>> values;
	ArrayList<ArrayList<Double>> distinctValues;
	int size;
	
	public Dataset(int dim) {
		this.dim = dim;
		this.indices = new int[dim];
		for(int i=1; i<dim; i++) {
			indices[i] = i;
		}
		this.values = new ArrayList<ArrayList<Double>>();
		this.distinctValues = new ArrayList<ArrayList<Double>>();
		this.size=0;
	}
	
	public Dataset(int dim, int indices[]) {
		this.dim = dim;
		this.indices = indices;
		this.values = new ArrayList<ArrayList<Double>>();
		this.distinctValues = new ArrayList<ArrayList<Double>>();
		this.size=0;
	}
	
	public void Add(ArrayList<Double> vector) throws Exception{
		if(vector.size() == this.dim) {
			this.values.add(vector);
			this.size += 1;
			for(int i = 1; i<dim; i++) {
				if(! distinctValues.get(i).contains(vector.get(i))) {
					distinctValues.get(i).add(vector.get(i));
				}
			}
		}
		else {
			throw new Exception("vector size does not match dataset size");
		}
	}
	
	public int Count(ArrayList<Integer> indices, ArrayList<Double> vector) {
		int count = 0;
		for(int i = 0; i<this.size; i++) {
			ArrayList<Double> toAnalyse = values.get(i);
			boolean ok = true;
			for(int j=0; j<indices.size() && ok; j++) {
				if(toAnalyse.get(indices.get(j))!=vector.get(j)) {
					ok = false;
				}
			}
			if (ok) {count += 1;}
		}
		return count;
	}
	
	public double[][] Fiber(int classe){
		//TODO: Implement method
		
		double[][] temp = new double[dim][dim];
		for (int i=0; i<dim; i++) {
			for(int j=0; j<dim; j++) {
				temp[i][j] = 0;
			}
		}
		return temp;
	}


}