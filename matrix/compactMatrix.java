package matrix;

/**
 * La classe compactMatrix implementa la rappresentazione di matrici sparse
 * secondo il tradizionale formato con 3 array rispettivamente per
 * indici di riga, indici di colonna e valori non nulli.
 * 
 * @author marcotelle
 * @since 2020-02-27
 *
 */
public class compactMatrix {
	
	private double values[];
	private int rows[];
	private int columns[];
	private int size;
	private boolean write;
	
	/**
	 * Metodo costruttore che alloca gli array e inizializza
	 * le variabili. La dimensione degli array viene inizializzata a 2.
	 */
	public compactMatrix() {
		size = 0;
		write = true;
		values = new double[2];
		rows = new int[2];
		columns =  new int[2];
	}
	
	/**
	 * Metodo setter che insierisce un elemento nella matrice.
	 * 
	 * @param i Indice di riga dell'elemento.
	 * @param j Indice di colonna dell'elemento.
	 * @param value Valore dell'elemento.
	 */
	public void set(int i, int j, double value) {
		if (!write) throw new IllegalArgumentException("La matrice è read only!");
		
		size++;
		
		if (values.length < size)
			growUp();
		
		values[size-1] = value;
		rows[size-1] = i;
		columns[size-1] = j;
	}
	
	/**
	 * Metodo che raddoppia la dimensione degli array. 
	 * Utilizzato nel caso in cui non ci sia più spazio per nuovi elementi.
	 */
	private void growUp() {
		double[] $values = new double[size*2];
		int[] $rows = new int[size*2];
		int[] $columns = new int[size*2];
		System.arraycopy(values, 0, $values, 0, size-1);
		System.arraycopy(rows, 0, $rows, 0, size-1);
		System.arraycopy(columns, 0, $columns, 0, size-1);
		values = $values;
		rows = $rows;
		columns = $columns;
		
	}
	
	/**
	 * Metodo che setta la matrice come read only.
	 */
	public void makeReadOnly() {
		write = false;
	}
	
	/**
	 * Metodo getter che restituisce il numero di elementi diversi da zero.
	 * @return Il numero di elementi diversi da zero.
	 */
	public int getNNZ() {
		return size;
	}
	
	/**
	 * Implmentazione dell'algoritmo Quicksort per le compactMatrix.
	 * @param begin Indice del primo elemento.
	 * @param end Indice dell'ultimo elemento.
	 */
	public void quickSort(int begin, int end) {
		// Ordino per riga
		rowQuickSort(begin, end);
		
		int row = rows[0];
		begin = 0;
		for (int i=0; i<size; i++) {	// scorro le righe
			if (rows[i] != row) {
				end = i-1;
				columnQuickSort(begin, end);
				begin = i;
				row = rows[i];
			}
			if ( i == size-1) {
				end = i;
				columnQuickSort(begin, end);
			}
		}
	}
	
	/**
	 * Implementazione dell'algoritmo Quicksort sulle righe di una compactMatrix.
	 * @param begin Indice del primo elemento.
	 * @param end Indice dell'ultimo elemento.
	 */
	public void rowQuickSort(int begin, int end) {
		if (begin < end) {
			int partitionIndex = rowPartition(begin, end);
		
			rowQuickSort(begin, partitionIndex-1);
			rowQuickSort(partitionIndex+1, end);
		}
		
	}
	
	/**
	 * Implmentazione dell'algoritmo Quicksort sulle colonne di una CompactMatrix.
	 * @param begin Indice del primo elemento.
	 * @param end Indice dell'ultimo elemento.
	 */
	public void columnQuickSort(int begin, int end) {
		if (begin < end) {
			int partitionIndex = columnPartition(begin, end);
		
			columnQuickSort(begin, partitionIndex-1);
			columnQuickSort(partitionIndex+1, end);
		}
	}
	
	/**
	 * Metodo ausiliario utilizzato da rowQuickSort.
	 * @param begin Indice del primo elemento.
	 * @param end Indice dell'ultimo elemento.
	 * @return Elemento dell'array che avrà elementi minori nelle 
	 * posizioni precedenti e elementi maggiori in quelle successive.
	 */
	private int rowPartition(int begin, int end) {
		int k = (int) (begin + (end - begin - 1) * Math.random());
		swapElem(k,end);
		int pivot = rows[end];
		int i = (begin-1);
		
		for (int j = begin; j<end; j++) {
			if (rows[j] < pivot) {
				i++;
				swapElem(i,j);
			}
		}
		swapElem(i+1,end);
		
		return i+1;
	}
	
	/**
	 * Metodo ausiliario utilizzato da columnQuickSort.
	 * @param begin Indice del primo elemento.
	 * @param end Indice dell'ultimo elemento.
	 * @return Elemento dell'array che avrà elementi minori nelle 
	 * posizioni precedenti e elementi maggiori in quelle successive.
	 */
	private int columnPartition(int begin, int end) {
		int k = (int) (begin + (end - begin - 1) * Math.random());
		swapElem(k,end);
		int pivot = columns[end];
		int i = (begin-1);
		
		for (int j = begin; j<end; j++) {
			if (columns[j] < pivot) {
				i++;
				swapElem(i,j);
			}
		}
		swapElem(i+1,end);
		
		return i+1;
	}
	
	/**
	 * Metodo che inverte due elementi all'interno
	 * degli array che rappresentano la matrice.
	 * @param i Indice del primo elemento.
	 * @param j Indice del secondo elemento.
	 */
	private void swapElem(int i, int j) {
		int $row = rows[i];
		int $col = columns[i];
		double $val = values[i];
		
		rows[i] = rows[j];
		columns[i] = columns[j];
		values[i] = values[j];
		
		rows[j] = $row;
		columns[j] = $col;
		values[j] = $val;
	}
	
	/**
	 * Metodo che stampa su console la matrice.
	 */
	public void print() { 
		for (int i=0; i<size; i++) {
			System.out.println(values[i]+"  "+rows[i]+"  "+columns[i]);
		}
	}


	/**
	 * Metodo getter che restituisce il valore di un elemento.
	 * @param i L'indice dell'elemento.
	 * @return Il valore dell'elemento.
	 */
	public double getValue(int i) {
		return values[i];
	}


	/**
	 * Metodo getter che restituisce l'indice della colonna di un elemento
	 * @param i L'indice dell'elemento nei tre array.
	 * @return L'indice della colonna dell'elemento.
	 */
	public int getColumn(int i) {
		return columns[i];
	}


	/**
	 * Metodo getter che restituisce l'indice della riga di un elemento.
	 * @param i L'indice dell'elemento nei tre array.
	 * @return L'indice della riga dell'elemento.
	 */
	public int getRow(int i) {
		return rows[i];
	}
	
	/**
	 * Metodo getter che restituisce la dimensione della matrice.
	 * La matrice è quadrata, si prende quindi il massimo tra il numero 
	 * di righe e colonne.
	 * @return La dimensione della matrice
	 */
	public int getSize() {
		if (rows[size-1] > columns[size-1])
			return rows[size-1]+1;
		else
			return columns[size-1]+1;
	}
	
}
