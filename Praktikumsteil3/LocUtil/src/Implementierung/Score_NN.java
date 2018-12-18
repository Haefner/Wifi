package Implementierung;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.pi4.locutil.GeoPosition;

import Diagramme.Diagram;

/**
 * Zur Berechnung der Fehler zwischen der Position des exakten Fingerprints und
 * der Position des gemittelten Fingerprints
 * 
 * @author nappenfeld
 *
 */
public class Score_NN {

	public Score_NN(){
		
	}
	
	/**
	 * 
	 * @param HashMap mit Key als die zu einem FIngerprint gehörige exaktePosition
	 *        und als Value die gemittelte Position 
	 * @return Liste, die die berechneten Fehler zwischen der Position des exakten Fingerprint 
	 * 		   und der Position des gemittelten Fingerprint enthält
	 */

	public static List<Double> berechneFehler(HashMap<GeoPosition, GeoPosition> map) {

		List<Double> fehlerList = new ArrayList<>();
		for(Map.Entry<GeoPosition,GeoPosition> positionen: map.entrySet()) {
            double fehler = positionen.getKey().distance(positionen.getValue());
            fehlerList.add(fehler);
            System.out.println("fehler in List: " + fehlerList.get(fehlerList.size()-1) + "\t\t" + "Position 1: " + positionen.getKey()
            + "\t\t" + "Position 2: " + positionen.getValue());
        }
		return fehlerList;
	}
	
	/**
	 * 
	 * @param Liste, die sortiert werden soll
	 * @return sortierte Liste
	 */
	public List sortiereFehlerList(List list) {
		Collections.sort(list);
//		for(int j=0; j<list.size();j++) {
//			System.out.println("fehler in List: " + list.get(j) + "\t\t" + "Position : " + j);
//		}
		return list;
	}
	
	/**
	 * Erzeugt eine Liste, die in Abhängigkeit der Anzahl von den exakten Positionen und somit auch der Anzahl
	 * der gemittelten Positionen die einzelnen Stufen der Wahrscheinlichkeitswerte von 0 bis 1 enthält
	 * 
	 * @param größe der jeweiligen Liste
	 * @return Liste, die die Stufen der Wahrscheinlichkeitswerte von 0 bis 1 enthält
	 */
	public List erzeugeWahrscheinlichkeitsList(int size) {
		List<Double> wahrscheinlichkeitsList = new ArrayList<>();
		double aufloesung = 1.0/(double)size;
		System.out.println("aufloesung " + aufloesung);
		for(int i=0; i<size; i++) {
			wahrscheinlichkeitsList.add(i,(i+1)*aufloesung);
		}
//		for(int j=0; j<wahrscheinlichkeitsList.size();j++) {
//			System.out.println("Wahrscheinlichkeit: " + wahrscheinlichkeitsList.get(j) + "\t\t" + "Position : " + j);
//		}
		return wahrscheinlichkeitsList;
	}
	
		
}

