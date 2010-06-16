package oop.ex5.nameserver;

import java.io.IOException;
import java.net.Socket;
import java.util.Iterator;

import oop.ex5.common.ClientThread;
import oop.ex5.common.CommLayer;
import oop.ex5.common.FileManager;
import oop.ex5.common.NameServer;
import oop.ex5.messages.*;
import oop.ex5.messages.Message.MessageType;

public class NameServerClientThread extends ClientThread {
		
	private CommLayer _comm;
	private NameServerData _data;
	private FileManager _fm;

	public NameServerClientThread(Socket socket, NameServerData data)
		throws IOException {
		_comm = new CommLayer(socket);
		_data = data;
	}
	
	@Override
	public void run() {
		try {
			initSession();
			// TODO condition?
			while (!_data.getShutdownSignal()) {
				Message rcvdMsg = _comm.receiveMessage();
				MessageType msgType = rcvdMsg.getType(); 
				switch(msgType) {
				case DONTHAVEFILE:
					handleDontHaveFile(rcvdMsg);
					break;
				case HAVEFILE:
					handleHaveFile(rcvdMsg);
					break;
				case HAVENAMESERVER:
					handleHaveNameServer(rcvdMsg);
					break;
				case NEEDFILE:
					handleNeedFile(rcvdMsg);
					break;
				case NEEDSERVERS:
					handleNeedServers();
					break;
				case BYE:
					handleBye();
					throw new EndSessionException();
				case KILL:
					handleKill();
					throw new EndSessionException();
				case SESSIONEND:
					handleSessionEnd();
					throw new EndSessionException();
				default:
					throw new EndSessionException();			
				}
			}
		} catch (InvalidMessageFormatException e) {
			sendErrorMessage();
			// Let the finally block close the connection
		} catch (InvalidMessageNameException e) {
			sendErrorMessage();
			// Let the finally block close the connection
		} catch (EndSessionException e) {
			sendErrorMessage();
			// Let the finally block close the connection
		} catch (IOException e) {
			System.err.println("IOException during initSession\n");
			e.printStackTrace();
			sendErrorMessage();
			// Let the finally block close the connection
		} finally {
			_comm.close();
		}
	}

	private void sendErrorMessage() {
		// Try to send error message and ignore IO exceptions
		// which can't be handled anyway
		try {
			_comm.sendMessage(Message.ERROR_MSG);
		} catch (IOException e) {
			// Fail silently
		}
	}

	private void initSession()
		throws InvalidMessageFormatException, InvalidMessageNameException, IOException, EndSessionException {
		Message rcvdMsg = _comm.receiveMessage();
		if (MessageType.INTRODUCE != rcvdMsg.getType()) {
			throw new EndSessionException();
		}
		IntroduceMessage introduce = (IntroduceMessage)rcvdMsg;
		_fm = introduce.getFileManager();
		if (! _data.hasFileManager(_fm)) {
			_comm.sendMessage(Message.ANNOUNCE_MSG);
			handleAnnounceReply(_fm);
		} else {
			_comm.sendMessage(Message.OK_MSG);
		}
	}
	
	private void handleAnnounceReply(FileManager fm)
		throws InvalidMessageFormatException, InvalidMessageNameException, IOException, EndSessionException {
		_data.addFileManager(fm);
		boolean listEnd = false;
		while(!listEnd) {
			Message rcvdMsg = _comm.receiveMessage();
			MessageType msgType = rcvdMsg.getType(); 
			switch(msgType) {
			case HAVEFILE:
				handleHaveFile(rcvdMsg);
				break;
			case LISTEND:
				listEnd = true;
				_comm.sendMessage(Message.OK_MSG);
				break;
			default:
				throw new EndSessionException();
			}
		}
		listEnd = false;
		while(!listEnd) {
			Message rcvdMsg = _comm.receiveMessage();
			MessageType msgType = rcvdMsg.getType(); 
			switch(msgType) {
			case HAVENAMESERVER:
				handleHaveNameServer(rcvdMsg);
				break;
			case LISTEND:
				listEnd = true;
				_comm.sendMessage(Message.OK_MSG);
				break;
			default:
				throw new EndSessionException();
			}
			
		}
	}

	private void handleDontHaveFile(Message rcvdMsg) throws IOException {
		DontHaveFileMessage msg = (DontHaveFileMessage)rcvdMsg;
		_data.removeFile(_fm, msg.getFileName());
		_comm.sendMessage(Message.OK_MSG);
	}
	
	private void handleHaveFile(Message rcvdMsg) throws IOException {
		HaveFileMessage msg = (HaveFileMessage)rcvdMsg;
		_data.addFile(_fm, msg.getFileName());
		_comm.sendMessage(Message.OK_MSG);
	}
	
	private void handleHaveNameServer(Message rcvdMsg) throws IOException {
		HaveNameServerMessage msg = (HaveNameServerMessage)rcvdMsg;
		NameServer ns = msg.getNameServer();
		_data.addNameServer(ns);
		_comm.sendMessage(Message.OK_MSG);
	}
	
	private void handleNeedFile(Message rcvdMsg) throws IOException {
		NeedFileMessage msg = (NeedFileMessage)rcvdMsg;
		Iterator<FileManager> it = _data.getFileManagers(msg.getFileName());
		if (null != it) {
			while (it.hasNext()) {
				FileManager fm = it.next();
				FileAddressMessage fileAddressMsg = new FileAddressMessage(fm);
				_comm.sendMessage(fileAddressMsg);
			}
		}
		_comm.sendMessage(Message.LISTEND_MSG);
	}
	
	private void handleNeedServers() throws IOException {
		Iterator<NameServer> it = _data.getNameServers();
		if (null != it) {
			while (it.hasNext()) {
				NameServer ns = it.next();
				HaveNameServerMessage haveNSMsg = new HaveNameServerMessage(ns);
				_comm.sendMessage(haveNSMsg);
			}
		}
		_comm.sendMessage(Message.LISTEND_MSG);
	}
	
	private void handleBye() throws IOException {
		_data.clearFileManager(_fm);
		_comm.sendMessage(Message.OK_MSG);
	}
	
	private void handleKill() throws IOException {
		_comm.sendMessage(Message.OK_MSG);
		_data.setShutdownSignal(true);
	}
	
	private void handleSessionEnd() throws IOException {
		_comm.sendMessage(Message.OK_MSG);
	}
	
}
