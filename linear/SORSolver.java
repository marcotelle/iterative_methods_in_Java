package linear;

import java.util.Arrays;

import matrix.CRSMatrix;

/**
 * Questa classe rappresenta il metodo iterativo del sovrarilassamento per risolvere i sistemi lineari.
 * Per maggiori informazioni sul <a href="http://mathworld.wolfram.com/SuccessiveOverrelaxationMethod.html">metodo. </a>
 * 
 * @author marcotelle
 * @version 1.0
 * @since 2019-09-20
 */
public class SORSolver extends AbstractSolver implements LinearSystemSolver {
	
	public static final int MAX_ITERATIONS = 100;
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
	public double[] Solver(double[] b) {
		// Se la matrice non è predominante diagonale non è assicurata la convergenza
		if (!DiagonallyDominant())
			System.err.println("La soluzione potrebbe non convergere!");
		
		int iterations = 0;
		int n = A.getLength();
		double epsilon = 1e-15;
		double err = Double.POSITIVE_INFINITY;
		double sum;
		double[] x = new double[n];
		double[] xOld = new double[n];
		Arrays.fill(x,  0);
		Arrays.fill(xOld, 0);

		while (err>epsilon && iterations <= MAX_ITERATIONS) {			
			for (int i=0; i<n; i++) {
				sum = 0;
				for (int j=0; j<i; j++)
					sum += A.getElement(i, j)*x[j];
				for (int j=i+1; j<n; j++)
					sum += A.getElement(i, j)*xOld[j];
				x[i] = ((1-w)*xOld[i]) + (w*(b[i]-sum)/A.getElement(i, i));
			}
			iterations++;
			err = norm(subtract(x,xOld));
			
			/*
			for (int i=0; i<n; i++)
				xOld[i] = x[i];
			*/
			xOld = Arrays.copyOf(x, x.length);
		}
		System.out.println("Numero di iterazioni: "+iterations);
		return x;
	}
		
}