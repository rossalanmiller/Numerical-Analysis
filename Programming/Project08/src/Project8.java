import java.util.function.*;
import java.util.Comparator;
import java.text.DecimalFormat;
import java.util.Arrays;
public class Project8 
{
	static double func1(double[] x)
	{
		//return (1.0/3.0)*(Math.cos(x[1]*x[2]))+(1.0/6.0);
		//return -Math.cos(x[0]*x[1]*x[2]) + 1;
		return -(Math.pow(x[1], 3) - 5*Math.pow(x[1], 2) - 2*x[1]-10);
	}
	static double func2(double[] x)
	{
		//return (1.0/9.0)*Math.sqrt(Math.pow(x[0], 2) + Math.sin(x[2]) +1.06) - 0.1;
		//return -Math.pow(1-x[0], (1.0/4.0)) - 0.05*Math.pow(x[2], 2) + 0.15*x[2] + 1;
		return (x[0]+Math.pow(x[1], 3) + Math.pow(x[1], 2) - 29)/14.0;
	}
	/*
	static double func3(double[] x)
	{
		//return (-1.0/20.0)*Math.pow(Math.E, (-x[0]*x[1])) - (10*Math.PI - 3)/60.0;
		return Math.pow(x[0], 2) - 0.1*Math.pow(x[1], 2) - 0.01*x[1] + 1;
	}*/
	
	/*Performs one iteration of jacobian matrix given the previos x term
	 * and returns the result
	 */
	static double[] iterate(double[] x, ToDoubleFunction[] funcArray)
	{
		//double[] temp = new double[x.length];
		double[] temp = copyVec(x);
		
		for(int i = 0; i < x.length; i++)
		{
			temp[i] = funcArray[i].applyAsDouble(temp);
		}
		
		return temp;
	}
	
	static double[] applyOpToVectors(double[] x, DoubleBinaryOperator op, double[] y)
	{
		double[] temp = new double[x.length];
		for(int i = 0; i < temp.length; i++)
		{
			temp[i] = op.applyAsDouble(x[i], y[i]);
		}
		return temp;
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
		return applyOpToVectors(x, (a,b) -> a, new double[x.length]);
	}
	static double getNorm(double[] x)
	{
		double[] t = copyVec(x);
		Arrays.sort(t);
		return Math.abs(t[t.length-1]);
	}
	
	public static void printRow(int i, double[] xn, double norm)
	{
		DecimalFormat fmt = new DecimalFormat("#0.000");
		
		String rowstr = String.format("%02d ", i);
		for(int j = 0; j < xn.length; j++)
		{
			rowstr += String.format("%10s", fmt.format(xn[j]));
		}
		rowstr += String.format("%10s", fmt.format(norm));
		System.out.println(rowstr);
	}
	
	public static String repeat(String s, int n)
	{
		String str = String.format("%0" + n + "d", 0);
		str = str.replace("0", s);
		return str;
	}
	
	//Prints a formated header for our table
	public static void printHeader()
	{
		//DecimalFormat fmt = new DecimalFormat("#.00");
		String header = "";
		header = String.format("%2s|", "k");
		header += String.format("%9s|","x1");
		header += String.format("%9s|", "x2");
		//header += String.format("%9s|", "x3");
		header += String.format("%9s", "norm");
		System.out.println(header);
		System.out.println(repeat("-",43));
		
	}
	
	public static void main(String[] args)
	{
		ToDoubleFunction<double[]> func1 = (a) -> func1(a);
		ToDoubleFunction<double[]> func2 = (a) -> func2(a);
		//ToDoubleFunction<double[]> func3 = (a) -> func3(a);
		
		//ToDoubleFunction[] funcArray = {func1,func2,func3};
		ToDoubleFunction[] funcArray = {func1,func2};
		
		double[] x0 = {15,-2};
		double[] xn = iterate(x0,funcArray);
		double E = 1e-5;
		double norm = getNorm(subVec(xn,x0));
		
		printHeader();
		int count = 1;
		while(norm > E)
		{
			if(count%5 == 0 || count == 1)
				printRow(count, xn, norm);
			x0 = copyVec(xn);
			xn = iterate(xn, funcArray);
			norm = getNorm(subVec(xn,x0));
			count++;
		}
		printRow(count, xn, norm);
		
		//System.out.println(Arrays.toString(xn));
		
	}
}
