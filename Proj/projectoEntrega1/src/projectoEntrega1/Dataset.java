package projectoEntrega1;

import java.util.ArrayList;

public class Dataset {
	
	int dim;
	int indices[];
	double values[][];
	
	public Dataset(int dim) {
		//TODO: Implement Method
	}
	
	public int Count(ArrayList<Integer> indices, ArrayList<Double> vector) {
		//Todo: Implement method;
		return 0;
	}
	
	public void Add(ArrayList<Double> vector) {
		//TODO: Implement method
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
	
	public int numValues(int index) {
		//TODO: Implement Method
		return 0;
	}
	
	public int totalSize() {
		//TODO: ImplementMethod
		return 10;
	}

}
