package Implementierung;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.pi4.locutil.GeoPosition;
import org.pi4.locutil.io.TraceGenerator;
import org.pi4.locutil.trace.Parser;
import org.pi4.locutil.trace.TraceEntry;

public class Model_FP_NN {
	
	private List<TraceEntry> offlineTrace;
	private List<TraceEntry> onlineTrace;
	private List<TraceEntry> generatedTrace;
	
	public HashMap<GeoPosition, GeoPosition> berechneModel_FP_NN() {
		
		getTraces();
		Model model = new Model(offlineTrace);
		generatedTrace = model.generateTraces(1.0, 3.415, -33.77);
		
		KNearestNeighbour k_NearestNeighbour = new KNearestNeighbour();
		HashMap<GeoPosition, GeoPosition> exakteGemittelteGeoposition = new HashMap<>();
		for (TraceEntry onlineFp : onlineTrace) {
			Set<TraceEntry> naechsteNachbarn = k_NearestNeighbour.whoAreTheKNearestNeigbours(onlineFp,
					generatedTrace, 1);
			GeoPosition gemitteltePosition = k_NearestNeighbour.getAvaragePositionOfNeighbours(
					naechsteNachbarn);
			//System.out.println(gemitteltePosition);
			exakteGemittelteGeoposition.put(onlineFp.getGeoPosition(), gemitteltePosition);
		}
		

		
		System.out.println("Model Traces exportieren.");
		try {
			Util.writeTraces("model",generatedTrace);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
		return exakteGemittelteGeoposition;
		
		
		
		
	}
	
	
	private void getTraces() {
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
			int offlineSize = 25;
			int onlineSize = 5;
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
}
