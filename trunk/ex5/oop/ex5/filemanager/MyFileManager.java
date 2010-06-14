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
	
	private static FileManagerDataBase _dataBase;
	private ListeningThread _listeningThread;
	private Socket _clientSock;
	private static Integer _port;
	
	enum Command {
		LIST,
		LISTSERVERS,
		GET,
		DEL,
		KILL,
		BYE,
		OTHER
	};
	
	public MyFileManager() {
		
	}
	
	public static void main(String[] args) {
		_dataBase = new FileManagerDataBase(args[0], args[1]);
		_port.valueOf(args[2]);
		Scenario init = new InitFileManagerScenario();
		init.executeScenario();
		FileManagerServerHandler serverHandler = new FileManagerServerHandler(_dataBase, _port);
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
				printFiles();
				break;
			case LISTSERVERS :
				printServers();
				break;
			case GET :
				Scenario getScenario = new GetScenario(_dataBase);
				getScenario.executeScenario();
				break;
			case DEL :
				Scenario delScenario = new DelScenario(_dataBase);
				delScenario.executeScenario();
				break;
			case KILL :
				Scenario killScenario = new KillScenario(_dataBase);
				killScenario.executeScenario();
				break;
			case BYE :
				serverHandler.shutDown();
				Scenario byeScenario = new ByeScenario(_dataBase);
				byeScenario.executeScenario();
				System.out.println("Good Bye!");
				break;
			case OTHER :
				System.out.println("unknown command please try again!");
			}
		}while (nextCommand != Command.BYE);
	}

	private static void printServers() {
		Iterator<NameServer> it = _dataBase.nameServersIterator();
		while (it.hasNext()) {
			NameServer nameServer = it.next();
			System.out.println("ip: " + nameServer.getIP() + " port: " + nameServer.getPort());
		}
	}

	private static void printFiles() {
		Set<String> files = _dataBase.getFiles();
		Iterator<String> it = files.iterator();
		while (it.hasNext()) {
			System.out.println(it.next());
		}
	}

}
