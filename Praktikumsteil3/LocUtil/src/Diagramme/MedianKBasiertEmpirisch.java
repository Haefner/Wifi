package Diagramme;

import java.io.IOException;
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
	Empirical_FP_KNN eFPKNN = new Empirical_FP_KNN();
	Score_NN score= new Score_NN();
	HashMap<GeoPosition, GeoPosition>  map= eFPKNN.berechneEmpiricalFP_KNN(k);
	
	List<Double> fehler= score.berechneFehler(map);
	List<Double> sortierterFehler=score.sortiereFehlerList(fehler);
	int mitte =sortierterFehler.size()/2;
	return sortierterFehler.get(mitte);
	
	}
	
}
