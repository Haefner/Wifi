package Implementierung;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.pi4.locutil.MACAddress;
import org.pi4.locutil.io.TraceGenerator;
import org.pi4.locutil.trace.Parser;
import org.pi4.locutil.trace.TraceEntry;

import Diagramme.Diagram;

public class SignalStrengthDependingDistance {

	private List<TraceEntry> offlineTrace;
	private List<TraceEntry> onlineTrace;
	private List<AccessPoint> accessPoints;
	private List<Double> distances;
	private List<Double> signalStrengths;
	private List<Point> strengthDependingDistancePoints;
	
	public static void main(String[] args) {
		
		/* Set Anzahl Messwerte für den Tracegenerator */
		int anzahlOfflineTraces = 10;
		int anzahlOnlineTraces = 0;
		/* Set AP */
		int apNr = 7; //apNr = 6: 0 Messpunkte (AP war ausgeschaltet -> hat keine Signalstärken geliefert)!
		
		SignalStrengthDependingDistance ssdd = new SignalStrengthDependingDistance();
		/* Online- und Offline-Traces erzeugen */
		ssdd.getTraces(anzahlOfflineTraces, anzahlOnlineTraces);
		
		System.out.println("offline size: " + ssdd.getOfflineTraces().size());
		System.out.println("aps size: " + ssdd.getAccessPoints().size());
		
		ssdd.berechneSignalstärkeZurEntfernung(ssdd.getAccessPoints().get(apNr - 1),ssdd.getOfflineTraces());
		ssdd.zeichneDiagramm(ssdd.getAccessPoints().get(apNr - 1));
	}
	
	

	public SignalStrengthDependingDistance() {

		distances = new ArrayList<>();
		accessPoints = new ArrayList<>();
		signalStrengths = new ArrayList<>();
		strengthDependingDistancePoints = new ArrayList<>();
	
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
	
	/**
	 * Erstellt eine Liste mit den Punkten (x: entfernung zum AP, y: Signalstärke vom AP).
	 * Zuerst wird zu jedem Messpunkt die Entfernung zum AP berechnet. Dann wird die Signalstärke zur Liste hinzugefügt
	 * 
	 * @param ap: 		AccessPoint zu dem das Diagramm erstellt wird 
	 * 		  traces:	Liste mit den Signalstärken von allen Punkten zu dem AccessPoint
	 */
	public void berechneSignalstärkeZurEntfernung(AccessPoint ap, List<TraceEntry> traces) {
		double apX = ap.getPosition().getX();
		double apY = ap.getPosition().getY();
		double d;
		double signalStrength;
		
		//System.out.println("x: " + traces.get(1).getGeoPosition().getX());
		for(TraceEntry te : traces) {
			/** berechnet Entfernung zwischen dem aktuellen Messpunkt und dem AP **/
			d = Util.euclidianDistance(apX, te.getGeoPosition().getX(), apY, te.getGeoPosition().getY());
			distances.add(d);
			//System.out.println("distance: " + d);
			
			/** Erst prüfen ob es zu dem AP eine Signalstärke gibt, anssonsten den aktuellen Messpunkt überspringen **/
			List<MACAddress> alleMacAdressenZumTrace = te.getSignalStrengthSamples().getSortedAccessPoints();
			if (!alleMacAdressenZumTrace.contains(ap.getAddress())) {
				continue;
			}
			signalStrength = te.getSignalStrengthSamples().getAverageSignalStrength(ap.getAddress());
			signalStrengths.add(signalStrength);
			//System.out.println("strength: " + signalStrength);
			
			strengthDependingDistancePoints.add(new Point(d,signalStrength));
		}
		
		//for(Point p: strengthDependingDistancePoints) {
		//	System.out.println("x: " + p.m_x + " ,y: " + p.m_y);
		//}
		System.out.println("Anzahl Messpunkte: " + strengthDependingDistancePoints.size());	
	}
	
	
	public void zeichneDiagramm(AccessPoint ap){
		Diagram diagram= new Diagram("Diagram");
		HashMap<Double, Double> points = new HashMap<>();
		for(Point p: strengthDependingDistancePoints) {
			points.put(p.m_x, p.m_y);
		}
		diagram.addLine(points, ap.getName() + ", MAC: " + ap.getAddress().toString());

		try {
			diagram.zeichnePunktDiagram("signalstrength-Distance.jpeg", "Signalstärke in Bezug zur Entfernung zum AP", "Distanz [m]",
					"Signalstärke [dBm]");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	private void getTraces(int nOffline, int nOnline) {
		String offlinePath = "data/MU.1.5meters.offline.trace", onlinePath = "data/MU.1.5meters.online.trace";

		// Construct parsers
		File offlineFile = new File(offlinePath);
		Parser offlineParser = new Parser(offlineFile);
		System.out.println("Offline File: " + offlineFile.getAbsoluteFile());

		File onlineFile = new File(onlinePath);
		Parser onlineParser = new Parser(onlineFile);
		System.out.println("Online File: " + onlineFile.getAbsoluteFile());

		// Construct trace generator
		TraceGenerator tg;

		try {
			int offlineSize = nOffline;
			int onlineSize = nOnline;
			tg = new TraceGenerator(offlineParser, onlineParser, offlineSize, onlineSize);

			// Generate traces from parsed files
			tg.generate();

			// Iterate the trace generated from the offline file
			offlineTrace = tg.getOffline();
			// Iterate the trace generated from the online file
			onlineTrace = tg.getOnline();

		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	public List<TraceEntry> getOfflineTraces(){
		return offlineTrace;
	}
	public List<TraceEntry> getOnlineTraces(){
		return onlineTrace;
	}
	public List<AccessPoint> getAccessPoints(){
		return accessPoints;
	}
	
	
	public class Point{
		public double m_x;
		public double m_y;
		
		public Point(double x, double y) {
			m_x = x;
			m_y = y;
		}
	}
	
	
}

