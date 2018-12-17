package Diagramme;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.pi4.locutil.GeoPosition;

import Implementierung.Empirical_FP_KNN;
import Implementierung.Model_FP_KNN;
import Implementierung.Score_NN;

public class MedianKBasiertModell {
	public void erzeugeDiagram() {
		HashMap<Double, Double> kFehler = new HashMap<>();
		for (int k = 1; k < 9; k++) {
			System.out.println("k="+k);
			kFehler.put((double) k, berechneModell(k));
			
		}
		Diagram diagram = new Diagram("Median Genauigkeit Modell");
		diagram.addLine(kFehler, "Median");

		try {
			diagram.zeichneDiamgram("MedianModell.jpeg", "Medianer Fehler nach k", "Anzahl der Nachbarn k",
					"Fehler in Meter");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private double berechneModell(int k) {
		List<Double> fehlerK = new ArrayList<>();
		Model_FP_KNN mFPKNN = new Model_FP_KNN();
		Score_NN score_NN = new Score_NN();
		HashMap<GeoPosition, GeoPosition> map = mFPKNN.berechneModel_FP_KNN(k);


		
		for(int i=0; i<10;i++) {
			
			map= mFPKNN.berechneModel_FP_KNN(k);
			
			List<Double> fehler= Score_NN.berechneFehler(map);
			List<Double> sortierterFehler=score_NN.sortiereFehlerList(fehler);
			int mitte =sortierterFehler.size()/2;
			fehlerK.add(sortierterFehler.get(mitte));
			}
			List<Double> sortierterFehlerK= score_NN.sortiereFehlerList(fehlerK);
			int mitteK =sortierterFehlerK.size()/2;
			return sortierterFehlerK.get(mitteK);

	}
}
