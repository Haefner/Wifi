package Implementierung;

import java.util.List;

public class Util {

public static double EuclidianDistance(List<Double> array1, List<Double> array2) {
		
		if (array1 == null || array2 == null || array1.size() != array2.size()) {
			throw new RuntimeException("Die Arrays m�ssen gleich gro� sein, um die Euklidische Distanz zu berechnen");
		}

		double calculated = 0;
		for (int i = 0; i < array1.size(); i++) {
			calculated = calculated + Math.pow(array1.get(i) - array2.get(i), 2);
		}
		return Math.sqrt(calculated);

	}

/**
 * Berechnet euklidische Distanz zwischen zwei Punkten (x1,y1) und (x2,y2)
 * @param x1 
 * @param x2
 * @param y1
 * @param y2
 * @return
 */
public static double euclidianDistance(double x1,double x2,double y1,double y2 ) {

		return Math.sqrt(Math.pow(x1-x2, 2)+Math.pow(y1, y2));
	}
}
