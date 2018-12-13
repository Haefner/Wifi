package Implementierung;

public class Main {
	
	public static void main(String[] args) {
		Empirical_FP_NN eFPNN = new Empirical_FP_NN();
		eFPNN.berechneEmpiricalFP_NN();
		
		Empirical_FP_KNN eFPKNN = new Empirical_FP_KNN();
		eFPKNN.berechneEmpiricalFP_KNN(3);
		
	}

}
