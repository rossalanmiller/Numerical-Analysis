import java.lang.Math;
import java.text.DecimalFormat;

public class Project1 
{
	public static double g1(double x)
	{
		return x*x*x + (4.0*x*x) - 10;
	}
	public static double g2(double x)
	{
		return Math.sqrt((10.0/x) - 4*x);
	}
	public static double g3(double x)
	{
		return 0.5*(Math.sqrt(10-x*x*x));
	}
	public static double g4(double x)
	{
		return Math.sqrt(10/(4+x));
	}
	public static double g5(double x)
	{
		return x - ((x*x*x + 4*x*x - 10)/(3*x*x + 8*x));
	}
	
	public static void printRow(int i, double[] val)
	{
		DecimalFormat fmt = new DecimalFormat("#.00000000");
		fmt.setMaximumIntegerDigits(7);
		
		String rowstr = String.format("%02d ", i);
		String num;
		for(int j = 0; j < 5; j++)
		{
			if(Double.isInfinite(val[j]))
			{
				num = "Infinite";
			}
			else if(Double.isNaN(val[j]))
			{
				num = "NaN";
			}
			else
			{
				num = fmt.format(val[j]);
			}
			rowstr += String.format("%17s",num);
		}
		System.out.println(rowstr);
	}
	
	public static String repeat(char s, int n)
	{
		String str = String.format("%0" + n + "d", 0);
		str = str.replace('0', s);
		return str;
	}
	
	public static void printHeader()
	{
		String[] functions = {"g1", "g2", "g3", "g4", "g5"};
		String header = String.format("%2s ", "n");
		for(int i = 0; i < functions.length; i++)
		{
			header += String.format("%17s",functions[i]);
		}
		//header += String.format("%5s", "k");
		System.out.println(header);
		
	}
	
	
	public static void main(String[] args) 
	{
		double p0 = 1.5;
		double[] vals = new double[5];
		
		
		vals[0] = g1(p0);
		vals[1] = g2(p0);
		vals[2] = g3(p0);
		vals[3] = g4(p0);
		vals[4] = g5(p0);
		
		printHeader();
		for(int i = 1; i <= 30; i++)
		{
			System.out.println(repeat('-',88));
			printRow(i,vals);
			vals[0] = g1(vals[0]);
			vals[1] = g2(vals[1]);
			vals[2] = g3(vals[2]);
			vals[3] = g4(vals[3]);
			vals[4] = g5(vals[4]);
		}
	}

}
