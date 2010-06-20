//###############  
// FILE : GetScenario.java  
// WRITER : Uri Greenberg, urig03, 021986039  
// WRITER : Yossi Mamo, ymamo29, 038073722
// EXERCISE : oop ex5 2010  
// DESCRIPTION: A scenario in which the fileManager needs to obtain a given
// file.
//###############

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

/**
 * A scenario in which the fileManager needs to obtain a given
 * file.
 * @author Uri Greenberg and Yossi Mamo.
 *
 */
public class GetScenario extends Scenario {
	
	// The files name
	private String _fileName;

	/**
	 * Constructs a new GetScenario with the file managers data and the 
	 * desired file name.
	 * @param data The file managers data.
	 * @param fileName The desired file name.
	 */
	public GetScenario(FileManagerData data, String fileName) {
		super(data);
		_fileName = fileName;
	}

	/*
	 * (non-Javadoc)
	 * @see oop.ex5.filemanager.scenarios.Scenario#executeScenario()
	 */
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
		
	/**
	 * Tries to download the file from the file managers known to the servers
	 * from the database. If it fails, we get new servers and search the file 
	 * using them. We continue doing so until there are no more new servers
	 * or until the file is found.
	 */
	private void getFile() {
		// Try to get the file from one of the file managers known to the
		// servers in the data base.
		Iterator<NameServer> serversIterator = _data.nameServersIterator();
		if (getFileLocationAndDownloadFile(serversIterator)) {
			return;
		}
		// Get more name servers and search using them. until the file is found
		// or there are no more name servers.
		LinkedList<NameServer> newServers;
		Iterator<NameServer> oldServersIterator = _data.nameServersIterator();
		do {
			newServers = getNewServers(oldServersIterator);
			// Try to get the file from one of the file managers known to the
			// new servers.
			_data.addAllNameServers(newServers);
			if (getFileLocationAndDownloadFile(newServers.iterator())) {
				return;
			}
			oldServersIterator = newServers.iterator();
		} while (!newServers.isEmpty());
		System.out.println("Downloading failed");
	}

