package oop.ex5.nameserver;

import java.io.IOException;
import java.net.Socket;

import oop.ex5.common.ClientThread;
import oop.ex5.common.ClientThreadFactory;

public class NameServerClientThreadFactory implements ClientThreadFactory {
	
	private NameServerData _data;
	
	public NameServerClientThreadFactory(NameServerData data) {
		_data = data;
	}

	@Override
	public ClientThread createClientThread(Socket socket) throws IOException {
		return new NameServerClientThread(socket, _data);
	}

}
