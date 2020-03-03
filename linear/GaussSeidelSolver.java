package linear;

import java.util.Arrays;

import matrix.CRSMatrix;

/**
 * Questa classe rappresenta il metodo iterativo di Gauss-Seidel per risolvere i sistemi lineari.
 * Per maggiori informazioni sul <a href="http://mathworld.wolfram.com/Gauss-SeidelMethod.html">metodo. </a>
 * 
 * @author marcotelle
 * @version 1.0
 * @since 2019-09-20
 */
public class GaussSeidelSolver extends AbstractSolver implements LinearSystemSolver {

	public static final int MAX_ITERATIONS = 100;
	
	/**
	 * Metodo costruttore che utilizza quello della superclasse astratta AbstractSolver.
	 * @param A La matrice da allocare.
	 */
	public GaussSeidelSolver(CRSMatrix A) {				
		super(A);
	}
		
	
	/**
	 * Metodo che risolve un sistema lineare A*x=b col metodo iterativo di Gauss-Seidel.
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
		Arrays.fill(x, 0);
		Arrays.fill(xOld, 0);
		while (err>epsilon && iterations <= MAX_ITERATIONS) {
			
			for (int i=0; i<n; i++) {
				sum = 0;
				for (int j=0; j<i; j++)
					sum += A.getElement(i, j)*x[j];
				for (int j=i+1; j<n; j++)
					sum += A.getElement(i, j)*xOld[j];
				x[i] = (b[i]-sum)/A.getElement(i, i);
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