package Diagramme;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;

import javafx.scene.chart.NumberAxis;

/**
 * Siehe Anleitung in der Datei "Wifi/LibFuerDiagram/Libraries hinzufügen"
 * https://www.tutorialspoint.com/jfreechart/jfreechart_line_chart.htm
 * 
 * @author haefn
 *
 */
public class Diagram extends ApplicationFrame {

	XYSeriesCollection dataset;

	public Diagram(String title) {
		super(title);
		dataset = new XYSeriesCollection();
	}

	/**
	 * Fügt die Daten für das Diagramm hinzu
	 * 
	 * @param punkte einer Linie, die gezeichnet werden soll
	 * @param name   name der Linie
	 */
	public void addLine(HashMap<Double, Double> punkte, String name) {
		// Die Punkte die verbunden sind
		XYSeries diagrammlinie = new XYSeries(name);
		for (Entry<Double, Double> punkt : punkte.entrySet()) {
			diagrammlinie.add(punkt.getKey(), punkt.getValue());
		}
		dataset.addSeries(diagrammlinie);
	}

	/**
	 * Zeichnet die zuvor hinzugefügten Linien in ein Diagram und schreibt das
	 * Diagramm in eine Datei
	 * 
	 * @param dateiname
	 * @throws IOException
	 */
	public void zeichneDiamgram(String dateiname, String diagramtitel, String nameXAchse, String nameYAchse) throws IOException {
		JFreeChart chart = ChartFactory.createXYLineChart("WIFI-Positionierungsfehler", "Fehler in Meter",
				"Prozentualer Fehler", dataset, PlotOrientation.VERTICAL, true, true, false);

		File file = new File(dateiname);
		ChartUtilities.saveChartAsJPEG(file, chart, 800, 800);

	}

}
