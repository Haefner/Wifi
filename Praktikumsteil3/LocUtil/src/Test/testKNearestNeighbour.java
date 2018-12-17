package Test;

import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.pi4.locutil.GeoPosition;
import org.pi4.locutil.MACAddress;
import org.pi4.locutil.trace.SignalStrengthSamples;
import org.pi4.locutil.trace.TraceEntry;

import Implementierung.KNearestNeighbour;
import junit.framework.Assert;

public class testKNearestNeighbour {

	@Test
	public void test() {
		List<Double> naechsteNachbarnDistanz = new ArrayList<Double>();
		naechsteNachbarnDistanz.add(5.0);
		naechsteNachbarnDistanz.add(1.0);
		naechsteNachbarnDistanz.add(7.0);
		naechsteNachbarnDistanz.add(2.0);
		naechsteNachbarnDistanz.add(6.0);

		Double groessterGemerkteDistanz = null;
		for (Double eintragVergleich : naechsteNachbarnDistanz) {
			// Erster durchlauf
			if (groessterGemerkteDistanz == null) {
				groessterGemerkteDistanz = eintragVergleich;
				continue;
			}
			// Wenn die zu vergleichende Distanz groesser als, die gemerkte Distanz ist,
			// merke dir den zu vergleichenden Wert als groessten Wert
			if (groessterGemerkteDistanz < eintragVergleich) {
				groessterGemerkteDistanz = eintragVergleich;
			}

		}

		assertTrue(groessterGemerkteDistanz.equals(7.0));
	}

	@Test
	public void testNearestNeighbour() {
		KNearestNeighbour kNearestNeighbour = new KNearestNeighbour();
		// Mac empfänger
		MACAddress mac = MACAddress.parse("00:02:2D:21:0F:33");
		// online
		SignalStrengthSamples samples = new SignalStrengthSamples();
		MACAddress mac1 = MACAddress.parse("01:02:2D:21:0F:33");
		MACAddress mac2 = MACAddress.parse("02:02:2D:21:0F:33");
		MACAddress mac3 = MACAddress.parse("03:02:2D:21:0F:33");
		MACAddress mac4 = MACAddress.parse("04:02:2D:21:0F:33");
		samples.put(mac1, -5.0);
		samples.put(mac2, -3.0);
		samples.put(mac3, -2.0);
		samples.put(mac4, -1.0);
		TraceEntry online = new TraceEntry(1L, new GeoPosition(-23.5, -10.75), 1.0, mac, samples);
		List<TraceEntry> offlineTraces = new ArrayList<>();

		// Offline3
		SignalStrengthSamples samples3 = new SignalStrengthSamples();
		samples3.put(mac1, -26.0);
		samples3.put(mac2, -23.0);
		samples3.put(mac3, -24.0);
		samples3.put(mac4, -21.0);
		TraceEntry of3 = new TraceEntry(1L, new GeoPosition(-50.5, -10.75), 1.0, mac, samples3);
		offlineTraces.add(of3);

		// Offline1
		SignalStrengthSamples samples1 = new SignalStrengthSamples();
		samples1.put(mac1, -6.0);
		samples1.put(mac2, -3.0);
		samples1.put(mac3, -4.0);
		samples1.put(mac4, -1.0);
		TraceEntry of1 = new TraceEntry(1L, new GeoPosition(-20.5, -10.75), 1.0, mac, samples1);
		offlineTraces.add(of1);

		// Offline3
		SignalStrengthSamples samples4 = new SignalStrengthSamples();
		samples4.put(mac1, -36.0);
		samples4.put(mac2, -33.0);
		samples4.put(mac3, -34.0);
		samples4.put(mac4, -31.0);
		TraceEntry of4 = new TraceEntry(1L, new GeoPosition(-70.5, -10.75), 1.0, mac, samples4);
		offlineTraces.add(of4);

		// Offline2
		SignalStrengthSamples samples2 = new SignalStrengthSamples();
		samples2.put(mac1, -16.0);
		samples2.put(mac2, -13.0);
		samples2.put(mac3, -14.0);
		samples2.put(mac4, -11.0);
		TraceEntry of2 = new TraceEntry(1L, new GeoPosition(-35.5, -20.75), 1.0, mac, samples2);
		offlineTraces.add(of2);

		Set<TraceEntry> ergebnis = kNearestNeighbour.whoAreTheKNearestNeigbours(online, offlineTraces, 3);
		Assert.assertTrue(ergebnis.contains(of3));
		Assert.assertTrue(ergebnis.contains(of2));
		Assert.assertTrue(ergebnis.contains(of1));
		Assert.assertFalse(ergebnis.contains(of4));
	}
}
