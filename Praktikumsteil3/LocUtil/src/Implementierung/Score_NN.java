package Implementierung;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.pi4.locutil.GeoPosition;

/**
 * Zur Berechnung der Fehler zwischen der Position des exakten Fingerprints und
 * der Position des gemittelten Fingerprints
 * 
 * @author nappenfeld
 *
 */
public class Score_NN {

	HashMap<GeoPosition, GeoPosition> exakteGemittelteGeopositionSortiert = new HashMap<>();
	
	public static void main(String[] args) {
		Empirical_FP_NN eFPNN = new Empirical_FP_NN();
		eFPNN.berechneEmpiricalFP_NN();
		List<Double> fehlerList = new ArrayList<>();
		//fehlerList = berechneFehler(eFPNN.berechneEmpiricalFP_NN());
		//sortiereFehlerList(fehlerList);
		//erzeugeWahrscheinlichkeitsList(fehlerList.size());

	}
	public Score_NN(){
		
	}
	
	/**
	 * 
	 * @param HashMap mit Key als die zu einem FIngerprint gehörige exaktePosition
	 *        und als Value die gemittelte Position 
	 * @return Liste, die die berechneten Fehler zwischen der Position des exakten Fingerprint 
	 * 		   und der Position des gemittelten Fingerprint enthält
	 */
	public List berechneFehler(HashMap<GeoPosition, GeoPosition> map) {
		List<Double> fehlerList = new ArrayList<>();
		// getting keySet() into Set
		Set<GeoPosition> set = map.keySet();
        // get Iterator from key set
        Iterator<GeoPosition> itr = set.iterator();
        int i = 0;
		while(itr.hasNext()) {
            double fehler = itr.next().distance(map.get(set.iterator().next()));
            fehlerList.add(i, fehler);
            //System.out.println("fehler : "  + fehler +"\t\t" + "Position : " + i + "\t\t" + "itr : " + fehlerList.get(i));
            i++;
        }
		for(int j=0; j<fehlerList.size();j++) {
			System.out.println("fehler in List: " + fehlerList.get(j) + "\t\t" + "Position : " + j);
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
		for(int j=0; j<list.size();j++) {
			System.out.println("fehler in List: " + list.get(j) + "\t\t" + "Position : " + j);
		}
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
		for(int j=0; j<wahrscheinlichkeitsList.size();j++) {
			System.out.println("Wahrscheinlichkeit: " + wahrscheinlichkeitsList.get(j) + "\t\t" + "Position : " + j);
		}
		return wahrscheinlichkeitsList;
	}
	
		
}

