package oop.ex5.filemanager;

import java.net.Socket;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import oop.ex5.common.ListeningThread;
import oop.ex5.common.NameServer;

public class MyFileManager {
	
	private FileManagerData _data;
	
	enum Command {
		LIST,
		LISTSERVERS,
		GET,
		DEL,
		KILL,
		BYE,
		OTHER
	};
	
	public MyFileManager(String serverList, String myDirectory, int port) {
		_data = new FileManagerData(serverList, myDirectory, port);
		Scenario init = new InitFileManagerScenario(_data);
		init.executeScenario();
		//_serverHandler = new FileManagerServerHandler(_dataBase, _port);
	}
	
	public static void main(String[] args) {
		MyFileManager myFileManager = new MyFileManager(args[0], args[1], Integer.valueOf(args[2]));
		Command nextCommand;
		Scanner sc = new Scanner(System.in);
		do {
			// assumes all commands are valid
			String nextCommandLine = sc.next();
			Scanner commandLineScanner = new Scanner(nextCommandLine);
			try {
				nextCommand = Command.valueOf(commandLineScanner.next());
			} catch (IllegalArgumentException e) {
				nextCommand = Command.OTHER;
			}
			String fileName;
			switch (nextCommand) {
			case LIST :
				printFiles(myFileManager);
				break;
			case LISTSERVERS :
				printServers(myFileManager);
				break;
			case GET :
				fileName = commandLineScanner.next();
				if (myFileManager._data.containsFile(fileName)) {
					System.out.println("File is already in the database");
				}
				else {
					Scenario getScenario = new GetScenario(myFileManager._data, fileName);
					getScenario.executeScenario();
				}
				break;
			case DEL :
				fileName = commandLineScanner.next();
				if (!myFileManager._data.containsFile(fileName)) {
					System.out.println("File is not in the database");
				}
				else {
					Scenario delScenario = new DelScenario(myFileManager._data, fileName);
					delScenario.executeScenario();
					System.out.println("Deletion OK");
				}
				break;
			case KILL :
				Scenario killScenario = new KillScenario(myFileManager._data);
				killScenario.executeScenario();
				break;
			case BYE :
				myFileManager._data.setShutdownSignal(true);
				Scenario byeScenario = new ByeScenario(myFileManager._data);
				byeScenario.executeScenario();
				System.out.println("Good Bye!");
				break;
			case OTHER :
				System.out.println("unknown command please try again!");
			}
			commandLineScanner.close();
		} while (nextCommand != Command.BYE);
		sc.close();
	}

	private static void printServers(MyFileManager myFileManager) {
		Iterator<NameServer> it = myFileManager._data.nameServersIterator();
		while (it.hasNext()) {
			NameServer nameServer = it.next();
			System.out.println("ip: " + nameServer.getIP() + " port: " + nameServer.getPort());
		}
	}

	private static void printFiles(MyFileManager myFileManager) {
		String[] files = myFileManager._data.getFiles();
		if (files != null) {
			for (int i=0 ; i<files.length ; i++) {
				System.out.println(files[i]);
			}
		}
		
	}

}
