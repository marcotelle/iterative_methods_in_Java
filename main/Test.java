package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Scanner;

import linear.GaussSeidelSolver;
import linear.JacobiSolver;
import linear.SORSolver;
import matrix.CRSMatrix;
import matrix.compactMatrix;


/**
 * Classe che testa i metodi iterativi di Jacobi, Gauss-Seidel e SOR su matrici sparse.
 * 
 * @author marcotelle
 * @version 1.0
 * @since 2019-09-22
 */
public class Test {

	public static void main(String[] args) throws NumberFormatException, IOException {
		
		/*
		double[][] M = new double[4][4];
		M[0][0] = 2;
		M[1][0] = 5;
		M[1][1] = 8;
		M[2][2] = 3;
		M[3][1] = 6;
		M[3][3] = 7;
		
		CRSMatrix CRS = new CRSMatrix(M, 6);
		
		System.out.println("Matrice M = ");
		CRS.print();
		System.out.println();
		
		double[] b = new double[4];
		Arrays.fill(b, 1);
		
		JacobiSolver J = new JacobiSolver(CRS);
		System.out.println("Metodo di Jacobi: ");
		double[] x = J.Solver(b);
		for (int i=0; i<x.length; i++)
			System.out.println("x["+i+"] = "+x[i]);
		
		System.out.println();
		
		GaussSeidelSolver G = new GaussSeidelSolver(CRS);
		System.out.println("Metodo di Gauss-Seidel: ");
		double[] y = G.Solver(b);
		for (int i=0; i<y.length; i++)
			System.out.println("y["+i+"] = "+y[i]);
		
		System.out.println();
		
		double w = 1.5;
		SORSolver S = new SORSolver(CRS, w);
		System.out.println("Metodo del sovrarilassamento: ");
		double[] z = S.Solver(b);
		for (int i=0; i<z.length; i++)
			System.out.println("z["+i+"] = "+z[i]);
		
		
		System.out.println();
		**/
				
		/*
		compactMatrix A = new compactMatrix();
		A.set(5, 5, 4.0);
		A.set(0, 0, 1.0);
		A.set(5, 6, 3.0);
		A.set(4, 4, 2.0);
		A.set(1, 1, 5.0);
		A.set(5, 2, 2.0);
		A.set(5, 1, 6.0);
		

		
		A.makeReadOnly();
		A.print();
		System.out.println();
		A.columnQuickSort(0, A.getNNZ()-1);
		A.print();
		System.out.println();
		A.rowQuickSort(0, A.getNNZ()-1);
		A.print();
		System.out.println();
		A.quickSort(0, A.getNNZ()-1);
		A.print();
		
		CRSMatrix AA = new CRSMatrix(A);
		int length = AA.getLength();
		int nnz = AA.getNNZ();
		
		
		System.out.println();
		double[] values = new double[nnz];
		values = AA.getValues();
		for (int i=0; i<nnz; i++)
			System.out.println(values[i]);
		System.out.println();
		
		int[] columns = new int[nnz];
		columns = AA.getColumns();
		for (int i=0; i<nnz; i++)
			System.out.println(columns[i]);
		System.out.println();
		
		int[] rows = new int[length];
		rows = AA.getRows();
		for (int i=0; i<length+1; i++)
			System.out.println(rows[i]);
		System.out.println();
		AA.print();
		System.out.println();
		System.out.println();
		**/
		
		BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
		boolean exit = false;
		System.out.println("EP Project Tester\n\n");
		System.out.print("Digitare la dimensione della matrice: ");
		int N = Integer.parseInt(consoleReader.readLine());
		System.out.println("\n\nAlloco la matrice...");
		
		compactMatrix I = new compactMatrix();
		//int N = 1000;
		for (int i=0; i<N; i++)
			I.set(i, i, 1.0);
		
		double a = Math.random()*0.01;
		
		for (int i=0; i<(N*0.01); i++) {
			int ii = (int) (N*Math.random());
			int jj = (int) (N*Math.random());
			I.set(ii, jj, a);
		}
		
		System.out.println("\n...");
		
		I.makeReadOnly();

		I.columnQuickSort(0, I.getNNZ()-1);
		I.rowQuickSort(0, I.getNNZ()-1);
		I.quickSort(0, I.getNNZ()-1);
		
		CRSMatrix CCRS = new CRSMatrix(I);
		
		System.out.println("\nMatrice allocata!");
		
		double[] b = new double[N];
		Arrays.fill(b, 1);
		
		while (!exit) {
			System.out.println("\n\nQuale metodo iterativo si vuole applicare?");
			System.out.println("1) Jacobi\n2) Gauss-Seidel\n3) Sovrarilassamento\n"
					+ "4) Allocare nuova matrice\n5)Uscire dal Test");
			int decision = Integer.parseInt(consoleReader.readLine());
			switch (decision) {
				case 1:
					JacobiSolver J = new JacobiSolver(CCRS);
					System.out.println("Metodo di Jacobi: ");
					double[] x = J.Solver(b);
					for (int i=0; i<x.length; i++)
						System.out.println("x["+i+"] = "+x[i]);		
					System.out.println();
					break;
				
				case 2:
					GaussSeidelSolver G = new GaussSeidelSolver(CCRS);
					System.out.println("Metodo di Gauss-Seidel: ");
					double[] y = G.Solver(b);
					for (int i=0; i<y.length; i++)
						System.out.println("y["+i+"] = "+y[i]);
					System.out.println();
					break;
					
				case 3:
					double w = 1.5;
					SORSolver S = new SORSolver(CCRS, w);
					System.out.println("Metodo del sovrarilassamento: ");
					double[] z = S.Solver(b);
					for (int i=0; i<z.length; i++)
						System.out.println("z["+i+"] = "+z[i]);
					System.out.println();
					break;
					
				case 4:
					System.out.println("\n\nAlloco la matrice...");
					
					I = new compactMatrix();
					
					for (int i=0; i<N; i++)
						I.set(i, i, 1.0);
					
					a = Math.random()*0.01;
					
					for (int i=0; i<(N*0.01); i++) {
						int ii = (int) (N*Math.random());
						int jj = (int) (N*Math.random());
						I.set(ii, jj, a);
					}
					
					System.out.println("\n...");
					
					I.makeReadOnly();

					I.columnQuickSort(0, I.getNNZ()-1);
					I.rowQuickSort(0, I.getNNZ()-1);
					I.quickSort(0, I.getNNZ()-1);
					
					CCRS = new CRSMatrix(I);
					
					System.out.println("\nMatrice allocata!");
					break;
				
				case 5:
					exit = true;
					break;
					
				default:
					System.out.println("Scelta non possibile! Digitare un numero tra 1/4");
					break;
			}
		}
	}

}