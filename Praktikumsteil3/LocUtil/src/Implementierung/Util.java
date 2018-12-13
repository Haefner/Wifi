package Implementierung;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.List;

import org.pi4.locutil.GeoPosition;
import org.pi4.locutil.MACAddress;
import org.pi4.locutil.trace.SignalStrengthSamples;
import org.pi4.locutil.trace.TraceEntry;

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

		return Math.sqrt(Math.pow(x1-x2, 2)+Math.pow(y1-y2,2));
}

public static void writeTraces(String outfile,List<TraceEntry> traces) throws IOException {
	PrintStream out = new PrintStream(outfile);
	
	// Iterate through the offline set.
	for (int i = 0; i < traces.size(); i++) {
		TraceEntry te = traces.get(i);
		String line = "t=" + te.getTimestamp() + ";id=" + te.getId() + ";pos=";
		GeoPosition gp = te.getGeoPosition();
		line += gp.getX() + "," + gp.getY() + "," + gp.getZ() + ";degree=" + te.getGeoPosition().getOrientation();
		SignalStrengthSamples samples = te.getSignalStrengthSamples();
		Iterator<MACAddress> sampleIterator = samples.keySet().iterator();
		while (sampleIterator.hasNext()) {
			MACAddress macAddress = sampleIterator.next();
			line += ";" + macAddress.toString() + "=" + samples.getFirstSignalStrength(macAddress) + "," + samples.getChannel(macAddress);
		}
		out.println(line);
		out.flush();
	}
	// Close the output file.
	out.close();
}
}

