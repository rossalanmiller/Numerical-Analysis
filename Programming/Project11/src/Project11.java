//////////////////////////////////
// Name: Ross Miller
// Date: 12/8/17
// Purpose:   Contains some functions related to the construction,
//				inverse, and condition number of the Hilbert matrix.
//////////////////////////////////


import java.util.function.*;
import java.util.Arrays;
import java.text.DecimalFormat;

public class Project11
{
	public static void main(String[] args)
	{
		
		double[][] HM = constructHilbertMatrix(4);
		double[][] HMi = getInverse(HM);
	
		System.out.println("Printing inverse of Hilbert: ");
		print2DArray(HMi);
		System.out.println();
		
		double norm1 = getSupremumNorm(HM);
		double norm2 = getSupremumNorm(HMi);
		
		
		System.out.println("Printing condition num: ");
		double conditionNum = norm1*norm2;
		System.out.println(conditionNum);
		System.out.println();
		
		double[] B1 = new double[]{1, 0, 0, 1};
		double[] X1 = solveLinearSystem(HM, B1);
		
		System.out.print("B1 = ");
		printVector(B1);
		System.out.print("X1 = ");
		printVector(X1);
		System.out.println();
		
		double[] B2 = new double[]{1.0001,0,0,1.0001};
		double[] X2 = solveLinearSystem(HM, B2);
		
		System.out.print("B2 = ");
		printVector(B2);
		System.out.print("X2 = ");
		printVector(X2);
	}
	
	public static void printVector(double[] vector)
	{
		DecimalFormat fmt = new DecimalFormat("0.0000");
		System.out.print("[ ");
		for(int i = 0; i < vector.length; i++)
		{
			System.out.print(fmt.format(vector[i]) + ", ");
		}
		System.out.println("]");
	}
	
	public static double getSupremumNorm(double[][] A)
	{
		double[] sums = new double[A.length];
		double sum = 0;
		int indexMax = 0;
		for(int i = 0; i < A.length; i++)
		{
			for(int j = 0; j < A[0].length; j++)
			{
				sums[i] += Math.abs(A[i][j]);
			}
			if(sums[i] > sums[indexMax])
			{
				indexMax = i;
			}
		}
		return sums[indexMax];
	}
	
	public static double[] solveLinearSystem(double[][] A, double[] B)
	{
		double[][] system = new double[A.length][A[0].length+1];
		for(int i = 0; i < A.length; i++)
		{
			for(int j = 0; j < A[0].length; j++)
			{
				system[i][j] = A[i][j];
			}
			system[i][system.length] = B[i];
		}
		
		for(int r = 0; r < system.length; r++)
		{
			// if the sytem has a zero in the expected pivot column
			// then find the first row with a non-zero number in that
			// column and perfrom row operations
			if(system[r][r] == 0)
			{
				repairRow(system, r);
			}
			
			/* This loop takes the repaired row at row r and performs
			 * gaussian elimination on all other rows so that at the
			 * end we're left with a diagnoal matrix
			 */
			for(int r2 = 0; r2 < system.length; r2++)
			{
				if(r != r2)
				{
					double multiplyer = -(system[r2][r]/system[r][r]);
					system[r2] = rowOp(system[r], multiplyer, system[r2]);
				}
			}
		}
		
		double[] result = new double[system.length];
		for(int i = 0; i < result.length; i++)
		{
			result[i] = system[i][system.length]/system[i][i];
		}
		return result;
	}
	
	static void print2DArray(double[][] A)
	{
		DecimalFormat fmt = new DecimalFormat("0.00");
		for(int i = 0; i < A.length; i++)
		{
			System.out.print("[ ");
			for(int j = 0; j < A[0].length; j++)
			{
				System.out.print(fmt.format(A[i][j]) + ", ");
			}
			System.out.println(" ]");
		}
	}
	
