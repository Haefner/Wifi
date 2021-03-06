package Diagramme;

import java.awt.BasicStroke;
import java.awt.Color;
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
 * Siehe Anleitung in der Datei "Wifi/LibFuerDiagram/Libraries hinzuf�gen"
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
	 * F�gt die Daten f�r das Diagramm hinzu
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
	 * Zeichnet die zuvor hinzugef�gten Linien in ein Diagram und schreibt das
	 * Diagramm in eine Datei
	 * 
	 * @param dateiname
	 * @throws IOException
	 */
	public void zeichneDiamgram(String dateiname, String diagramtitel, String nameXAchse, String nameYAchse) throws IOException {
		JFreeChart chart = ChartFactory.createXYLineChart(diagramtitel, nameXAchse,
				nameYAchse, dataset, PlotOrientation.VERTICAL, true, true, false);

		ChartPanel chartPanel = new ChartPanel(chart);
	    final XYPlot plot = chart.getXYPlot();
	      
	    XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
	    renderer.setSeriesPaint(0, Color.RED);
	    renderer.setSeriesPaint(1, Color.GREEN);
	    renderer.setSeriesPaint(2, Color.BLUE);
	    renderer.setSeriesPaint(3, Color.BLACK);
	    renderer.setSeriesStroke(0, new BasicStroke(2.0f));
	    renderer.setSeriesStroke(1, new BasicStroke(2.0f));
	    renderer.setSeriesStroke(2, new BasicStroke(2.0f));
	    renderer.setSeriesStroke(3, new BasicStroke(2.0f));
	    plot.setRenderer(renderer); 
	    setContentPane(chartPanel); 
		File file = new File(dateiname);
		ChartUtilities.saveChartAsJPEG(file, chart, 800, 800);

	}
	
	/**
	 * Zeichnet die zuvor hinzugef�gten PUNKTE in ein Diagram und schreibt das
	 * Diagramm in eine Datei
	 * 
	 * @param dateiname
	 * @throws IOException
	 */
	public void zeichnePunktDiagram(String dateiname, String diagramtitel, String nameXAchse, String nameYAchse) throws IOException {
		JFreeChart chart = ChartFactory.createXYLineChart(diagramtitel, nameXAchse,
				nameYAchse, dataset, PlotOrientation.VERTICAL, true, true, false);

		//Daten zwar mit addline() hinzugef�gt aber mit setSeriesLinesVisible(0,false) werden die Linien nicht dargestellt
		final XYPlot plot = chart.getXYPlot();
	    final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
	    renderer.setSeriesLinesVisible(0, false);
	    renderer.setSeriesPaint(0, Color.GREEN);
	    plot.setRenderer(renderer);

		File file = new File(dateiname);
		ChartUtilities.saveChartAsJPEG(file, chart, 800, 800);
	}
	

}
