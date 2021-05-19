package projectoEntrega1.Models;

import java.io.Serializable;

public class WeightedEdge implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int o;
	int d;
	double w;
	
	public WeightedEdge(int o, int d, double w) {
		this.o = o;
		this.d = d;
		this.w = w;
	}
}
