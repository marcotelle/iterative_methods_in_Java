package linear;

import java.util.Arrays;

import matrix.CRSMatrix;

/**
 * Questa classe rappresenta il metodo iterativo del sovrarilassamento per risolvere i sistemi lineari.
 * Per maggiori informazioni sul <a href="http://mathworld.wolfram.com/SuccessiveOverrelaxationMethod.html">metodo. </a>
 * 
 * @author marcotelle
 * @since 2020-02-27
 */
public class SORSolver extends AbstractSolver implements LinearSystemSolver {
	
	public static final int MAX_ITERATIONS = 200;
	// parametro di rilassamento deve essere compreso tra 0 e 2
	private double w;
	
	
	/**
	 * Metodo costruttore della classe. Fa la copia della matrice passata in input e setta il parametro
	 * di rilassamento.
	 * @param M La matrice da allocare.
	 * @param w Il paramento di rilassamento.
	 */
	public SORSolver(CRSMatrix A, double w) {
		super(A);
		this.w = w;
	}
		
	
	/**
	 * Metodo che risolve un sistema lineare A*x=b col metodo iterativo del sovrarilassamento.
	 * @param b Array dei termini noti.
	 * @return Restituisce l'array contenente la soluzione del sistema.
	 */
	public double[] Solver(double[] b, double[] xEsatto) {

		int iterations = 0;
		int n = A.getLength();
		double epsilon = 10e-8;
		double err = Double.POSITIVE_INFINITY;
		double sum, errVero, terza;
		//double elem;
		double[] x = new double[n];
		double[] xOld = new double[n];
		for (int i=0; i<n; i++) {
			x[i]=0;
			xOld[i]=0;
		}
		
		System.out.println("it err errVero errVero/err");

		while (err>epsilon && iterations <= MAX_ITERATIONS) {	
			
			for (int i=0; i<n; i++) {
				/*
				sum = 0;
				for (int j=0; j<i; j++) {
					elem = A.getElement(i, j);
					if (elem != 0) {
						sum += A.getElement(i, j)*x[j];
					}
				}
				for (int j=i+1; j<n; j++) {
					elem = A.getElement(i, j);
					if (elem != 0) {
						sum += A.getElement(i, j)*xOld[j];
					}
				}	
				*/
				sum = A.getSummation(xOld, x, i);
				x[i] = ((1-w)*xOld[i]) + (w/A.getElement(i, i))*(b[i]-sum);
			}
			iterations++;
			err = norm(subtract(x,xOld));
			
			//System.out.println("Iterazione: "+iterations);
			//System.out.println("Differenza x^(k+1) - x^k in norma infinito: "+ err);
			errVero = norm(subtract(xEsatto, x));
			//System.out.println("Errore: "+errVero);
			terza = errVero/err;
			//System.out.println("errVero/err: "+terza);
			//System.out.println("Tol: "+epsilon);
			//System.out.println();	
	
			
			
			System.out.println(iterations+" "+err+" "+errVero+" "+terza);
			
			xOld = Arrays.copyOf(x, x.length);
		}
		System.out.println("Numero di iterazioni: "+iterations);
		return x;
	}
		
}
