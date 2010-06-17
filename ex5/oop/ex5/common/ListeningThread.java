package oop.ex5.common;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Iterator;
import java.util.LinkedList;

public class ListeningThread extends Thread {
	
	private static final int LISTEN_TIMEOUT_MS = 5000;
	
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
		Socket clientSock = new Socket();
		while (true) {
			try {
				_listenSock = new ServerSocket(_port);
				_listenSock.setSoTimeout(LISTEN_TIMEOUT_MS);
				listen(clientSock);
			} catch (IOException e) {
				// Restart server
				try {
					clientSock.close();
					_listenSock.close();
				} catch (IOException ee) {
					// Fail silently, nothing to do
				}
				continue;
			} catch (ServerShutdownException e) {
				// Join all current client threads and break from loop
				Iterator<ClientThread> it = _clientThreads.iterator();
				while (it.hasNext()) {
					try {
						it.next().join();
					} catch (InterruptedException ee) {
						// Fail silently, nothing to do
					}
				}
				break;
			}
		}
		try {
			_listenSock.close();
		} catch (IOException e) {
			// Fail silently, nothing to do
		}
		
	}
	
	private void listen(Socket socket) throws IOException, ServerShutdownException {
		while (true) {
			try {
				socket = _listenSock.accept();
				ClientThread ct = _factory.createClientThread(socket);
				_clientThreads.add(ct);
				ct.start();
			} catch (SocketTimeoutException e) {
				if (_signal.getShutdownSignal()) {
					throw new ServerShutdownException();
				} else {
					// Clean up dead threads from the list
					Iterator<ClientThread> it = _clientThreads.listIterator();
					while (it.hasNext()) {
						ClientThread thread = it.next();
						if (!thread.isAlive()) {
							it.remove();
						}
					}
					continue;
				}
			}
		}
	}
	
}
