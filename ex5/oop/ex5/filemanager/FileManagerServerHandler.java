package oop.ex5.filemanager;

import java.io.IOException;

import oop.ex5.common.IHandlesReceivedMassages;
import oop.ex5.common.ListeningThread;
import oop.ex5.common.ProcessThread;
import oop.ex5.messages.Message;

public class FileManagerServerHandler implements IHandlesReceivedMassages {
	
	private AbstractDataBase _dataBase;
	private ListeningThread _listeningThread;

	public FileManagerServerHandler(AbstractDataBase dataBase, int port) throws IOException {
		_dataBase = dataBase;
		_listeningThread =  new ListeningThread(port, this);
		_listeningThread.start();
	}

	public void HandleReceivedMessage(ProcessThread processThread,
			Message incomingMessage) {
		if (!incomingMessage.getID.equals("NEEDFILE")) {
			processThread.sendMessage(new ErrorMessage());
		}
		else {
			String fileName=  incomingMessage.getFileName();
			if (_dataBase.hasFile(fileName)) {
				processThread.sendMessage(new FileMessage(_dataBase.getFile(fileName)));
			}
			else {
				processThread.sendMessage(new FileNotFoundMessage());
			}
		}
	}
	
	public void shutDown() {
		_listeningThread.shutDown();
		try {
			_listeningThread.join();
		} catch (InterruptedException e) {
			//TODO shouldn't happen
			e.printStackTrace();
		}
	}

}
