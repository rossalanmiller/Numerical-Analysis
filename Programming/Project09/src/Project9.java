import java.util.function.*;
import java.util.Comparator;
import java.text.DecimalFormat;
import java.util.Arrays;
public class Project9
{
	static double func1(double[] x)
	{
		return (Math.pow(x[0], 2) + Math.pow(x[1], 2) + 8)/10.0;
	}
	
	static double func2(double[] x)
	{
		
		return (x[0]*x[1] + x[0] + 8)/10.0;
	}
	
	/*
	static double func3(double[] x)
	{
		return Math.pow(x[0], 2)+.1*Math.pow(x[1], 2)-.01*x[1]+1;
	}*/
	
	static double[] iterate(double[] x, ToDoubleFunction[] funcArray)
	{
		double[] temp = new double[x.length];
		//double[] temp = copyVec(x);
		for(int i = 0; i < x.length; i++)
		{
			//temp[i] = funcArray[i].applyAsDouble(temp);
			temp[i] = funcArray[i].applyAsDouble(x);
		}
		
		return temp;
	}
	
	static double[] applyOpToVectors(double[] x, DoubleBinaryOperator op, double[] y)
	{
		double[] temp = new double[x.length];
		for(int i = 0; i < temp.length; i++)
		{
			double val = op.applyAsDouble(x[i], y[i]);
			temp[i] = val;
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
		return applyOpToVectors(x, (a,b) -> (a+0), x);
	}
	static double getNorm(double[] x)
	{
		double[] t = copyVec(x);
		Arrays.sort(t);
		return Math.abs(t[t.length-1]);
	}
	
	public static void printRow(int i, double[] xn, double norm)
	{
		DecimalFormat fmt = new DecimalFormat("#0.00000");
		
		String rowstr = String.format("%02d ", i);
		for(int j = 0; j < xn.length; j++)
		{
			rowstr += String.format("%10s", fmt.format(xn[j]));
		}
		rowstr += String.format("%15s", fmt.format(norm));
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
		header += String.format("%9s|", "x3");
		header += String.format("%20s", "norm(x(n)-x(n-1))");
		System.out.println(header);
		System.out.println(repeat("-",53));
		
	}
	
	public static void main(String[] args)
	{
		ToDoubleFunction<double[]> func1 = (a) -> func1(a);
		ToDoubleFunction<double[]> func2 = (a) -> func2(a);
		//ToDoubleFunction<double[]> func3 = (a) -> func3(a);
		
		//ToDoubleFunction[] funcArray = {func1,func2,func3};
		ToDoubleFunction[] funcArray = {func1,func2};
		
		double[] x0 = {0,0};
		double[] xn = iterate(x0,funcArray);
		double E = 1e-5;
		double norm = getNorm(subVec(xn,x0));
		
		printHeader();
		int count = 1;
		while(norm > E)
		{
			printRow(count, xn, norm);
			x0 = copyVec(xn);
			xn = iterate(xn, funcArray);
			norm = getNorm(subVec(xn,x0));
			count++;
		}
		printRow(count, xn, norm);
		
		double[] test = {.5, 0, 0};
		//System.out.println(func2(test));
		//System.out.println(Arrays.toString(xn));
		
	}
}
