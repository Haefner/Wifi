package Implementierung;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.pi4.locutil.GeoPosition;

public class Score_NN {

	HashMap<GeoPosition, GeoPosition> exakteGemittelteGeopositionSortiert = new HashMap<>();
	
	public static void main(String[] args) {
		Empirical_FP_NN eFPNN = new Empirical_FP_NN();
		eFPNN.berechneEmpiricalFP_NN();
		List<Double> fehlerList = new ArrayList<>();
		fehlerList = berechneFehler(eFPNN.berechneEmpiricalFP_NN());
		sortiereFehlerList(fehlerList);
		erzeugeWahrscheinlichkeitsList(fehlerList.size());

	}
	
	/**
	 * 
	 * @param
	 * @return 
	 */
	public static List berechneFehler(HashMap<GeoPosition, GeoPosition> map) {
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
	 * @param 
	 * @return 
	 */
	public static List sortiereFehlerList(List list) {
		Collections.sort(list);
		for(int j=0; j<list.size();j++) {
			System.out.println("fehler in List: " + list.get(j) + "\t\t" + "Position : " + j);
		}
		return list;
	}
	
	/**
	 * Erzeugt eine Liste, die in Abh�ngigkeit der Anzahl von den exakten Positionen und somit auch der Anzahl
	 * der gemittelten Positionen die einzelnen Stufen der Wahrscheinlichkeitswerte von 0 bis 1 enth�lt
	 * 
	 * @param gr��e der geweiligen Liste
	 * @return Liste, die die Stufen der Wahrscheinlichkeitswerte von 0 bis 1 enth�lt
	 */
	public static List erzeugeWahrscheinlichkeitsList(int size) {
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

