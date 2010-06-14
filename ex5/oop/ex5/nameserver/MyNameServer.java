package oop.ex5.nameserver;

import java.net.ServerSocket;
import java.util.HashMap;
import java.util.LinkedList;

import oop.ex5.common.ListeningThread;
import oop.ex5.common.NameServer;

public class MyNameServer implements NameServerData {
	
	private static final int NUM_OF_ARGS = 1;
	private static final int PORT_ARG_INDEX = 1;
	
	private NameServerData _data;
	
	public MyNameServer() {
		_data = new NameServerData();
	}
	
	public static void main(String[] args) {
		// TODO listen for clients and create a ClientThread for each incoming connection
		if (NUM_OF_ARGS != args.length) {
			return;
		}
		int port = Integer.parseInt(args[PORT_ARG_INDEX]);
		MyNameServer server = new MyNameServer();
		Thread listeningThread = new ListeningThread(port, new NameServerClientThreadFactory(data));
		listeningThread.run();
	}

}