	/* Constructs the hilbert matrix of size n*n */
	public static double[][] constructHilbertMatrix(int n)
	{
		double[][] HM = new double[n][n];
		
		for(int i = 0; i < n; i++)
		{
			for(int j = 0; j < n; j++)
			{
				HM[i][j] = 1.0/((i+1) + (j+1) - 1);
			}
		}
		
		return HM;
	}
	
	/* Given a matrix A, returns the inverse of A using
	 * Gauss-Jordan elimination
	 */
	public static double[][] getInverse(double[][] A)
	{
		
		double[][] AImatrix = new double[A.length][A.length+A.length];
		
		for(int i = 0; i < A.length; i++)
		{
			for(int j = 0; j < A.length; j++)
			{
				AImatrix[i][j] = A[i][j];
			}
		}
		
		for(int i = 0; i < A.length; i++)
		{
			AImatrix[i][i+A.length] = 1;
		}
		
		
		for(int r = 0; r < AImatrix.length; r++)
		{
			// if the sytem has a zero in the expected pivot column
			// then find the first row with a non-zero number in that
			// column and perfrom row operations
			if(AImatrix[r][r] == 0)
			{
				repairRow(AImatrix, r);
			}
			
			AImatrix[r] = rowOp(AImatrix[r], 1.0/AImatrix[r][r]);
			/* This loop takes the repaired row at row r and performs
			 * gaussian elimination on all other rows so that at the
			 * end we're left with a diagnoal matrix*/
			for(int r2 = 0; r2 < AImatrix.length; r2++)
			{
				if(r != r2)
				{
					double multiplyer = -(AImatrix[r2][r]/AImatrix[r][r]);
					AImatrix[r2] = rowOp(AImatrix[r], multiplyer, AImatrix[r2]);
				}
			}
		}
		
		double[][] inverse = new double[A.length][A.length];
		
		for(int i = 0; i < inverse.length; i++)
		{
			for(int j = 0; j < inverse.length; j++)
			{
				inverse[i][j] = AImatrix[i][j+inverse.length];
			}
		}
		
		return inverse;
	}
	
	public static double[][] roundMatrix(double[][] A)
	{

		return A;
	}
	
	
	/* given a linear sytem and the index of a diagonal with
	 * a zero cofactor performs a row operation to "repair it"
	 */
	public static void repairRow(double[][] system, int row)
	{
		for(int i = 0; i < system.length; i++)
		{
			if(system[i][row] != 0)
			{
				system[row] = rowOp(system[i], 1, system[row]);
				break;
			}
		}
	}
	
	
	/* Performs a rudimentary row operation on two ros
	 * of a linear system
	 */
	public static double[] rowOp(double[] source, double multiplyer, double[] dest)
	{
		double[] newRow = copyVec(source);
		for(int i = 0; i < newRow.length; i++)
		{
			newRow[i] = newRow[i]*multiplyer;
		}
		newRow = addVec(newRow, dest);
		return newRow;
	}
	
	/* Performs a rowOperation using only one row
	 * of a linear system
	 */
	public static double[] rowOp(double[] source, double multiplyer)
	{
		double[] newRow = copyVec(source);
		for(int i = 0; i < newRow.length; i++)
		{
			newRow[i] = newRow[i]*multiplyer;
		}
		return newRow;
	}
	
	/**
	  Given two vectors x and y of the same size, applyOpToVectors will apply the given binary operator
	  to each element of x and y and return the resulting vector.
	  */
	static double[] applyOpToVectors(double[] x, DoubleBinaryOperator op, double[] y)
	{
		double[] result = new double[x.length];
		for(int i = 0; i < result.length; i++)
		{
			double val = op.applyAsDouble(x[i], y[i]);
			result[i] = val;
		}
		return result;
	}
	
	static double[] addVec(double[] x, double[] y)
	{
		return applyOpToVectors(x, (a,b) -> (a+b), y);
	}
	
	static double[] subVec(double[] x, double[] y)
	{
		return applyOpToVectors(x, (a,b) -> (a-b), y);
	}
	
	static double[] copyVec(double[] x)
	{
		return applyOpToVectors(x, (a,b) -> (a+0), x);
	}
}