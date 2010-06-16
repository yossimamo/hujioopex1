package oop.ex5.common;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Iterator;
import java.util.LinkedList;

public class ListeningThread extends Thread {
	
	private static final int LISTEN_TIMEOUT = 5000;
	
	private int _port;
	private ServerSocket _listenSock;
	private LinkedList<ClientThread> _clientThreads;
	private ClientThreadFactory _factory;
	private ShutdownSignal _signal;
	
	public ListeningThread(int port, ClientThreadFactory factory, ShutdownSignal signal)
		throws IOException {
		_port = port;
		_factory = factory;
		_clientThreads = new LinkedList<ClientThread>();
		_signal = signal;
	}
	
	@Override
	public void run() {
		// TODO U-G-L-Y
		Socket clientSock = new Socket();
		while (true) {
			try {
				_listenSock = new ServerSocket(_port);
				_listenSock.setSoTimeout(LISTEN_TIMEOUT);
				listen(clientSock);
			} catch (IOException e) {
				e.printStackTrace(); //TODO remove
				// Restart server
				try {
					clientSock.close();
					_listenSock.close();
				} catch (IOException ee) {
					ee.printStackTrace(); //TODO remove
					// Fail silently, nothing to do
				}
				continue;
			} catch (ServerShutdownException e) {
				e.printStackTrace(); //TODO remove
				// Join all current client threads and break from loop
				Iterator<ClientThread> it = _clientThreads.iterator();
				while (it.hasNext()) {
					try {
						it.next().join();
					} catch (InterruptedException ee) {
						ee.printStackTrace(); //TODO remove
						// Fail silently, nothing to do
					}
				}
				break;
			}
		}
		try {
			_listenSock.close();
		} catch (IOException e) {
			e.printStackTrace(); //TODO remove
			// Fail silently, nothing to do
		}
		
	}
	
	private void listen(Socket socket) throws IOException, ServerShutdownException {
		while (true) {
			try {
				socket = _listenSock.accept();
				//TODO List cleanup is needed!
				ClientThread ct = _factory.createClientThread(socket);
				_clientThreads.add(ct);
				ct.start();
			} catch (SocketTimeoutException e) {
				if (_signal.getShutdownSignal()) {
					throw new ServerShutdownException();
				} else {
					// TODO now would be a good time to clean up the thread list?
					continue;
				}
			}
		}
	}
	
}
