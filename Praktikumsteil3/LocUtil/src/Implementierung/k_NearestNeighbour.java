package Implementierung;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.pi4.locutil.GeoPosition;
import org.pi4.locutil.MACAddress;
import org.pi4.locutil.trace.SignalStrengthSamples;
import org.pi4.locutil.trace.TraceEntry;


/**
 * Zur Positionsbestimmung des Empfängers, werden erst die k-Nächsten Nachbarn
 * bestimmt. Aus den k-Nächsten Nachbarn wird anschließend die Position
 * gemittelt
 * 
 * @author haefn
 *
 */
public class k_NearestNeighbour {

	/**
	 * Ermittle aus dem zuvor gesammelten Fingerprint und den gemessenen
	 * Fingerprints, über die Euklidische Distanz, die k-Nächsten Nachbarn
	 * 
	 * Gesammelter Fingerpring beispielsweise E1 (1,1,1,-25,-35,-55) (X-Koordinate,
	 * y-Koordinate, z-Koordinate, Empfangsstärke ACP1, Empfangsstärke ACP2,
	 * Empfangsstärke ACP3)
	 * @param messpunkt Messpunkt mit dem verglichen werden soll
	 * @param offlineTraces Alle zu vergleichenden Messpunkte.
	 * 						<b> Eine VORAUSWAHL muss zuvor getroffen werden </b>
	 * @param anzahlNachbarn Die Anzahl der k-nächsten Nachbarn, die am nächsten
	 *        zur gemessenen Position sind
	 * @return Die k-Nachbarn, die am nächsten zur gemessenen Position sind
	 */
	public HashMap<TraceEntry, Double> whoAreTheKNearestNeigbours(TraceEntry messpunkt,List<TraceEntry> offlineTraces, int anzahlNachbarn) {

		// Map, die zu den k naechsten Nachbarn die Distanz speichert
		HashMap<TraceEntry, Double> naechsteNachbarnDistanz = new HashMap<>();
		for (TraceEntry nachbar : offlineTraces) {
			double distanz = getDistanzeOfSignal(messpunkt, nachbar);
			naechsteNachbarnDistanz=sollWertInListe(nachbar, distanz, naechsteNachbarnDistanz, anzahlNachbarn);
		}
		return naechsteNachbarnDistanz;

	}

	/**
	 * Berechnet die Distanz der Signalstärke zu den Routern
	 * 
	 * Formel der Euklidischen Distanz dist=
	 * sqrt((pow(gemesseneSignalstärkeZumRouter1-gesammelteOfflineSignalstarkeZumRouter1),2)
	 * +
	 * pow(gemesseneSignalstärkeZumRouter2-gesammelteOfflineSignalstarkeZumRouter2),2)+...)
	 */
	private double getDistanzeOfSignal(TraceEntry messpunkt, TraceEntry offlineFingerprint) {
		//TODO
	
	    return 0;
		
	}

	/**
	 * Pruefe ob ein neuer nächster Nachbar gefunden wurde. Falls ja, entferne den
	 * Nachbarn mit der gröchsten Distanz und füge neuen Nachbarn hinzu
	 * 
	 * @return aktuallisierte Liste mit den Nächsten Nachbarn und ihrer Distanz
	 */
	private HashMap<TraceEntry, Double> sollWertInListe(TraceEntry naechsterNachbar, Double distanz,
			HashMap<TraceEntry, Double> naechsteNachbarnDistanz, int anzahlNachbarn) {

		// Liste ist noch nicht voll, packe neuen Wert hinzu
		if (naechsteNachbarnDistanz.size() < anzahlNachbarn) {
			naechsteNachbarnDistanz.put(naechsterNachbar, distanz);
			return naechsteNachbarnDistanz;
		}

		// Finde den Nachbarn mit der groessten Distanz
		Map.Entry<TraceEntry, Double> groessterGemerkteDistanz = null;
		for (Map.Entry<TraceEntry, Double> eintragVergleich : naechsteNachbarnDistanz.entrySet()) {
			// Erster durchlauf
			if (groessterGemerkteDistanz == null) {
				groessterGemerkteDistanz = eintragVergleich;
				continue;
			}
			// Wenn die zu vergleichende Distanz groesser als, die gemerkte Distanz ist,
			// merke dir den zu vergleichenden Wert als groessten Wert
			if (groessterGemerkteDistanz.getValue() < eintragVergleich.getValue()) {
				groessterGemerkteDistanz = eintragVergleich;
			}

		}

		// Neue Distanz ist kleiner als die gemerkten Werte.
		// Entferne den größten Wert und füge den neuen Wert hinzu
		if (groessterGemerkteDistanz.getValue() > distanz) {
			naechsteNachbarnDistanz.remove(groessterGemerkteDistanz);
			naechsteNachbarnDistanz.put(naechsterNachbar, distanz);
		}

		return naechsteNachbarnDistanz;

	}

	/**
	 * Bestimme von den übergebenen Nachbarn den Mittelpunkt (Schwerkunkt) k=Anzahl
	 * der Übergebenen Nachbarn Koordinate X 
	 * x=1/k * (X-Fingerprint1 +X-Fingerprint2+ X-Fingerprint3...)
	 * 
	 * @return Die gemittelte Position, an der man sich befindet
	 */
	private GeoPosition getAvaragePositionOfNeighbours(TraceEntry messpunkt,
			HashMap<TraceEntry, Double> naechsteNachbarnDistanz) {

		if (messpunkt == null || naechsteNachbarnDistanz == null || naechsteNachbarnDistanz.size() == 0) {
			throw new RuntimeException("getAvaragePosition braucht einen Messpunkt und eine Liste mit den "
					+ "nächsten Nachbarn zur durchschnittsberechnung");
		}

		double xTotal = 0;
		double yTotal = 0;
		double zTotal = 0;

		for (Map.Entry<TraceEntry, Double> entry : naechsteNachbarnDistanz.entrySet()) {

			xTotal += entry.getKey().getGeoPosition().getX();
			yTotal += entry.getKey().getGeoPosition().getY();
			zTotal += entry.getKey().getGeoPosition().getZ();
		}

		double xAvg = xTotal / naechsteNachbarnDistanz.size();
		double yAvg = yTotal / naechsteNachbarnDistanz.size();
		double zAvg = zTotal / naechsteNachbarnDistanz.size();

		return new GeoPosition(xAvg, yAvg,zAvg);

	}

}
