package oop.ex5.common;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.LinkedList;

public class ListeningThread extends Thread {
	
	private ServerSocket _listenSock;
	private IHandlesReceivedMassages _serverLogicHandler;
	private LinkedList<ProcessThread> _processThreads;
	
	public ListeningThread(int port, IHandlesReceivedMassages serverLogicHandler) throws IOException {
		_listenSock = new ServerSocket(port);
		_serverLogicHandler = serverLogicHandler;
		_processThreads = new LinkedList<ProcessThread>();
	}
	
	public void run() {
		Socket socket;
		try {
			while (true) {
				socket = _listenSock.accept();
				//TODO check if cleaning up the list is needed.
				_processThreads.add(new ProcessThread(socket,_serverLogicHandler));
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
