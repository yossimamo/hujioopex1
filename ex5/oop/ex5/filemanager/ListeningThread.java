package oop.ex5.filemanager;

import java.net.ServerSocket;

public class ListeningThread extends Thread {
	
	private ServerSocket _listenSock;
	
	@Override
	public void run() {
		// listen for incoming connections
		// create UploadThread for each incoming client 
	}
}