	/**
	 * Receives an iterator of servers and returns a LinkedList of new servers
	 * known to the servers in the iterator and that are not in the database.
	 * The new servers are saved in the database.
	 * @param oldServersIterator An iterator on the servers in the database
	 * that hasn't been asked for all the servers they know.
	 * @return A LinkedList of new servers
	 * known to the servers in the iterator and that are not in the database.
	 */
	private LinkedList<NameServer> getNewServers(
			Iterator<NameServer> oldServersIterator) {
		LinkedList<NameServer> newServers = new LinkedList<NameServer>();
		while (oldServersIterator.hasNext()) {
			NameServer nameServer = oldServersIterator.next();
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
				// Move on to next server
				continue;
			} catch (InvalidMessageNameException e) {
				// Move on to next server
				continue;
			} catch (IOException e) {
				// Move on to next server
				continue;
			} catch (InvalidMessageContextException e) {
				// Move on to next server
				continue;
			} finally {
				_comm.close();
			}
		}
		return newServers;
	}

	/**
	 * gets all the servers known to the nameServer currently connected in
	 * the _comm, that aren't already in the file managers data base.
	 * @return all the servers known to the nameServer currently connected in
	 * the _comm, that aren't already in the file managers data base.
	 * @throws IOException In case an IO error occurs.
	 * @throws InvalidMessageFormatException In case the format of the message
	 * receives was invalid.
	 * @throws InvalidMessageNameException In case the name of the message
	 * receives is invalid.
	 * @throws InvalidMessageContextException In case the message received
	 * was received while not expected to (out of context).
	 */
	private LinkedList<NameServer> getNameServers()
		throws InvalidMessageFormatException, InvalidMessageNameException,
								IOException, InvalidMessageContextException {
		LinkedList<NameServer> newNameServers = new LinkedList<NameServer>();
		_comm.sendMessage(Message.NEED_SERVERS_MSG);
		Message incomingMessage;
		do {
			incomingMessage = _comm.receiveMessage();
			switch (incomingMessage.getType()) {
			case HAVENAMESERVER:
				HaveNameServerMessage incomingMsg =
					(HaveNameServerMessage) incomingMessage;
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

	/**
	 * Receives an iterator of name servers and returns true if it succeeded
	 * downloading the file from one of the file managers in one of the servers
	 * in the iterator or false otherwise.
	 * @param serversIterator An iterator of servers.
	 * @return true if it succeeded downloading the file from one of the file
	 * managers in one of the servers in the iterator or false otherwise.
	 */
	private boolean getFileLocationAndDownloadFile(Iterator<NameServer> serversIterator) {
		while (serversIterator.hasNext()) {
			NameServer nameServer = serversIterator.next();
			try {
				_comm = new CommLayer(nameServer.getIP(), nameServer.getPort());
			} catch (IOException e) {
				// Move on to next server
				continue;
			}
			if (tryDownloadingFileUsingAServer()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * tries downloading the file from one of the file managers known to the
	 * server currently connected in the _comm.
	 * @return true if it succeeded downloading the file or false otherwise.
	 */
	private boolean tryDownloadingFileUsingAServer() {
		// get all file managers holding the file known to current server.
		LinkedList<FileManager> fileManagers;
		try {
			introduce();
			fileManagers = getFileManagersHoldingFile();
			endSession();
		} catch (InvalidMessageContextException e) {
			return false;
		} catch (IOException e) {
			return false;			
		} catch (InvalidMessageFormatException e) {
			return false;			
		} catch (InvalidMessageNameException e) {
			return false;			
		} finally {
			_comm.close();
		}
		//try downloading the file from the file managers.
		Iterator<FileManager> fileManagersIterator = fileManagers.iterator();
		while (fileManagersIterator.hasNext()) {
			if (downloadFile(fileManagersIterator.next())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Receives a file manager and tries to download the file from it.
	 * @param fileManager a file manager.
	 * @return true if the file was successfully downloaded or false otherwise.
	 */
	private boolean downloadFile(FileManager fileManager) {
		try {
			_comm = new CommLayer(fileManager.getIP(), fileManager.getPort());
			Message incomingMessage;
			try {
				_comm.sendMessage(new NeedFileMessage(_fileName));
				incomingMessage = _comm.receiveMessage();	
			} catch (IOException e) {
				return false;
			} catch (InvalidMessageFormatException e) {
				return false;
			} catch (InvalidMessageNameException e) {
				return false;
			} finally {
				_comm.close();
			}
			switch (incomingMessage.getType()) {
			case FILE:
				FileMessage msg = (FileMessage) incomingMessage;
				// write the file to the computer.
				FileOutputStream out =
					new FileOutputStream(new File(_data.generateLocalPath(_fileName)));
				out.write(msg.getFileContents());
				out.close();
				// add the file to the database and update all nameservers.
				_data.addFile(_fileName);
				sendMsgToAllNameServers(new HaveFileMessage(_fileName));
				System.out.printf("File Downloaded Successfully from %s:%d\n",
						fileManager.getIP(), fileManager.getPort());
				return true;
			default:
				return false;
			}	
		} catch (IOException e) {
			return false;
		}				
	}

	/**
	 * Returns a linkedlist of all the fileManagers ,known to the server currently
	 * connected in the _comm, holding the file.
	 * @return A linkedlist of all the fileManagers ,known to the server currently
	 * connected in the _comm, holding the file.
	 * @throws IOException In case an IO error occurs.
	 * @throws InvalidMessageFormatException In case the format of the message
	 * receives was invalid.
	 * @throws InvalidMessageNameException In case the name of the message
	 * receives is invalid.
	 * @throws InvalidMessageContextException In case the message received
	 * was received while not expected to (out of context).
	 */
	private LinkedList<FileManager> getFileManagersHoldingFile()
		throws IOException, InvalidMessageFormatException,
		InvalidMessageNameException,
		InvalidMessageContextException {
		LinkedList<FileManager> fileManagers = new LinkedList<FileManager>();
		_comm.sendMessage(new NeedFileMessage(_fileName));
		Message incomingMessage = _comm.receiveMessage();
		if (MessageType.FILENOTFOUND != incomingMessage.getType()) {
			while (incomingMessage.getType() != MessageType.LISTEND) {
				switch (incomingMessage.getType()) {
				case FILEADDRESS:
					FileAddressMessage incomingMsg =
						(FileAddressMessage) incomingMessage;
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
