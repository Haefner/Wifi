package Implementierung;

import java.util.ArrayList;
import java.util.List;

import org.pi4.locutil.MACAddress;
import org.pi4.locutil.trace.SignalStrengthSamples;
import org.pi4.locutil.trace.TraceEntry;

public class Model {
	private List<TraceEntry> generatedTraces;
	private List<AccessPoint> accessPoints;
	private List<TraceEntry> offlineTraces;

	public Model(List<TraceEntry> offlineTraces) {

		this.offlineTraces = offlineTraces;
		generatedTraces = new ArrayList<>();
		accessPoints = new ArrayList<>();
	
		accessPoints.add(new AccessPoint("00:14:BF:B1:7C:54", 23.626, -18.596, 0.0));
	    accessPoints.add(new AccessPoint("00:16:B6:B7:5D:8F", -10.702, -18.596, 0.0));
		accessPoints.add(new AccessPoint("00:14:BF:B1:7C:57", 8.596, -14.62, 0.0));
		accessPoints.add(new AccessPoint("00:14:BF:B1:97:8D", 8.538, -9.298, 0.0));
		accessPoints.add(new AccessPoint("00:16:B6:B7:5D:9B", -1.93, -2.749, 0.0));
		accessPoints.add(new AccessPoint("00:14:6C:62:CA:A4", 4.035, -0.468, 0.0));
		accessPoints.add(new AccessPoint("00:14:BF:3B:C7:C6", 13.333, -2.69, 0.0));
		accessPoints.add(new AccessPoint("00:14:BF:B1:97:8A", 21.17, -2.69, 0.0));
		accessPoints.add(new AccessPoint("00:14:BF:B1:97:81", 32.398, -2.69, 0.0));
		accessPoints.add(new AccessPoint("00:16:B6:B7:5D:8C", 32.573, 13.86, 0.0));
		accessPoints.add(new AccessPoint("00:11:88:28:5E:E0", 7.135, 6.023, 0.0));
	    
	}

	public List<TraceEntry> generateTraces(double d0, double n, double Pd0) {

		double Pd;
		double distance;
		TraceEntry tr;
		MACAddress dummyMac = MACAddress.parse("00:00:00:00:00:00");

		//Berechnen der Signalstärken zu jedem Punkt.
		for (TraceEntry traceEntry : offlineTraces) {
			
			SignalStrengthSamples sSample = new SignalStrengthSamples();
			tr = new TraceEntry(traceEntry.getTimestamp(), traceEntry.getGeoPosition(),dummyMac,sSample);
			
			for (AccessPoint ap : accessPoints) {

				distance = Util.euclidianDistance(ap.getPosition().getX(), traceEntry.getGeoPosition().getX(),
						ap.getPosition().getY(), traceEntry.getGeoPosition().getY());

				Pd = Pd0 - (10.0 *n* Math.log10(distance / d0));//WAF wird ignoriert.
				
				sSample.put(ap.getAddress(), Pd);

				 
				
			}
			generatedTraces.add(tr);
		}
		return generatedTraces;
	}
}
