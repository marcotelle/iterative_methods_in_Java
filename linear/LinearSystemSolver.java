package linear;

/**
 * Interfaccia contenente i metodi di un risolutore di sistemi lineari.
 * 
 * @author marcotelle
 * @since 2020-02-27
 */
public interface LinearSystemSolver {

	
	/**
	 * Metodo che risolve il sistema lineare A*x = b.
	 * @param b Array dei termini noti.
	 * @return Restituisce l'array contenente la soluzione del sistema.
	 */
	double[] Solver(double[] b, double[] xEsatto);
	

	/**
	 * Metodo che controlla se la matrice è predominante diagonale.
	 * @return true se la matrice è predominante diagonale, false altrimenti.
	 */
	boolean DiagonallyDominant();
	

	/**
	 * Metodo che fa la sottrazione tra gli elementi di due arrays.
	 * @param a Primo termine della sottrazione.
	 * @param b Secondo termine della sottrazione.
	 * @return Il risultato della sottrazione.
	 */
	double[] subtract(double[] a, double[] b);
	

	/**
	 * Metodo che calcola la norma infinito di un vettore passato in input.
	 * @param a Array contenente il vettore.
	 * @return La norma infinito calcolata.
	 */
	public double norm(double[] a);
		
}
