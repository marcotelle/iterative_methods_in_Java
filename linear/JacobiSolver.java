package linear;

import java.util.Arrays;

import matrix.CRSMatrix;

/**
 * Questa classe rappresenta il metodo iterativo di Jacobi per risolvere i sistemi lineari.
 * Per maggiori informazioni sul <a href="http://mathworld.wolfram.com/JacobiMethod.html">metodo. </a>
 * 
 * @author marcotelle
 * @since 2020-02-27
 */
public class JacobiSolver extends AbstractSolver implements LinearSystemSolver {

	public static final int MAX_ITERATIONS = 200;
	
	/**
	 * Metodo costruttore che utilizza quello della superclasse astratta AbstractSolver.
	 * @param A La matrice da allocare.
	 */
	public JacobiSolver(CRSMatrix A) {
		super(A);
	}

	
	/**
	 * Metodo che risolve un sistema lineare A*x=b col metodo iterativo di Jacobi.
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
				for (int j=0; j<n; j++) {
					if (i!=j) {
						elem = A.getElement(i, j);
						if ((elem) != 0)
							sum += elem*xOld[j];
					}
				}
				*/
				sum = A.getSummation(xOld, i);
				x[i] = (b[i]-sum)/A.getElement(i, i);
			}
			iterations++;
			err = norm(subtract(x, xOld));
			
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
