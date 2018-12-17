package Diagramme;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.pi4.locutil.GeoPosition;

import Implementierung.Empirical_FP_KNN;
import Implementierung.Score_NN;

public class MedianKBasiertEmpirisch {

	
	public void erzeugeDiagram()
	{
		HashMap<Double, Double> kFehler= new HashMap<>();
		for(int k=1;k<9;k++)
		{
			kFehler.put((double)k, berechneEmpirisch(k));
			System.out.println("kEmpirisch berechnet mit k = " +k);
		}
		Diagram diagram= new Diagram("Median Genauigkeit Empirisch");
		diagram.addLine(kFehler,"Median");
		
		try {
			diagram.zeichneDiamgram("MedianEmpirisch.jpeg", "Empirischer Medianer Fehler nach k", "Anzahl der Nachbarn k",
					"Fehler in Meter");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	private double berechneEmpirisch(int k) {
	List<Double> fehlerK = new ArrayList<>();
	Score_NN score_NN = new Score_NN();
	for(int i=0; i<10;i++) {
	Empirical_FP_KNN eFPKNN = new Empirical_FP_KNN();
	HashMap<GeoPosition, GeoPosition>  map= eFPKNN.berechneEmpiricalFP_KNN(k);
	
	List<Double> fehler= Score_NN.berechneFehler(map);
	List<Double> sortierterFehler=score_NN.sortiereFehlerList(fehler);
	int mitte =sortierterFehler.size()/2;
	fehlerK.add(sortierterFehler.get(mitte));
	}
	List<Double> sortierterFehlerK= score_NN.sortiereFehlerList(fehlerK);
	int mitteK =sortierterFehlerK.size()/2;
	System.out.println("Median Fehler:"+sortierterFehlerK.get(mitteK));
	return sortierterFehlerK.get(mitteK);
	
	
	}
	
}
