package projectoEntrega1.Models;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import projectoEntrega1.Exceptions.InvalidDomainException;
import projectoEntrega1.Exceptions.InvalidSizeException;

public class Classifier implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int[] domain;
	private int[] fDomain;
	private double[] frequence;
	private ArrayList<MRFT> mrfts;
	
	public int[] getfDomain() {
		return fDomain;
	}

	public void setfDomain(int[] fDomain) {
		this.fDomain = fDomain;
	}

	public int[] getDomain() {
		return domain;
	}

	public void setDomain(int[] domain) {
		this.domain = domain;
	}

	public double[] getFrequence() {
		return frequence;
	}

	public void setFrequence(double[] frequence) {
		this.frequence = frequence;
	}

	public ArrayList<MRFT> getMrfts() {
		return mrfts;
	}

	public void setMrfts(ArrayList<MRFT> mrfts) {
		this.mrfts = mrfts;
	}

	/*Inicializa um objecto classifier, com uma lista de MRFT's e um array de frequênicias.
	*Confirma que os tamanhos dos dois arrays coincidem (deve haver um MRFT para cada classe, 
	e uma frequência para cada classe)*/
	public Classifier(Dataset data, double delta, boolean save) throws Exception {
		this.frequence = data.getFrequencies();
		this.domain = data.getDomain().clone();
		this.fDomain = data.removeLast(data.getDomain().clone());
		ArrayList<Dataset> datasets = data.datasetInicialization();
		ArrayList<MRFT> mrfts = new ArrayList<MRFT>();
		for(Dataset d:datasets) {
			ChowLiu cLiu = new ChowLiu(d);
			mrfts.add(new MRFT(cLiu.getData(), cLiu.getGraph(), cLiu.getGraph().getEdgeList().get(0).o, delta));
		}
		this.mrfts = mrfts;
		if(save) {
			String name = "name" + ".txt";
			FileOutputStream f = new FileOutputStream(new File(name));
			ObjectOutputStream o = new ObjectOutputStream(f);
			o.writeObject(this);
			o.close();
			f.close();
		}
	}
	
	public Classifier(String dataFilePath, double delta, boolean save) throws Exception {
		Dataset data = new Dataset(dataFilePath);
		this.domain = data.getDomain().clone();
		this.fDomain = data.removeLast(data.getDomain().clone());
		this.frequence = data.getFrequencies();
		ArrayList<Dataset> datasets = data.datasetInicialization();
		ArrayList<MRFT> mrfts = new ArrayList<MRFT>();
		for(Dataset d:datasets) {
			ChowLiu cLiu = new ChowLiu(d);
			mrfts.add(new MRFT(cLiu.getData(), cLiu.getGraph(), cLiu.getGraph().getEdgeList().get(0).o, delta));
		}
		this.mrfts = mrfts;
		if(save) {
			String filePath = new File("").getAbsolutePath();
			String name = filePath + dataFilePath.substring(0, dataFilePath.length() - 4) + ".txt";
			System.out.println(name);
			FileOutputStream f = new FileOutputStream(new File(name));
			ObjectOutputStream o = new ObjectOutputStream(f);
			o.writeObject(this);
			o.close();
			f.close();
		}
	}
	
	/*Classifica um dado vetor de inteiros, devolvendo a sua classe mais provável, com base nos
	 * MRFT's do classificador. Calcula a probabilidade do vetor em cada MRFT do array, verfica
	 * que MRFT é que maximiza este valor e devolve o seu índice.
	 */
	public int classify(int[] vector) throws InvalidSizeException, InvalidDomainException {
		int index = 0;
		double prob = 0;
		for(int i=0; i<this.mrfts.size(); i++) {
			if(this.mrfts.get(i).prob(this.domain, vector)*this.frequence[i] > prob) {
				index = i;
				prob = this.mrfts.get(i).prob(this.domain, vector)*this.frequence[i];
			}
		}
		return index;
	}
	
	public double[] getAccuracyBin(Dataset data) throws InvalidSizeException, InvalidDomainException {
		double TP = 0;
		double TN = 0;
		double FP = 0;
		double FN = 0;
		for(int[] i:data.getValues()) {
			int pred = this.classify(data.removeLast(i));
			int real = i[data.getDim()-1];
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
		double accuracy = (TP + TN)/(TP + TN + FP + FN);
		double TPR = (TP)/(TP+FN);
		double TNR = (TN)/(TN + FP);
		double pt = (Math.sqrt(TPR*(-TNR+1)) + TNR -1)/(TPR + TNR - 1);
		double F1 = 2*TP/(2*TP + FP + FN);
		double[] res = new double[] {accuracy, TPR, pt, F1};
		return res;
	}
}
