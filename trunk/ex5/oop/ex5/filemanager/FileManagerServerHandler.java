package oop.ex5.filemanager;

import java.io.IOException;

import oop.ex5.common.IHandlesReceivedMassages;
import oop.ex5.common.ListeningThread;
import oop.ex5.common.ProcessThread;
import oop.ex5.messages.Message;

public class FileManagerServerHandler implements IHandlesReceivedMassages {
	
	private DataBase _dataBase;

	public FileManagerServerHandler(DataBase dataBase, int port) throws IOException {
		_dataBase = dataBase;
		new ListeningThread(port, this).start();
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

}
