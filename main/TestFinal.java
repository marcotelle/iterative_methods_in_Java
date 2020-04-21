package main;

import java.util.Arrays;
import java.util.Random;

import linear.GaussSeidelSolver;
import linear.JacobiSolver;
import linear.SORSolver;
import matrix.CRSMatrix;
import matrix.compactMatrix;

public class TestFinal {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Random R = new Random(1);
		double startTime, endTime, timeElapsed;
		int N = 5000000;
		startTime = System.nanoTime();
		compactMatrix I = new compactMatrix();
		for (int i=0; i<N; i++)
			I.set(i, i, 1.0);
		for (int i=1; i<N; i++) {
			I.set(i-1,i, -0.27);
			I.set(i,i-1, -0.27);
			int ii = (int) (N*R.nextDouble());
			int jj = (int) (N*R.nextDouble());
			double a = R.nextDouble()*0.59;
			I.set(ii, jj, -a);
		}
		
		I.makeReadOnly();
		I.quickSort(0, I.getNNZ()-1);
		
		CRSMatrix CCRS = new CRSMatrix(I);
		endTime = System.nanoTime();
		timeElapsed = endTime-startTime;
		System.out.println("Matrice allocata!");
		System.out.println("Tempo per l'allocazione: "+timeElapsed/1000000000+" secondi");
		
		double[] xEsatto = new double[N];
		Arrays.fill(xEsatto, 1);
		
		double[] b = new double[N];
		b = CCRS.times(xEsatto);	
		
		/*
		double[] b = new double[N];
		Arrays.fill(b, 1);
		*/
		
		/*
		//JACOBI
		System.out.println();
		JacobiSolver J = new JacobiSolver(CCRS);
		System.out.println("Metodo di Jacobi: ");
		startTime = System.nanoTime();
		double[] x = J.Solver(b, xEsatto);
		endTime = System.nanoTime();
		timeElapsed = endTime-startTime;
		System.out.println("Tempo di esecuzione: "+timeElapsed/1000000000+" secondi");
		double errore = J.norm(J.subtract(xEsatto, x));
		System.out.println("Errore: "+errore);
		*/
		
		
		//GAUSS-SEIDEL
		System.out.println();
		GaussSeidelSolver G = new GaussSeidelSolver(CCRS);
		System.out.println("Metodo di Gauss-Seidel: ");
		startTime = System.nanoTime();
		double[] y = G.Solver(b, xEsatto);
		endTime = System.nanoTime();
		timeElapsed = endTime-startTime;
		System.out.println("Tempo di esecuzione: "+timeElapsed/1000000000+" secondi");
		double errore = G.norm(G.subtract(xEsatto, y));
		System.out.println("Errore: "+errore);
		
		
		/*
		//SOR
		System.out.println();
		double w = 1.3;
		SORSolver S = new SORSolver(CCRS, w);
		System.out.println("Metodo del sovrarilassamento: ");
		startTime = System.nanoTime();
		double[] z = S.Solver(b, xEsatto);
		endTime = System.nanoTime();
		timeElapsed = endTime-startTime;
		System.out.println("Tempo di esecuzione: "+timeElapsed/1000000000+" secondi");
		double errore = S.norm(S.subtract(xEsatto, z));
		System.out.println("Errore: "+errore);
		*/
	}
}
