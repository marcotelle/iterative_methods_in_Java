package matrix;

/**
 * Il programma CRSMatrix implementa la rappresentazione CRS per matrici sparse.
 * 
 * @author marcotelle
 * @version 1.0
 * @since 2019-09-20
 */
public class CRSMatrix {
	
	// dimensione NNZ (=elementi diversi da 0), contiene i vaori diversi da 0 della matrice
	private double[] values;
	// dimensione NNZ, contiene l'indice della colonna di ogni elementi di values
	private int[] columnIndices;
	// dimenisone num righe + 1, è definito dalla relazione ricorsiva
	// rowPointers[0] = 0
	// rowPointers[i] = rowPointers[i-1] + numero di elementi diversi da 0 nella riga i-1
	private int[] rowPointers;
	// lunghezza della matrice = numero di righe = numero di colonne
	private int length;
	
	private int nnz;
		
	
	/**
	 * Metodo costruttore usato per allocare una matrice CRS vuota.
	 * @param n Dimensione della matrice da allocare.
	 * @param nnz Numero di elementi diversi da zero.
	 * @throws IllegalArgumentException Se il numero di elementi diversi da zero diviso il numero totale è maggiore di 0,5.
	 */
	public CRSMatrix(int n, int nnz) {
		//if ((nnz/(n+n)) > 0.5) throw new IllegalArgumentException("La matrice non è sparsa!");
			
		this.values = new double[nnz];
		this.rowPointers = new int[n+1];
		this.columnIndices = new int [nnz];
		this.length = n;
		this.nnz = nnz;
	}
	
	
	/**
	 * Metodo costruttore usato per allocare una matrice CRS da una fornita in input
	 * nel formato array 2D.
	 * @param m Array 2D contenente la matrice sparsa da implementare.
	 * @param nnz Numeri di elementi diversi da zero.
	 * @throws IllegalArgumentException Se la matrice non è quadrata.
	 */
	public CRSMatrix(double[][] m, int nnz)  {
		this(m.length, nnz);
		
		// controllo se la matrice passata è quadrata
		if (m.length != m[0].length) throw new IllegalArgumentException("La matrice deve essere quadrata!");
		
		int k=0;
		for (int i=0; i<m.length; i++)
			for (int j=0; j<m.length; j++)
				if (m[i][j] != 0) {
					this.values[k] = m[i][j];
					this.columnIndices[k] = j;
					for (int h=i+1; h<m.length+1; h++)
						this.rowPointers[h]++;
					k++;
				}
	}
	
	// TODO Matrice CRS da Matrice compact
	public CRSMatrix(compactMatrix M) {
		this(M.getSize(), M.getNNZ());
		
		for (int i = 0; i<M.getNNZ(); i++) {
			values[i] = M.getValue(i);
			columnIndices[i] = M.getColumnIndex(i);
			rowPointers[M.getRow(i)+1] += 1;
		}
		for (int i=1; i<=M.getSize(); i++)
			rowPointers[i] += rowPointers[i-1];
		
	}
	
	
	/**
	 * Metodo che stampa la matrice CRS.
	 * @return 0 se la stampa è andata a buon fine.
	 */
	public int print() {
		for (int i=0; i<length; i++) {
			int zeroIndex = 0;
			for (int k=rowPointers[i]; k<rowPointers[i+1]; k++) {
				int j = columnIndices[k];
				while (zeroIndex<j) {
					System.out.print(0.0+"   ");
					zeroIndex++;
				}
				System.out.print(values[k]+"   ");
				zeroIndex++;
			}
			while (zeroIndex < length) {
				System.out.print(0.0+"   ");
				zeroIndex++;
			}
			System.out.println();
		}
		return 0;
	}
	
	
	/**
	 * Metodo che cerca nella matrice l'elemento in posizione (i,j).
	 * @param i Indice di riga dell'elemento.
	 * @param j Indice di colonna dell'elemento.
	 * @throws IndexOutOfBoundsException Se l'indice di riga e/o di colonna è maggiore della dimensione della matrice.
	 * @return Il valore dell'elemento cercato.
	 */
	public double getElement(int i, int j) {
		if (i >= length  && j >= length) throw new IndexOutOfBoundsException();
		
		// scorro gli elementi diversi da zero nella riga
		for (int k = rowPointers[i]; k < rowPointers[i+1]; k++) {
			// se l'indice colonna corrisponde
			if (columnIndices[k] == j)
				return values[k];
			// se l'indice colonna è maggiore l'elemento ricercato è uguale a 0
			if (columnIndices[k] > j)
				return 0;
		}
		return 0;
	}
	
	
	public void set(int i, int j, double value) {
		if (i >= length  && j >= length) throw new IndexOutOfBoundsException();
		
		int z = searchForColumnIndex(j, rowPointers[i], rowPointers[i+1]);
		
		if (z < rowPointers[i+1] && columnIndices[z] == j) {
			if (value == 0.0) {
				remove(z, i);
			} else {
				values[z] = value;
			}
		} else {
			insert(z, i , j, value);
		}
	}
	
	
	/**
	 * Metodo che fa la copia della matrice.
	 * @return La copia della matrice.
	 */
	public CRSMatrix copy() {
		CRSMatrix M = new CRSMatrix(length, values.length);
		for (int i=0; i<nnz; i++)
			M.values[i] = values[i];
		for (int i=0; i<length+1; i++)
			M.rowPointers[i] = rowPointers[i];
		for (int i=0; i<nnz; i++)
			M.columnIndices[i] = columnIndices[i];
		return M;
	}
	
	/**
	 * Metodo che restituisce la dimensione della matrice.
	 * @return La dimensione della matrice.
	 */
	public int getLength() {
		return length;
	}
	
	
	private int searchForColumnIndex(int j, int left, int right) {
		if (right - left == 0 || j > columnIndices[right-1]) {
			return right;
		}
		
		while (left < right) {
			int p = (left + right) / 2;
			if (columnIndices[p] > j) {
				right = p;
			} else if (columnIndices[p] < j) {
				left = p + 1;
			} else {
				return p;
			}
		}
		return left;
	}
	
	
	private void remove(int k, int i) {
		nnz--;
		if (nnz - k > 0) {
			System.arraycopy(values, k+1, values, k, nnz);
			System.arraycopy(columnIndices, k+1, columnIndices, k, nnz);
		}
		for (int ii = i + 1; ii < length+1; ii++)
			rowPointers[ii]--;
	}
	
	
	private void insert(int k, int i, int j, double value) {
		if (value == 0.0) 
			return;
		
		if (values.length < nnz+1)
			growUp();
		
		if (nnz - k > 0) {
			System.arraycopy(values, k, values, k+1, nnz - k);
			System.arraycopy(columnIndices, k, columnIndices, k+1, nnz - k);
		}
		
		values[k] = value;
		columnIndices[k] = j;
		
		for (int ii = i+1; ii < length+1; ii++) {
			rowPointers[ii]++;
		}
		
		nnz++;
	}
		
	
	private void growUp() {
		int tmp = (nnz*3)/2;
		
		double[] $values = new double[tmp];
		int[] $columnIndices = new int[tmp];
		
		System.arraycopy(values, 0, $values, 0, nnz);
		System.arraycopy(columnIndices, 0, $columnIndices, 0, nnz);
		
		values = $values;
		columnIndices = $columnIndices;
	}
	
	public double [] getValues() {
		return values;
	}
	
	public int [] getColumns() {
		return columnIndices;
	}
	
	public int [] getRows() {
		return rowPointers;
	}
	
	public int getNNZ() {
		return nnz;
	}
	
	
}