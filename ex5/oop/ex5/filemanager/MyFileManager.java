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
	
	private AbstractDataBase _dataBase;
	private FileManagerServerHandler _serverHandler;
	
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
		_dataBase = new FileManagerDataBase(serverList, myDirectory, port);
		Scenario init = new InitFileManagerScenario(_dataBase);
		init.executeScenario();
		//_serverHandler = new FileManagerServerHandler(_dataBase, _port);
	}
	
	public static void main(String[] args) {
		MyFileManager myFileManager = new MyFileManager(args[0], args[1], Integer.valueOf(args[2]));
		Command nextCommand;
		do {
			// assumes all commands are valid
			Scanner sc = new Scanner(System.in);
			String nextCommandLine = sc.next();
			Scanner commandLineScanner = new Scanner(nextCommandLine);
			try {
				nextCommand = Command.valueOf(commandLineScanner.next());
			} catch (IllegalArgumentException e) {
				nextCommand = Command.OTHER;
			}
			switch (nextCommand) {
			case LIST :
				printFiles(myFileManager);
				break;
			case LISTSERVERS :
				printServers(myFileManager);
				break;
			case GET :
				String fileName = commandLineScanner.next();
				if (myFileManager.getDataBase().containsFile(fileName)) {
					System.out.println("File is already in the database");
				}
				else {
					Scenario getScenario = new GetScenario(myFileManager.getDataBase(), fileName);
					getScenario.executeScenario();
				}
				break;
			case DEL :
				String fileName = commandLineScanner.next();
				if (!myFileManager.getDataBase().containsFile(fileName)) {
					System.out.println("File is not in the database");
				}
				else {
					Scenario delScenario = new DelScenario(myFileManager.getDataBase(), fileName);
					delScenario.executeScenario();
					System.out.println("Deletion OK");
				}
				break;
			case KILL :
				Scenario killScenario = new KillScenario(myFileManager.getDataBase());
				killScenario.executeScenario();
				break;
			case BYE :
				myFileManager.getServerHandler().shutDown();
				Scenario byeScenario = new ByeScenario(myFileManager.getDataBase());
				byeScenario.executeScenario();
				System.out.println("Good Bye!");
				break;
			case OTHER :
				System.out.println("unknown command please try again!");
			}
		}while (nextCommand != Command.BYE);
	}

	private static void printServers(MyFileManager myFileManager) {
		Iterator<NameServer> it = myFileManager.getDataBase().nameServersIterator();
		while (it.hasNext()) {
			NameServer nameServer = it.next();
			System.out.println("ip: " + nameServer.getIP() + " port: " + nameServer.getPort());
		}
	}

	private static void printFiles(MyFileManager myFileManager) {
		String[] files = myFileManager.getDataBase().getFiles();
		if (files != null) {
			for (int i=0 ; i<files.length ; i++) {
				System.out.println(files[i]);
			}
		}
		
	}
	
	public AbstractDataBase getDataBase() {
		return _dataBase;
	}
	
	public FileManagerServerHandler getServerHandler() {
		return _serverHandler;
	}

}
