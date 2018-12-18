package Implementierung;

import java.io.IOException;
import java.util.HashMap;

import Diagramme.CDF;
import Diagramme.Diagram;
import Diagramme.MedianKBasiertEmpirisch;
import Diagramme.MedianKBasiertModell;

public class Main {
	
	public static void main(String[] args) {
		
		CDF cdf = new CDF();
		cdf.erstelleCDF();

		
		
		/*MedianKBasiertEmpirisch medianKBasiertEmpirisch= new MedianKBasiertEmpirisch();
		medianKBasiertEmpirisch.erzeugeDiagram();*/
		
		MedianKBasiertModell medianKBasiertModell = new MedianKBasiertModell();
		medianKBasiertModell.erzeugeDiagram();
		
	}

}
