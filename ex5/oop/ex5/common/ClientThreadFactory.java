package oop.ex5.common;

import java.net.Socket;

public interface ClientThreadFactory {
	
	public ClientThread createClientThread(Socket socket);
	
}
