package oop.ex5.nameserver;

import java.io.IOException;

import oop.ex5.common.ListeningThread;

public class MyNameServer {
	
	private static final int NUM_OF_ARGS = 1;
	private static final int PORT_ARG_INDEX = 1;
	private NameServerData _data;
	
	public MyNameServer() {
		_data = new NameServerData();
	}
	
	public static void main(String[] args) {
		if (NUM_OF_ARGS != args.length) {
			// TODO print error
			return;
		}
		try {
			int port = Integer.parseInt(args[PORT_ARG_INDEX]);
			MyNameServer server = new MyNameServer();
			Thread listeningThread = new ListeningThread(port, new NameServerClientThreadFactory(server._data), server._data);
			listeningThread.run();
		} catch (NumberFormatException e) {
			// TODO print error
			return;
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO other exceptions?
	}

}
