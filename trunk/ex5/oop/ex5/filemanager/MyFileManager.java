package oop.ex5.filemanager;

import java.net.Socket;
import java.util.LinkedList;
import java.util.TreeMap;

import oop.ex5.common.NameServer;

public class MyFileManager {
	
	private LinkedList<NameServer> _servers = new LinkedList<NameServer>();
	private TreeMap<String, File> _files = new TreeMap<String, File>();
	private ListeningThread _listeningThread;
	private Socket _clientSock;
	
	public MyFileManager() {
		
	}
	
	public static void main(String[] args) {
		
		// init blah blah
		
		// start listening
		// start ui
	}	

}
