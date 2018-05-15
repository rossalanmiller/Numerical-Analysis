import java.lang.Math;
import java.text.DecimalFormat;

public class Project2
{
	public static double g1(double x)
	{
		return Math.pow(x+1.0, (1.0/3.0));
	}
	public static double g2(double x)
	{
		return Math.sqrt(Math.pow(Math.E, x)/3.0);
	}
	
	public static void printRow(int i, double Pn, double P0)
	{
		DecimalFormat fmt = new DecimalFormat("#0.00000");
		
		String rowstr = String.format("%03d ", i);
		rowstr += String.format("%15s", fmt.format(Pn));
		rowstr += String.format("  %12s", fmt.format(Math.abs(P0 - Pn)));
		//rowstr += String.format("    %3s", "1e-4");
		
		System.out.println(rowstr);
	}
	
	public static String repeat(String s, int n)
	{
		String str = String.format("%0" + n + "d", 0);
		str = str.replace("0", s);
		return str;
	}
	
	public static void printHeader(double E, double P0)
	{
		DecimalFormat fmt = new DecimalFormat("#.00");
		String header = "";
		header = String.format("%2s  |", "n");
		header += String.format("%15s  |","g(Pn-1) = Pn");
		header += String.format("%12s  |", "Pn - Pn-1");
		header += String.format(" E = %.0e |", E);
		header += String.format("%10s", "P0 = " + fmt.format(P0));
		System.out.println(header);
		System.out.println(repeat("-",60));
		
	}
	
	
	public static void main(String[] args) 
	{
		double P0 = 1;
		double E = 1e-5;
		double Pn = g1(P0);
		
		int count = 1;
		printHeader(E,P0);
		while(Math.abs(Pn - P0) > E)
		{
			//if(count % 10 == 0 || count == 1)
				printRow(count, Pn, P0);
			P0 = Pn;
			Pn = g1(Pn);
			count++;
		}
		printRow(count, Pn, P0);
		
		
	}

}