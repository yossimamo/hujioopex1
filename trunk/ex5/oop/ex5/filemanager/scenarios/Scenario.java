//###############  
// FILE : Scenario.java  
// WRITER : Uri Greenberg, urig03, 021986039  
// WRITER : Yossi Mamo, ymamo29, 038073722
// EXERCISE : oop ex5 2010  
// DESCRIPTION: An Abstract class representing a scenario of communication.
//###############

package oop.ex5.filemanager.scenarios;

import java.io.IOException;
import java.util.Iterator;

import oop.ex5.common.CommLayer;
import oop.ex5.common.NameServer;
import oop.ex5.filemanager.FileManagerData;
import oop.ex5.filemanager.InvalidMessageContextException;
import oop.ex5.messages.*;
import oop.ex5.messages.Message.MessageType;

/**
 * An Abstract class representing a scenario of communication.
 * @author Uri Greenberg and Yossi Mamo.
 *
 */
public abstract class Scenario {
	
	// The fileManagers database.
	protected FileManagerData _data;
	
	// The commLayer, responsible for all communications.
	protected CommLayer _comm;
	
	/**
	 * Constructs a new scenario, holding the database given as a parameter.
	 * @param data The fileManagers database.
	 */
	public Scenario(FileManagerData data) {
		_data = data;
	}
	
	/**
	 * Executes the Scenario.
	 */
	public abstract void executeScenario();
	
	/**
	 * Sends the given message to all servers known to the fileManager.
	 * Expects to receive an OK message after it sends the message.
	 * If any error occurs during the communication, it will just try the
	 * next server in the list.
	 * @param msg The message to send to all servers.
	 */
	protected void sendMsgToAllNameServers(Message msg) {
		Iterator<NameServer> it = _data.nameServersIterator();
		while (it.hasNext()) {
			NameServer nameServer = it.next();
			try {
				_comm = new CommLayer(nameServer.getIP(), nameServer.getPort());
			} catch (IOException e) {
				// move on to next server.
				//no need to close communication because it wasn't opened.
				continue;
			}
			try {
				introduce();
				_comm.sendMessage(msg);
				receiveOKMessage();
				endSession();
			} catch (IOException e) {
				// move on to next server.
				continue;
			} catch (InvalidMessageFormatException e) {
				// move on to next server.
				continue;
			} catch (InvalidMessageNameException e) {
				// move on to next server.
				continue;
			} catch (InvalidMessageContextException e) {
				// move on to next server.
				continue;
			} finally {
				_comm.close();
			}
		}
	}
	
	/**
	 * Introduce the fileManager to the server, and continues to announce
	 * in case the server does not know the fileManager.
	 * @throws IOException In case an IO exception occurs.
	 * @throws InvalidMessageFormatException In case the format of the message
	 * receives was invalid.
	 * @throws InvalidMessageNameException In case the name of the message
	 * receives is invalid.
	 * @throws InvalidMessageContextException In case the message received
	 * was received while not expected to (out of context).
	 */
	protected void introduce() throws IOException,
							InvalidMessageFormatException,
							InvalidMessageNameException,
							InvalidMessageContextException {
		_comm.sendMessage(new IntroduceMessage(_data.getSelfFileManager()));
		switch (_comm.receiveMessage().getType()) {
		case ANNOUNCE :
			announce();
			break;
		case OK :
			break;
		default :
			throw new InvalidMessageContextException();
		}
	}
	
	/**
	 * announces the fileManagers files and servers to a server.
	 * @throws IOException In case an IO error occurs.
	 * @throws InvalidMessageFormatException In case the format of the message
	 * receives was invalid.
	 * @throws InvalidMessageNameException In case the name of the message
	 * receives is invalid.
	 * @throws InvalidMessageContextException In case the message received
	 * was received while not expected to (out of context).
	 */
	private void announce() throws IOException, InvalidMessageFormatException,
									InvalidMessageNameException,
									InvalidMessageContextException {
		String[] files = _data.getFiles();
		for (int i=0 ; i < files.length ; i++) {
			_comm.sendMessage(new HaveFileMessage(files[i]));
			receiveOKMessage();
		}
		_comm.sendMessage(Message.LISTEND_MSG);
		receiveOKMessage();
		Iterator<NameServer> it = _data.nameServersIterator();
		while (it.hasNext()) {
			_comm.sendMessage(new HaveNameServerMessage(it.next()));
			receiveOKMessage();
		}
		_comm.sendMessage(Message.LISTEND_MSG);
		receiveOKMessage();
	}
	
	/**
	 * Receives a message and makes sure its an OK message. 
	 * @throws IOException In case an IO error occurs.
	 * @throws InvalidMessageFormatException In case the format of the message
	 * receives was invalid.
	 * @throws InvalidMessageNameException In case the name of the message
	 * receives is invalid.
	 * @throws InvalidMessageContextException In case the message received
	 * was received while not expected to (out of context).
	 */
	private void receiveOKMessage() throws InvalidMessageFormatException,
											InvalidMessageNameException,
											IOException,
											InvalidMessageContextException {
		if (_comm.receiveMessage().getType() != MessageType.OK) {
			throw new InvalidMessageContextException();
		}
	}
	
	/**
	 * Ends a session with a server by sending it  an end session message
	 * and closing the communication.
	 * @throws IOException In case an IO error occurs.
	 * @throws InvalidMessageFormatException In case the format of the message
	 * receives was invalid.
	 * @throws InvalidMessageNameException In case the name of the message
	 * receives is invalid.
	 * @throws InvalidMessageContextException In case the message received
	 * was received while not expected to (out of context).
	 */
	protected void endSession() throws IOException, InvalidMessageFormatException,
										InvalidMessageNameException,
										InvalidMessageContextException {
		_comm.sendMessage(Message.SESSION_END_MSG);
		receiveOKMessage();
		_comm.close();
	}

}
