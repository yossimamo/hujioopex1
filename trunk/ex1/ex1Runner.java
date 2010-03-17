import oop.ex1.dataStructures.ObjectNotFoundException;
import oop.ex1.processToolKit.*;

import java.util.Scanner;
import java.util.HashMap;

public class ex1Runner {
	
	public static void main(String[] args)
	{
		Scanner scanner = new Scanner(System.in);
		HashMap<String,oop.ex1.processToolKit.Process> processPool = 
							new HashMap<String,oop.ex1.processToolKit.Process> ();
		ProcessManager mgr = new ProcessManager();
		processPool.put("null", null);
		
		while (scanner.hasNext())
		{
			String line = scanner.nextLine();
			try
			{
				handleLine(line,mgr,processPool);
			}
			catch (Exception e)
			{
				System.err.println("Exception caught: " + e.toString());
			}
		}
		
	}
	
	private static void handleLine(String line, ProcessManager mgr,
							       HashMap<String,oop.ex1.processToolKit.Process> processPool ) 
						throws NumberFormatException, ObjectNotFoundException, 
						DisorderException
	{
		String[] strArr = line.split(" ");
		if (strArr[0].equals("new"))
		{
			processPool.put(strArr[1],
					new oop.ex1.processToolKit.Process(strArr[1],
					processPool.get(strArr[3]),Integer.parseInt(strArr[2])));
		}
		else if (strArr[0].equals("add"))
		{
			if (processPool.get(strArr[1]) == null)
			{
				System.err.println("no such process: " + strArr[1]);
				return;
			}
			mgr.addProcessTree(processPool.get(strArr[1]));
		}
		else if (strArr[0].equals("update"))
		{
			if (processPool.get(strArr[1]) == null)
			{
				System.err.println("no such process: " + strArr[1]);
				return;
			}
			
			oop.ex1.processToolKit.Process prc = processPool.get(strArr[1]);
			mgr.updatePriority(prc, Integer.parseInt(strArr[2]));
		}
		else if (strArr[0].equals("run"))
		{
			mgr.runAllProcesses();
			System.exit(0);
		}
		else if (strArr[0].equals("prio"))
		{
			if (processPool.get(strArr[1]) == null)
			{
				System.err.println("no such process: " + strArr[1]);
				return;
			}
			
			oop.ex1.processToolKit.Process prc = processPool.get(strArr[1]);
			System.out.println(prc.getPriority());
		}
		else if (strArr[0].equals("print"))
		{
			if (processPool.get(strArr[1]) == null)
			{
				System.err.println("no such process: " + strArr[1]);
				return;
			}
			
			oop.ex1.processToolKit.Process prc = processPool.get(strArr[1]);
			System.out.println(prc.toString());
		}
	}

}
