package matrix;

public class compactMatrix {
	
	private double values[];
	private int rows[];
	private int columns[];
	private int size;
	private boolean write;
	
	public compactMatrix() {
		size = 0;
		write = true;
		values = new double[2];
		rows = new int[2];
		columns =  new int[2];
	}
	
	
	public void set(int i, int j, double value) {
		if (!write) throw new IllegalArgumentException("La matrice è read only!");
		
		size++;
		
		if (values.length < size)
			growUp();
		
		values[size-1] = value;
		rows[size-1] = i;
		columns[size-1] = j;
	}
	
	
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
	
	
	public void makeReadOnly() {
		write = false;
	}
	
	
	public int getNNZ() {
		return size;
	}
	
	
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
	
	
	public void rowQuickSort(int begin, int end) {
		if (begin < end) {
			int partitionIndex = rowPartition(begin, end);
		
			rowQuickSort(begin, partitionIndex-1);
			rowQuickSort(partitionIndex+1, end);
		}
		
	}
	
	
	public void columnQuickSort(int begin, int end) {
		if (begin < end) {
			int partitionIndex = columnPartition(begin, end);
		
			columnQuickSort(begin, partitionIndex-1);
			columnQuickSort(partitionIndex+1, end);
		}
	}
	
	
	private int rowPartition(int begin, int end) {
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
	
	
	private int columnPartition(int begin, int end) {
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
	
	
	public void print() { 
		for (int i=0; i<size; i++) {
			System.out.println(values[i]+"  "+rows[i]+"  "+columns[i]);
		}
	}


	/**
	 * @param i
	 * @return
	 */
	public double getValue(int i) {
		// TODO Auto-generated method stub
		return values[i];
	}


	/**
	 * @param i
	 * @return
	 */
	public int getColumnIndex(int i) {
		// TODO Auto-generated method stub
		return columns[i];
	}


	/**
	 * @param i
	 * @return
	 */
	public int getRow(int i) {
		// TODO Auto-generated method stub
		return rows[i];
	}
	
	public int getSize() {
		if (rows[size-1] > columns[size-1])
			return rows[size-1]+1;
		else
			return columns[size-1]+1;
	}
	
}