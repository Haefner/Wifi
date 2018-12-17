package Implementierung;

import java.io.IOException;
import java.util.HashMap;

import Diagramme.Diagram;
import Diagramme.MedianKBasiertEmpirisch;
import Diagramme.MedianKBasiertModell;

public class Main {
	
	public static void main(String[] args) {
	/*	Empirical_FP_NN eFPNN = new Empirical_FP_NN();
		eFPNN.berechneEmpiricalFP_NN();
		
		Empirical_FP_KNN eFPKNN = new Empirical_FP_KNN();
		eFPKNN.berechneEmpiricalFP_KNN(3);
		
		Model_FP_NN mFPNN = new Model_FP_NN();
		mFPNN.berechneModel_FP_NN();
		
		Model_FP_KNN mFPKNN = new Model_FP_KNN();
		mFPKNN.berechneModel_FP_KNN(3);
		
		
		Diagram diagram= new Diagram("testDiagram");
		HashMap<Double, Double> punkte1 = new HashMap<>();
		punkte1.put(1.0, 1.0);
		punkte1.put(2.0, 2.0);
		punkte1.put(3.0, 3.0);
		punkte1.put(4.0, 4.0);
		punkte1.put(5.0, 5.0);
		diagram.addLine(punkte1, "Messung1");
		HashMap<Double, Double> punkte2 = new HashMap<>();
		punkte2.put(1.0, 5.0);
		punkte2.put(2.0, 2.0);
		punkte2.put(3.0, 4.0);
		punkte2.put(4.0, 1.0);
		punkte2.put(5.0, 5.0);
		diagram.addLine(punkte2, "Messung1");
		try {
			diagram.zeichneDiamgram("test.jpeg", "WIFI-Positionierungsfehler", "Fehler in Meter",
					"Prozentualer Fehler");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		
		/*MedianKBasiertEmpirisch medianKBasiertEmpirisch= new MedianKBasiertEmpirisch();
		medianKBasiertEmpirisch.erzeugeDiagram();*/
		
		MedianKBasiertModell medianKBasiertModell = new MedianKBasiertModell();
		medianKBasiertModell.erzeugeDiagram();
		
	}

}
