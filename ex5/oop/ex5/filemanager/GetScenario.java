package oop.ex5.filemanager;

import java.io.FileOutputStream;
import java.io.IOException;
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
		if (getFileLocationAndDownloadFile(serversIterator)) {
			return;
		}
		LinkedList<NameServer> newServers = new LinkedList<NameServer>();
		while (serversIterator.hasNext()) {
			NameServer nameServer = serversIterator.next();
			try {
				_comm = new CommLayer(nameServer.getIP(), nameServer.getPort());
			} catch (IOException e) {
				e.printStackTrace(); //TODO remove
				continue;
			}
			try {
				newServers.addAll(getNameServers());
			} catch (InvalidMessageFormatException e) {
				e.printStackTrace(); //TODO remove
				_comm.close();
				continue;
			} catch (InvalidMessageNameException e) {
				e.printStackTrace(); //TODO remove
				_comm.close();
				continue;
			} catch (IOException e) {
				e.printStackTrace(); //TODO remove
				_comm.close();
				continue;
			} catch (InvalidMessageContextException e) {
				e.printStackTrace(); //TODO remove
				_comm.close();
				continue;
			}
			_comm.close();
		}
		_data.addAllNameServers(newServers);
		if (getFileLocationAndDownloadFile(newServers.iterator())) {
			return;
		}
		System.out.println("Downloading failed");
	}

	private LinkedList<NameServer> getNameServers()
		throws InvalidMessageFormatException, InvalidMessageNameException, IOException, InvalidMessageContextException {
		LinkedList<NameServer> newNameServers = new LinkedList<NameServer>();
		_comm.sendMessage(Message.NEED_SERVERS_MSG);
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
				throw new InvalidMessageContextException();
			}
		} while (incomingMessage.getType() != Message.MessageType.LISTEND);
		return newNameServers;
	}

	private boolean getFileLocationAndDownloadFile(Iterator<NameServer> serversIterator) {
		while (serversIterator.hasNext()) {
			NameServer nameServer = serversIterator.next();
			try {
				_comm = new CommLayer(nameServer.getIP(), nameServer.getPort());
			} catch (IOException e) {
				e.printStackTrace(); //TODO remove
				continue;
			}
			try {
				if (tryDownloadingFileUsingAServer()) {
					return true;
				}
			} catch (IOException e) {
				e.printStackTrace(); //TODO remove
				continue;
			} catch (InvalidMessageFormatException e) {
				e.printStackTrace(); //TODO remove
				continue;
			} catch (InvalidMessageNameException e) {
				e.printStackTrace(); //TODO remove
				continue;
			}
		}
		return false;
	}

	private boolean tryDownloadingFileUsingAServer() throws IOException, InvalidMessageFormatException, InvalidMessageNameException {
		LinkedList<FileManager> fileManagers;
		try {
			fileManagers = getFileManagersHoldingFile();
			endSession();
		} catch (InvalidMessageContextException e) {
			e.printStackTrace(); //TODO remove
			_comm.close();
			return false;
		}
		Iterator<FileManager> fileManagersIterator = fileManagers.iterator();
		while (fileManagersIterator.hasNext()) {
			if (downloadFile(fileManagersIterator.next())) {
				return true;
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
				endSession();				
			} catch (IOException e) {
				e.printStackTrace(); //TODO remove
				_comm.close();
				return false;
			} catch (InvalidMessageFormatException e) {
				e.printStackTrace(); //TODO remove
				_comm.close();
				return false;
			} catch (InvalidMessageNameException e) {
				e.printStackTrace(); //TODO remove
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
			e.printStackTrace(); //TODO remove
			return false;
		}				
	}

	private LinkedList<FileManager> getFileManagersHoldingFile()
		throws IOException, InvalidMessageFormatException, InvalidMessageNameException, InvalidMessageContextException {
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
				throw new InvalidMessageContextException();
			}
		} while (incomingMessage.getType() != Message.MessageType.LISTEND);
		return fileManagers;
	}

}
