package Implementierung;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

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
public class KNearestNeighbour {

	/**
	 * Ermittle aus dem zuvor gesammelten Fingerprint und den gemessenen
	 * Fingerprints, über die Euklidische Distanz, die k-Nächsten Nachbarn
	 * 
	 * Gesammelter Fingerpring beispielsweise E1 (1,1,1,-25,-35,-55) (X-Koordinate,
	 * y-Koordinate, z-Koordinate, Empfangsstärke ACP1, Empfangsstärke ACP2,
	 * Empfangsstärke ACP3)
	 * 
	 * @param messpunkt      Messpunkt mit dem verglichen werden soll
	 * @param offlineTraces  Alle zu vergleichenden Messpunkte. 
	 * @param anzahlNachbarn Die Anzahl der k-nächsten Nachbarn, die am nächsten zur
	 *                       gemessenen Position sind
	 * @return Die k-Nachbarn, die am nächsten zur gemessenen Position sind
	 */
	public Set<TraceEntry> whoAreTheKNearestNeigbours(TraceEntry messpunkt, List<TraceEntry> offlineTraces,
			int anzahlNachbarn) {

		// Map, die zu den k naechsten Nachbarn die Distanz speichert
		HashMap<TraceEntry, Double> naechsteNachbarnDistanz = new HashMap<>();
		for (TraceEntry nachbar : offlineTraces) {
			double distanz = getDistanzeOfSignal(messpunkt, nachbar);
			naechsteNachbarnDistanz = sollWertInListe(nachbar, distanz, naechsteNachbarnDistanz, anzahlNachbarn);
		}
		return naechsteNachbarnDistanz.keySet();

	}

	/**
	 * Berechnet die Distanz der Signalstärke zu den Routern
	 * 
	 * Formel der Euklidischen Distanz dist=
	 * sqrt((pow(gemesseneSignalstärkeZumRouter1-gesammelteOfflineSignalstarkeZumRouter1),2)
	 * +
	 * pow(gemesseneSignalstärkeZumRouter2-gesammelteOfflineSignalstarkeZumRouter2),2)+...)
	 */
	private double getDistanzeOfSignal(TraceEntry onlineFingerprint, TraceEntry offlineFingerprint) {

		SignalStrengthSamples offlineSignale = offlineFingerprint.getSignalStrengthSamples();
		SignalStrengthSamples onlineSignale = onlineFingerprint.getSignalStrengthSamples();

		// Ermittel zunächst alle online Mac-Adressen, um zu den passenden Macadressen,
		// anschließend die Singalstärke eines Fingerprints abfragen zu können
		List<MACAddress> alleMacAdressenOnline = onlineSignale.getSortedAccessPoints();
		List<MACAddress> alleMacAdressenOffline = offlineSignale.getSortedAccessPoints();
		// TODO muss ich auch alle MacAdressen der Offline Router nehmen

		List<Double> signalStaerkenOffline = new ArrayList<>();
		List<Double> signalStaerkenOnline = new ArrayList<>();
		for (MACAddress adresse : alleMacAdressenOnline) {
			// Prüfe erst, ob die MacAdresse überhaupt exisitert.
			if (!alleMacAdressenOffline.contains(adresse)) {
				continue;
			}
			// Innerhalb des Fingerprintes existieren zu jeder MacAdresse eines Routers,
			// mehrere Signalstärken
			// Daher arbeitet man mit den Durchnittswert, der Empfangen wurde
			signalStaerkenOffline.add(offlineSignale.getAverageSignalStrength(adresse));
			signalStaerkenOnline.add(onlineSignale.getAverageSignalStrength(adresse));

			// FIXME, was ist, wenn zu einer Macadresse kein Signal empfangen wurde
		}

		return EuclidianDistance(signalStaerkenOnline, signalStaerkenOffline);
	}

	private double EuclidianDistance(List<Double> array1, List<Double> array2) {

		if (array1 == null || array2 == null || array1.size() != array2.size()) {
			throw new RuntimeException("Die Arrays müssen gleich groß sein, um die Euklidische Distanz zu berechnen");
		}

		double calculated = 0;
		for (int i = 0; i < array1.size(); i++) {
			calculated = calculated + Math.pow(array1.get(i) - array2.get(i), 2);
		}
		return Math.sqrt(calculated);

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
			naechsteNachbarnDistanz.remove(groessterGemerkteDistanz.getKey());
			naechsteNachbarnDistanz.put(naechsterNachbar, distanz);
		}

		return naechsteNachbarnDistanz;

	}

	/**
	 * Bestimme von den übergebenen Nachbarn den Mittelpunkt (Schwerkunkt) k=Anzahl
	 * der Übergebenen Nachbarn Koordinate X x=1/k * (X-Fingerprint1
	 * +X-Fingerprint2+ X-Fingerprint3...)
	 * 
	 * @return Die gemittelte Position, an der man sich befindet
	 */
	public GeoPosition getAvaragePositionOfNeighbours(
			Set<TraceEntry> naechsteNachbarnDistanz) {

		if ( naechsteNachbarnDistanz == null || naechsteNachbarnDistanz.size() == 0) {
			throw new RuntimeException("getAvaragePosition braucht eine Liste mit den "
					+ "nächsten Nachbarn zur durchschnitts Berechnung");
		}

		double xTotal = 0;
		double yTotal = 0;
		double zTotal = 0;

		for (TraceEntry entry : naechsteNachbarnDistanz) {

			xTotal += entry.getGeoPosition().getX();
			yTotal += entry.getGeoPosition().getY();
			zTotal += entry.getGeoPosition().getZ();
		}

		double xAvg = xTotal / naechsteNachbarnDistanz.size();
		double yAvg = yTotal / naechsteNachbarnDistanz.size();
		double zAvg = zTotal / naechsteNachbarnDistanz.size();

		return new GeoPosition(xAvg, yAvg, zAvg);

	}

}
