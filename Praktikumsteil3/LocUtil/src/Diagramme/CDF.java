package Diagramme;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Implementierung.Empirical_FP_KNN;
import Implementierung.Empirical_FP_NN;
import Implementierung.Model_FP_KNN;
import Implementierung.Model_FP_NN;
import Implementierung.Score_NN;

public class CDF {

	public CDF() {
		
	}
	
	public void erstelleCDF() {
		Empirical_FP_NN eFPNN = new Empirical_FP_NN();
		Empirical_FP_KNN eFPKNN = new Empirical_FP_KNN();
		Model_FP_NN mFPNN = new Model_FP_NN();
		Model_FP_KNN mFPKNN = new Model_FP_KNN();
		
		List<Double> empirical_FP_NNfehlerList = new ArrayList<>();
		List<Double> empirical_FP_NNsortierteList = new ArrayList<>();
		List<Double> empirical_FP_NNwahrscheinlichkeitsList = new ArrayList<>();
		
		List<Double> empirical_FP_KNNfehlerList = new ArrayList<>();
		List<Double> empirical_FP_KNNsortierteList = new ArrayList<>();
		List<Double> empirical_FP_KNNwahrscheinlichkeitsList = new ArrayList<>();
		
		List<Double> model_FP_NNfehlerList = new ArrayList<>();
		List<Double> model_FP_NNsortierteList = new ArrayList<>();
		List<Double> model_FP_NNwahrscheinlichkeitsList = new ArrayList<>();
		
		List<Double> model_FP_KNNfehlerList = new ArrayList<>();
		List<Double> model_FP_KNNsortierteList = new ArrayList<>();
		List<Double> model_FP_KNNwahrscheinlichkeitsList = new ArrayList<>();
		
		HashMap<Double,Double> empirical_FP_NNwerteFuerCDF = new HashMap();
		HashMap<Double,Double> empirical_FP_KNNwerteFuerCDF = new HashMap();
		HashMap<Double,Double> model_FP_NNwerteFuerCDF = new HashMap();
		HashMap<Double,Double> model_FP_KNNwerteFuerCDF = new HashMap();
		
		Score_NN score_nn = new Score_NN();
		
		
		empirical_FP_NNfehlerList = score_nn.berechneFehler(eFPNN.berechneEmpiricalFP_NN());
		empirical_FP_NNsortierteList = score_nn.sortiereFehlerList(empirical_FP_NNfehlerList);
		empirical_FP_NNwahrscheinlichkeitsList = score_nn.erzeugeWahrscheinlichkeitsList(empirical_FP_NNfehlerList.size());
		
		empirical_FP_KNNfehlerList = score_nn.berechneFehler(eFPKNN.berechneEmpiricalFP_KNN(3));
		empirical_FP_KNNsortierteList = score_nn.sortiereFehlerList(empirical_FP_KNNfehlerList);
		empirical_FP_KNNwahrscheinlichkeitsList = score_nn.erzeugeWahrscheinlichkeitsList(empirical_FP_KNNfehlerList.size());
		
		model_FP_NNfehlerList = score_nn.berechneFehler(mFPNN.berechneModel_FP_NN());
		model_FP_NNsortierteList = score_nn.sortiereFehlerList(model_FP_NNfehlerList);
		model_FP_NNwahrscheinlichkeitsList = score_nn.erzeugeWahrscheinlichkeitsList(model_FP_NNfehlerList.size());
		
		model_FP_KNNfehlerList = score_nn.berechneFehler(mFPKNN.berechneModel_FP_KNN(3));
		model_FP_KNNsortierteList = score_nn.sortiereFehlerList(model_FP_KNNfehlerList);
		model_FP_KNNwahrscheinlichkeitsList = score_nn.erzeugeWahrscheinlichkeitsList(model_FP_KNNfehlerList.size());
		
		for(int i = 0; i<empirical_FP_NNsortierteList.size();i++){
			empirical_FP_NNwerteFuerCDF.put(empirical_FP_NNsortierteList.get(i), empirical_FP_NNwahrscheinlichkeitsList.get(i));
		}
		for(int i = 0; i<empirical_FP_KNNsortierteList.size();i++){
			empirical_FP_KNNwerteFuerCDF.put(empirical_FP_KNNsortierteList.get(i), empirical_FP_KNNwahrscheinlichkeitsList.get(i));
		}
		for(int i = 0; i<model_FP_NNsortierteList.size();i++){
			model_FP_NNwerteFuerCDF.put(model_FP_NNsortierteList.get(i), model_FP_NNwahrscheinlichkeitsList.get(i));
		}
		for(int i = 0; i<model_FP_KNNsortierteList.size();i++){
			model_FP_KNNwerteFuerCDF.put(model_FP_KNNsortierteList.get(i), model_FP_KNNwahrscheinlichkeitsList.get(i));
		}
		
		Diagram diagramm = new Diagram("Test");
		diagramm.addLine(empirical_FP_NNwerteFuerCDF, "empirical NN");
		diagramm.addLine(empirical_FP_KNNwerteFuerCDF, "empirical KNN");
		diagramm.addLine(model_FP_NNwerteFuerCDF, "model NN");
		diagramm.addLine(model_FP_KNNwerteFuerCDF, "model KNN");
		try {
			diagramm.zeichneDiamgram("CDF.jpeg", "cumulative distribution function (CDF)", "Fehler in Meter",
					"Prozentualer Fehler");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
