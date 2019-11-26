/*
 * Program for marking calls which fall in a specific callset, given the sensitive callset
 * Takes parameters for read support and SV length required for the specific callset
 */
import java.util.*;
import java.io.*;
public class MarkSpecificCalls {
public static void main(String[] args) throws Exception
{
	String fn = "";
	String ofn = "";
	int minReadSupport = 0;
	int minLength = 0;
	if(args.length == 4)
	{
		fn = args[0];
		ofn = args[1];
		minReadSupport = Integer.parseInt(args[2]);
		minLength = Integer.parseInt(args[3]);
	}
	else
	{
		System.out.println("Usage: java MarkSpecificCalls vcffile outfile minreadsupport minlength");
		return;
	}
	
	convertFile(fn, ofn, minReadSupport, minLength);
	
}
static void convertFile(String fn, String ofn, int minReadSupport, int minLength) throws Exception
{
	Scanner input = new Scanner(new FileInputStream(new File(fn)));
	PrintWriter out = new PrintWriter(new File(ofn));
	
	VcfHeader header = new VcfHeader();
	ArrayList<VcfEntry> entries = new ArrayList<VcfEntry>();
	
	while(input.hasNext())
	{
		String line = input.nextLine();
		if(line.length() == 0)
		{
			continue;
		}
		if(line.startsWith("#"))
		{
			header.addLine(line);
		}
		else
		{
			VcfEntry entry = new VcfEntry(line);
			boolean inSpecific = false;
			int readSupport = 0;
			String[] rnamesField = entry.getRnames();
			if(rnamesField.length > 0)
			{
				readSupport = rnamesField.length;
			}
			
			if(readSupport >= minReadSupport && Math.abs(entry.getLength()) >= minLength) 
			{
				inSpecific = true;
			}
			
			entry.setInfo("IN_SPECIFIC", inSpecific ? "1" : "0");
			entries.add(entry);
		}
	}
	
	header.addInfoField("IN_SPECIFIC", "1", "String", "");
	header.print(out);
	
	for(VcfEntry entry : entries)
	{
		out.println(entry);
	}
	
	input.close();
	out.close();
}
}
