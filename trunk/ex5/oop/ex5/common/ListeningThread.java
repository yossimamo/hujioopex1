package oop.ex5.common;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.LinkedList;

import oop.ex5.nameserver.NameServerData;

public class ListeningThread extends Thread {
	
	private ServerSocket _listenSock;
	private LinkedList<ClientThread> _clientThreads;
	private ClientThreadFactory _factory;
	
	public ListeningThread(int port, ClientThreadFactory factory) throws IOException {
		_listenSock = new ServerSocket(port);
		_clientThreads = new LinkedList<ClientThread>();
	}
	
	@Override
	public void run() {
		Socket socket;
		try {
			while (true) {
				socket = _listenSock.accept();
				//TODO check if cleaning up the list is needed.
				_clientThreads.add(_factory.createClientThread(socket));
			}
			
		} catch (IOException e) {
			socket.close();
			//TODO restart thread.
		} catch (Interuption e) {
			Iterator<ProcessThread> it = _processThreads.iterator();
			while (it.hasNext()) {
				it.next().join();
			}
			_listenSock.close();
		}
	}
	
	public void shutDown() {
		//TODO interrupt
	}
}
