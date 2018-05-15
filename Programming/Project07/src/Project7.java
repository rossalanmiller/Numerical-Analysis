import java.text.DecimalFormat;
import java.util.Arrays;
public class Project7 
{
	public static void main(String[] args) 
	{
		double E = .01;
		double[][] T = {{0, 1.0/10, -1.0/5, 0},
						{1.0/11, 0, 1.0/11, -3.0/11},
						{-1.0/5, 1.0/10, 0, 0},
						{0, -3.0/8, 1.0/8, 0}};
		
		
		double[][] x0j = {{0},
						 {0},
						 {0},
						 {0}};
		
		double[][] x0g = getCopy(x0j);
		
		double[][] c = {{3.0/5},
				  		{25.0/11},
				  		{-11.0/10},
				  		{-11.0/8}};
		
		
		double[][] xj = jacobi(T, x0j, c);
		double[][] xg = gaussSeidel(T, x0g, c);
		
		int count = 0;
		double jNorm = getNorm(subMatrices(xj, x0j));
		double gNorm = getNorm(subMatrices(xg, x0g));
		
		printHeader();
		while(count < 25 && (jNorm > E || gNorm > E))
		{
			printRow(count, xj, xg, jNorm, gNorm );
			count++;
			x0j = getCopy(xj);
			x0g = getCopy(xg);
			
			xj = jacobi(T, xj, c);
			xg = gaussSeidel(T, xg, c);
			
			jNorm = getNorm(subMatrices(xj, x0j));
			gNorm = getNorm(subMatrices(xg, x0g));
			
			//printRow(count, xj, xg, jNorm, gNorm );
		}
		printRow(count, xj, xg, jNorm, gNorm );
		System.out.println(Arrays.toString(xg[0]));
		System.out.println(Arrays.toString(xg[1]));
		System.out.println(Arrays.toString(xg[2]));
		System.out.println(Arrays.toString(xg[3]));
		
	}
		

	
	static double[][] jacobi(double[][] T, double[][] x, double[][] c)
	{
		double[][] xn = addMatrices(multiplyMatrices(T,x),c);
		return xn;
	}
	
	static double[][] gaussSeidel(double[][] T, double[][] x, double[][] c)
	{
		double[][] xn = getCopy(x);
		for(int i = 0; i < x.length; i++)
		{
			xn[i][0] = multiplyRowColumn(T, i, xn, 0) +c[i][0];
		}
		
		return xn;
	}
	
	static double[][] multiplyMatrices(double[][] A, double[][] B)
	{
		int newR = A.length;
		int newC = B[0].length;
		double[][] newMatrix = new double[newR][newC];
		
		
		for(newR = 0; newR < newMatrix.length; newR++)
		{
			for(newC = 0; newC < newMatrix[0].length; newC++)
			{
				double sum = 0;
				sum = multiplyRowColumn(A, newR, B, newC);
				newMatrix[newR][newC] = sum;
			}
		}
		
		return newMatrix;
	}
	
	static double multiplyRowColumn(double[][] A, int row, double[][] B, int col)
	{
		double sum = 0;
		
		for(int i = 0; i < A[0].length; i++)
		{
			sum += A[row][i] * B[i][col];
		}
		return sum;
	}
	
	static double[][] subMatrices(double[][] A, double[][] B)
	{
		double[][] newMatrix = new double[A.length][A[0].length];
		for(int i = 0; i < A.length; i++)
		{
			for(int j = 0; j < A[0].length; j++)
			{
				newMatrix[i][j] = A[i][j] - B[i][j];
			}
		}
		return newMatrix;
	}
	
	static double[][] addMatrices(double[][] A, double[][] B)
	{
		double[][] newMatrix = new double[A.length][A[0].length];
		for(int i = 0; i < A.length; i++)
		{
			for(int j = 0; j < A[0].length; j++)
			{
				newMatrix[i][j] = A[i][j] + B[i][j];
			}
		}
		return newMatrix;
	}
	
	static double getNorm(double[][] A)
	{
		double[] sums = new double[A.length];
		for(int i = 0; i < A.length; i++)
		{
			double sum = 0;
			for(int j = 0; j < A[0].length; j++)
			{
				sum += Math.abs(A[i][j]);
			}
			sums[i] = sum;
		}
		Arrays.sort(sums);
		
		return sums[sums.length-1];
	}
	
	static double[][] getCopy(double[][] A)
	{
		double[][] newMatrix = new double[A.length][A[0].length];
		
		for(int i = 0; i < A.length; i++)
		{
			for(int j = 0; j < A[0].length; j++)
			{
				newMatrix[i][j] = A[i][j];
			}
		}
		return newMatrix;
	}
	
	static void printHeader()
	{
		String str = "";
		
		str += "n |             x  (jacobi)              |           x  (Gaus-Seidel)             |    ||xjn - xjn-1||   |   ||xgn - xgn-1 ||\n";
		str += "-----------------------------------------------------------------------------------------------------------------------------";
		System.out.println(str);
	}
	
	static void printRow(int n, double[][] xj, double[][] xgs, double normJ, double normG)
	{
		DecimalFormat formatter = new DecimalFormat("0.000");
		formatter.setMaximumIntegerDigits(5);
		String str = "";
		str += n +" ";
		str += vectorToString(xj) + "   ";
		str += vectorToString(xgs)+ "            ";
		str += String.format("%5s               ", formatter.format(normJ));
		str += String.format("%5s   ", formatter.format(normG));
		str += "\n";
		System.out.print(str);
	}

	static String matrixToString(double[][] A)
	{
		DecimalFormat formatter = new DecimalFormat("#.##");
		formatter.setMaximumIntegerDigits(9);
		String str = "";
		for(int i = 0; i < A.length; i++)
		{
			str += "[";
			for(int j = 0; j < A[0].length; j++)
			{
				str += String.format("%4s", formatter.format(A[i][j]));
			}
			str += "   ]\n";
		}
		
		return str;
	}
	
	static String vectorToString(double[][] A)
	{
		DecimalFormat formatter = new DecimalFormat("0.00");
		formatter.setMaximumIntegerDigits(3);
		String str = "";
		str += "[ ";
		for(int i = 0; i < A.length-1; i++)
		{
			str += String.format("%10s, ", formatter.format(A[i][0]));
		}
		str += String.format("%10s", formatter.format(A[A.length-1][0]));
		str += " ]";
		return str;
	}
	
}