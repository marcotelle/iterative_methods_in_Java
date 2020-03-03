package linear;

import matrix.CRSMatrix;

/**
 * La classe astratta AbstractSolver implementa i metodi di un risolutore di sistemi lineari
 * 
 * @author marcotelle
 * @version 1.0
 * @since 2019-09-20
 */
public abstract class AbstractSolver implements LinearSystemSolver {

	protected CRSMatrix A;
		
	/**
	 * Metodo costruttore della classe. Fa la copia della matrice passata in input.
	 * @param M La matrice da allocare.
	 */
	protected AbstractSolver(CRSMatrix M) {
		// Faccio la copia della matrice
		A = M.copy();
	}
	

	/**
	 * Metodo che controlla se la matrice è predominante diagonale per righe.
	 * @return true se la matrice è predominante diagonale, false altrimenti.
	 */
	public boolean DiagonallyDominant() {
		for (int i=0; i<A.getLength(); i++) {
			double tmpSum = 0;
			for (int j=0; j<A.getLength(); j++) 
					tmpSum += Math.abs(A.getElement(i, j));
			if (tmpSum > 2*Math.abs(A.getElement(i, i)))
				return false;
		}
		return true;
	}

	
	/**
	 * Metodo che fa la sottrazione tra gli elementi di due arrays.
	 * @param a Primo termine della sottrazione.
	 * @param b Secondo termine della sottrazione.
	 * @return Il risultato della sottrazione.
	 */
	public double[] subtract(double[] a, double[] b) {
		double[] tmp = new double[a.length];
		for (int i=0; i< a.length; i++) 
			tmp[i] = a[i] - b[i];
		return tmp;
	}
	
	
	/**
	 * Metodo che calcola la norma infinito di un vettore passato in input.
	 * @param a Array contenente il vettore.
	 * @return La norma infinito calcolata.
	 */
	public double norm(double[] a) {
		double tmp = Math.abs(a[0]);
		for (int i=1; i<a.length; i++)
			if (Math.abs(a[i]) > tmp)
				tmp = Math.abs(a[i]);
		return tmp;
	}
	
}