package Implementierung;

import java.io.IOException;
import java.util.HashMap;

import Diagramme.CDF;
import Diagramme.Diagram;
import Diagramme.MedianKBasiertEmpirisch;
import Diagramme.MedianKBasiertModell;

public class Main {
	
	public static void main(String[] args) {
		Empirical_FP_NN eFPNN = new Empirical_FP_NN();
		eFPNN.berechneEmpiricalFP_NN();
		
		Empirical_FP_KNN eFPKNN = new Empirical_FP_KNN();
		eFPKNN.berechneEmpiricalFP_KNN(3);
		
		Model_FP_NN mFPNN = new Model_FP_NN();
		mFPNN.berechneModel_FP_NN();
		
		Model_FP_KNN mFPKNN = new Model_FP_KNN();
		mFPKNN.berechneModel_FP_KNN(3);
		
		CDF cdf = new CDF();
		cdf.erstelleCDF();

		
		
		/*MedianKBasiertEmpirisch medianKBasiertEmpirisch= new MedianKBasiertEmpirisch();
		medianKBasiertEmpirisch.erzeugeDiagram();*/
		
		MedianKBasiertModell medianKBasiertModell = new MedianKBasiertModell();
		medianKBasiertModell.erzeugeDiagram();
		
	}

}
