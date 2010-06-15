package oop.ex5.common;

import java.io.IOException;
import java.net.Socket;

public interface ClientThreadFactory {
	
	public ClientThread createClientThread(Socket socket) throws IOException;
	
}
