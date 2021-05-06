package projectoEntrega1.Models;

import java.util.List;

import projectoEntrega1.Exceptions.InvalidDomainException;
import projectoEntrega1.Exceptions.InvalidSizeException;

public class Classifier {

	List<MRFT> mrfts;
	double[] frequence;
	
	
	public Classifier(List<MRFT> mrfts, double[] frequence) throws Exception {
		super();
		if(mrfts.size() == frequence.length) {
			this.mrfts = mrfts;
			this.frequence = frequence;
		}
		else {
			throw new InvalidSizeException("Size of classes must match!");
		}
	}
	
	public int Classify(int[] vector) throws InvalidSizeException, InvalidDomainException {
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
