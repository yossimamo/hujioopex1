package oop.ex5.filemanager.scenarios;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import oop.ex5.common.CommLayer;
import oop.ex5.common.NameServer;
import oop.ex5.messages.*;
import oop.ex5.messages.Message.MessageType;
import oop.ex5.common.FileManager;
import oop.ex5.filemanager.FileManagerData;
import oop.ex5.filemanager.InvalidMessageContextException;

public class GetScenario extends Scenario {
	
	private String _fileName;

	public GetScenario(FileManagerData data, String fileName) {
		super(data);
		_fileName = fileName;
	}

	public void executeScenario() {
		if (_data.containsFile(_fileName)) {
			System.out.println("File is already in the database");
			return;
		}
		else {
			getFile();
			return;
		}
	}
		
	private void getFile() {
		Iterator<NameServer> serversIterator = _data.nameServersIterator();
		if (getFileLocationAndDownloadFile(serversIterator)) {
			return;
		}
		LinkedList<NameServer> newServers = new LinkedList<NameServer>();
		serversIterator = _data.nameServersIterator();
		while (serversIterator.hasNext()) {
			NameServer nameServer = serversIterator.next();
			try {
				_comm = new CommLayer(nameServer.getIP(), nameServer.getPort());
			} catch (IOException e) {
				// Move on to next server
				continue;
			}
			try {
				introduce();
				newServers.addAll(getNameServers());
				endSession();
			} catch (InvalidMessageFormatException e) {
				_comm.close();
				continue;
			} catch (InvalidMessageNameException e) {
				_comm.close();
				continue;
			} catch (IOException e) {
				_comm.close();
				continue;
			} catch (InvalidMessageContextException e) {
				_comm.close();
				continue;
			}
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
				// Move on to next server
				continue;
			}
			try {
				if (tryDownloadingFileUsingAServer()) {
					return true;
				}
			} catch (IOException e) {
				// Move on to next server
				continue;
			} catch (InvalidMessageFormatException e) {
				// Move on to next server
				continue;
			} catch (InvalidMessageNameException e) {
				// Move on to next server
				continue;
			}
		}
		return false;
	}

	private boolean tryDownloadingFileUsingAServer() throws IOException, InvalidMessageFormatException, InvalidMessageNameException {
		LinkedList<FileManager> fileManagers;
		try {
			introduce();
			fileManagers = getFileManagersHoldingFile();
			endSession();
		} catch (InvalidMessageContextException e) {
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
				_comm.close();
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
				FileOutputStream out = new FileOutputStream(new File(_data.generateLocalPath(_fileName)));
				out.write(msg.getFileContents());
				out.close();
				_data.addFile(_fileName);
				sendMsgToAllNameServers(new HaveFileMessage(_fileName));
				System.out.printf("File Downloaded Successfully from %s:%d\n", fileManager.getIP(), fileManager.getPort());
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
		Message incomingMessage = _comm.receiveMessage();
		if (MessageType.FILENOTFOUND != incomingMessage.getType()) {
			while (incomingMessage.getType() != Message.MessageType.LISTEND) {
				switch (incomingMessage.getType()) {
				case FILEADDRESS:
					FileAddressMessage incomingMsg = (FileAddressMessage) incomingMessage;
					fileManagers.add(incomingMsg.getFileManager());
					break;
				case LISTEND:
					break;
				default :
					throw new InvalidMessageContextException();
				}
				incomingMessage = _comm.receiveMessage();
			}
		}		
		return fileManagers;
	}

}
