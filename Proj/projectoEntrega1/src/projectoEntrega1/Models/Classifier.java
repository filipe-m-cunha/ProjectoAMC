package projectoEntrega1.Models;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import projectoEntrega1.Exceptions.InvalidDomainException;
import projectoEntrega1.Exceptions.InvalidSizeException;

public class Classifier {
	
	public double[] frequence;
	public ArrayList<MRFT> mrfts;
	public Dataset data;
	
	/*Inicializa um objecto classifier, com uma lista de MRFT's e um array de frequênicias.
	*Confirma que os tamanhos dos dois arrays coincidem (deve haver um MRFT para cada classe, 
	e uma frequência para cada classe)*/
	public Classifier(Dataset data, double delta) throws Exception {
		this.data = data;
		this.frequence = data.getFrequencies();
		ArrayList<Dataset> datasets = data.datasetInicialization();
		ArrayList<MRFT> mrfts = new ArrayList<MRFT>();
		for(Dataset d:datasets) {
			ChowLiu cLiu = new ChowLiu(d);
			mrfts.add(new MRFT(cLiu.getData(), cLiu.getGraph(), cLiu.getGraph().getEdgeList().get(0).o, delta));
		}
		this.mrfts = mrfts;
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
	
	public double getAccuracyBin() throws InvalidSizeException, InvalidDomainException {
		double TP = 0;
		double TN = 0;
		double FP = 0;
		double FN = 0;
		for(int[] i:this.data.getValues()) {
			int[] val = new int[this.data.getDim()-1];
			for(int j=0; j<this.data.getDim()-1; j++) {
				val[j] = i[j];
			}
			int pred = this.Classify(val);
			int real = i[this.data.getDim()-1];
			if(pred == 1) {
				if(real == 1) {
					TP += 1;
				}else {
					if(real==0) {
						FP += 1;
					}
				}
			}else {
				if(real == 1) {
					FN += 1;
				}else {
					if(real==0) {
						TN += 1;
					}
				}
			}
		}
		return (TP + TN)/(TP + TN + FP + FN);
	}
}
