package projectoEntrega1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Dataset {
	
	int dim;
	ArrayList<int[]> values;
	int domain[];
	
	
	
	public Dataset(int dim) {
		super();
		this.dim = 0;
		this.values = new ArrayList<int[]>();
		this.domain = null;
	}
	
	public Dataset(String file) throws IOException {
		
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line;
		while((line=reader.readLine())!= null) {
			System.out.println(line);
		}
		reader.close();
	}
	
	public void Add(int[] vector) throws Exception{
		if(this.dim == 0 ) {
			this.values.add(vector);
			this.dim = vector.length;
			this.domain = vector;
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
				throw new Exception("vector size does not match dataset size");
			}
		}
	}
	
	public String toStringaux() {
		String s= "";
		for (int[] a:values) {
			s = s+"\n" + Arrays.toString(a);
		}
		return s;
	}
	
	
	@Override
	public String toString() {
		return "Dataset [dim=" + dim + ", values=" + toStringaux() + ", domain=" + Arrays.toString(domain) + "]";
	}

	public int Count(ArrayList<Integer> indices, ArrayList<Integer> vector) {
		int count = 0;
		for(int i = 0; i<values.size(); i++) {
			boolean found = true;
			for(int j=0; j<indices.size() && found; j++) {
				if(vector.get(j) != values.get(i)[indices.get(j)]) {
					found = false;
				}
			}
			if(found) {count+=1;}
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

	public static void main(String[] args) {
		try {
			Dataset df = new Dataset(2);
			df.Add(new int[] {1, 2});
			df.Add(new int[] {2, 3});
			df.Add(new int[] {2, 3});
			df.Add(new int[] {7, 8});
			ArrayList<Integer> testI = new ArrayList<Integer>();
			ArrayList<Integer> testV = new ArrayList<Integer>();
			testI.add(0);
			testI.add(1);
			testV.add(2);
			testV.add(3);
			System.out.println(df.Count(testI, testV));
	}
		catch (Exception e) {
			e.printStackTrace();

		}
	}
}
