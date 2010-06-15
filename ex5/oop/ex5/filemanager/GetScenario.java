package oop.ex5.filemanager;

import java.io.FileOutputStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import oop.ex5.common.CommLayer;
import oop.ex5.common.NameServer;
import oop.ex5.messages.*;
import oop.ex5.common.FileManager;

public class GetScenario extends Scenario {
	
	private String _fileName;

	public GetScenario(FileManagerData data, String fileName) {
		super(data);
		_fileName = fileName;
	}

	public void executeScenario() {
		Iterator<NameServer> serversIterator = _data.nameServersIterator();
		if (findAndDownloadFile(serversIterator)) {
			return;
		}
		LinkedList<NameServer> newServers = new LinkedList<NameServer>();
		while (serversIterator.hasNext()) {
			NameServer nameServer = serversIterator.next();
			CommLayer comm = new CommLayer(nameServer.getIP(), nameServer.getPort());
			newServers.addAll(getNameServers(comm));
			comm.close();
		}
		_data.addAllServers(newServers);
		if (findAndDownloadFile(newServers.iterator())) {
			return;
		}
		//TODO throw
	}

	private LinkedList<NameServer> getNameServers(CommLayer comm) {
		LinkedList<NameServer> newNameServers = new LinkedList<NameServer>();
		comm.sendMessage(new NeedServersMessage());
		Message incomingMessage;
		do {
			incomingMessage = comm.receiveMessage();
			switch (incomingMessage.getType()) {
			case HAVENAMESERVER:
				HaveNameServerMessage incomingMsg = (HaveNameServerMessage) incomingMessage;
				if (!_data.containsNameServer(incomingMsg.getNameServer())) {
					newNameServers.add(incomingMsg.getNameServer());
				}
				break;
			case LISTEND :
				break;
			default :
				//TODO
			}
		} while (incomingMessage.getType() != Message.MessageType.LISTEND);
		return newNameServers;
	}

	private boolean findAndDownloadFile(Iterator<NameServer> serversIterator) {
		while (serversIterator.hasNext()) {
			NameServer nameServer = serversIterator.next();
			CommLayer comm = new CommLayer(nameServer.getIP(), nameServer.getPort());
			LinkedList<FileManager> fileManagers = getFileManagersHoldingFile(comm);
			comm.close();
			Iterator<FileManager> fileManagersIterator = fileManagers.iterator();
			while (fileManagersIterator.hasNext()) {
				if (downloadFile(fileManagersIterator.next())) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean downloadFile(FileManager fileManager) {
		CommLayer comm = new CommLayer(fileManager.getIP(), fileManager.getPort());
		comm.sendMessage(new NeedFileMessage(_fileName));
		Message incomingMessage = comm.receiveMessage();
		comm.close();
		switch (incomingMessage.getType()) {
		case FILE :
			FileMessage msg = (FileMessage) incomingMessage;
			FileOutputStream out = new FileOutputStream(new java.io.File(_data.getFileObject(_fileName).getLocalPath()));
			out.write(msg.getFileContents());
			out.close();
			_data.addFile(_fileName);
			return true;
		default :
			return false;
		}
	}

	private LinkedList<FileManager> getFileManagersHoldingFile(CommLayer comm) {
		LinkedList<FileManager> fileManagers = new LinkedList<FileManager>();
		comm.sendMessage(new NeedFileMessage(_fileName));
		Message incomingMessage;
		do {
			incomingMessage = comm.receiveMessage();
			switch (incomingMessage.getType()) {
			case FILEADDRESS:
				FileAddressMessage incomingMsg = (FileAddressMessage) incomingMessage;
				fileManagers.add(incomingMsg.getFileManager());
				break;
			case LISTEND :
				break;
			default :
				//TODO
			}
		} while (incomingMessage.getType() != Message.MessageType.LISTEND);
		return fileManagers;
	}

}
