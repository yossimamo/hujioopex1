package oop.ex5.filemanager;

import java.io.IOException;
import java.net.Socket;

import oop.ex5.common.ClientThread;
import oop.ex5.common.ClientThreadFactory;

public class FileManagerClientThreadFactory implements ClientThreadFactory {
	
	FileManagerData _data;
	
	public FileManagerClientThreadFactory(FileManagerData data) {
		_data = data;
	}

	@Override
	public ClientThread createClientThread(Socket socket) throws IOException {
		return new FileManagerClientThread(socket, _data);
	}

}
