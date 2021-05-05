package projectoEntrega1;

import java.util.ArrayList;

public class Classifier {

	ArrayList<MRFT> mrfts;
	double[] frequence;
	
	
	public Classifier(ArrayList<MRFT> mrfts, double[] frequence) {
		super();
		this.mrfts = mrfts;
		this.frequence = frequence;
	}
	
	public int Classify(ArrayList<Integer> vector) {
		double[] prob = new double[2];
		prob[0] = 0; prob[1] = 0;
		for(int i=0; i<this.mrfts.size(); i++) {
			MRFT mrft = this.mrfts.get(i);
			if(mrft.probability(vector) > prob[1]) {
				prob[0] = i;
				prob[1] = mrft.probability(vector);
			}
		}
		return (int) prob[0];
	}
}
