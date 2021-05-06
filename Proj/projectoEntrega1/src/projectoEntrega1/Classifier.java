package projectoEntrega1;

import java.util.ArrayList;

public class Classifier {

	ArrayList<MRFT> mrfts;
	double[] frequence;
	
	
	public Classifier(ArrayList<MRFT> mrfts, double[] frequence) throws Exception {
		super();
		if(mrfts.size() == frequence.length) {
			this.mrfts = mrfts;
			this.frequence = frequence;
		}
		else {
			throw new Exception("Size of classes must match!");
		}
	}
	
	public int Classify(ArrayList<Integer> vector) {
		int index = 0;
		double prob = 0;
		for(int i=0; i<this.mrfts.size(); i++) {
			if(this.mrfts.get(i).probability(vector)*this.frequence[i] > prob) {
				index = i;
				prob = this.mrfts.get(i).probability(vector)*this.frequence[i];
			}
		}
		return index;
	}
}
