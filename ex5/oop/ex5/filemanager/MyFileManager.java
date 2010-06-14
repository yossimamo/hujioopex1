package oop.ex5.filemanager;

import java.net.Socket;
import java.util.LinkedList;
import java.util.TreeMap;

import oop.ex5.common.ListeningThread;
import oop.ex5.common.NameServer;

public class MyFileManager {
	
	private ListeningThread _listeningThread;
	private Socket _clientSock;
	private Integer _port;
	
	enum Commands {
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
		initServers(args[0]);
		initFiles(args[1]);
		_port.valueOf(args[2]);
		Scenario init = new InitFileManagerScenario();
		init.executeScenario();
		Thread listeningTread = new ListeningThread(_port, this);
		listeningTread.run();
		
		do {
			
		}while ()
		
		
		
		
	}

}
