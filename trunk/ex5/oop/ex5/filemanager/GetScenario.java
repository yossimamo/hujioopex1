package oop.ex5.filemanager;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import oop.ex5.common.CommLayer;
import oop.ex5.common.NameServer;
import oop.ex5.messages.*;
import oop.ex5.common.FileManager;

public class GetScenario extends Scenario {
	
	private String _fileName;
	private CommLayer _comm;

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
			_comm = new CommLayer(nameServer.getIP(), nameServer.getPort());
			newServers.addAll(getNameServers());
			_comm.close();
		}
		_data.addAllServers(newServers);
		if (findAndDownloadFile(newServers.iterator())) {
			return;
		}
		//TODO throw
	}

	private LinkedList<NameServer> getNameServers()
		throws InvalidMessageFormatException, InvalidMessageNameException, IOException {
		LinkedList<NameServer> newNameServers = new LinkedList<NameServer>();
		_comm.sendMessage(new NeedServersMessage());
		Message incomingMessage;
		do {
			incomingMessage = _comm.receiveMessage();
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
			try {
				_comm = new CommLayer(nameServer.getIP(), nameServer.getPort());
			} catch (IOException e) {
				continue;
			}
			try {
				LinkedList<FileManager> fileManagers = getFileManagersHoldingFile();
				Iterator<FileManager> fileManagersIterator = fileManagers.iterator();
				while (fileManagersIterator.hasNext()) {
					if (downloadFile(fileManagersIterator.next())) {
						return true;
					}
				}
			} catch (IOException e) {
				continue;
			} catch (InvalidMessageFormatException e) {
				continue;
			} catch (InvalidMessageNameException e) {
				continue;
			}
		}
		return false;
	}

	private boolean downloadFile(FileManager fileManager) {
		try {
			_comm = new CommLayer(fileManager.getIP(), fileManager.getPort());
			Message incomingMessage;
			try {
				_comm.sendMessage(new NeedFileMessage(_fileName));
				incomingMessage = _comm.receiveMessage();
				_comm.close();				
			} catch (IOException e) {
				_comm.close();
				return false;
			} catch (InvalidMessageFormatException e) {
				_comm.close();
				return false;
			} catch (InvalidMessageNameException e) {
				_comm.close();
				return false;
			}
			switch (incomingMessage.getType()) {
			case FILE:
				FileMessage msg = (FileMessage) incomingMessage;
				FileOutputStream out = new FileOutputStream(new java.io.File(_data.getFileObject(_fileName).getLocalPath()));
				out.write(msg.getFileContents());
				out.close();
				_data.addFile(_fileName);
				return true;
			default:
				return false;
			}	
		} catch (IOException e) {
			return false;
		}				
	}

	private LinkedList<FileManager> getFileManagersHoldingFile()
		throws IOException, InvalidMessageFormatException, InvalidMessageNameException {
		LinkedList<FileManager> fileManagers = new LinkedList<FileManager>();
		_comm.sendMessage(new NeedFileMessage(_fileName));
		Message incomingMessage;
		do {
			incomingMessage = _comm.receiveMessage();
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
