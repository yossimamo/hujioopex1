package oop.ex5.nameserver;

import java.net.ServerSocket;
import java.util.HashMap;
import java.util.LinkedList;

import oop.ex5.common.NameServer;

public class MyNameServer {
	
	private LinkedList<FileManager> _fileManagers;
	private LinkedList<NameServer> _nameServers;
	private HashMap<String, LinkedList<FileManager>> _filesMapping;
	private ServerSocket _listenSock;
	
	public static void main(String[] args) {
		// listen for clients and create a ClientThread for each incoming connection
	}

}
