package projectoEntrega1.Models;

import java.util.List;

import projectoEntrega1.Exceptions.InvalidDomainException;
import projectoEntrega1.Exceptions.InvalidSizeException;

public class Classifier {

	List<MRFT> mrfts;
	double[] frequence;
	
	/*Inicializa um objecto classifier, com uma lista de MRFT's e um array de frequênicias.
	*Confirma que os tamanhos dos dois arrays coincidem (deve haver um MRFT para cada classe, 
	e uma frequência para cada classe)*/
	public Classifier(List<MRFT> mrfts, double[] frequence) throws Exception {
		
		if(mrfts.size() == frequence.length) {
			this.mrfts = mrfts;
			this.frequence = frequence;
		}
		else {
			throw new InvalidSizeException("Size of classes must match!");
		}
	}
	
	/*Classifica um dado vetor de inteiros, devolvendo a sua classe mais provável, com base nos
	 * MRFT's do classificador. Calcula a probabilidade do vetor em cada MRFT do array, verfica
	 * que MRFT é que maximiza este valor e devolve o seu índice.
	 */
	public int Classify(int[] vector) throws InvalidSizeException, InvalidDomainException {
		int index = 0;
		double prob = 0;
		for(int i=0; i<this.mrfts.size(); i++) {
			if(this.mrfts.get(i).prob(vector)*this.frequence[i] > prob) {
				index = i;
				prob = this.mrfts.get(i).prob(vector)*this.frequence[i];
			}
		}
		return index;
	}
}
