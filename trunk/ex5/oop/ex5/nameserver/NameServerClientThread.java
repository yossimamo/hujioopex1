package oop.ex5.nameserver;

import java.net.Socket;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

import oop.ex5.common.ClientThread;
import oop.ex5.common.CommLayer;
import oop.ex5.common.NameServer;
import oop.ex5.messages.*;
import oop.ex5.messages.Message.MessageType;

public class NameServerClientThread extends ClientThread {
	
	private static final Message OK_MSG = new OkMessage();
	private static final Message ANNOUNCE_MSG = new AnnounceMessage();
	private static final Message ERROR_MSG = new ErrorMessage();
	private static final Message LISTEND_MSG = new ListEndMessage();
	
	private CommLayer _comm;
	private NameServerData _data;
	private FileManager _fm;

	public NameServerClientThread(Socket socket, NameServerData data) {
		_comm = new CommLayer(socket);
		_data = data;
	}
	
	@Override
	public void run() {
		_fm = initSession();
		// TODO condition
		while (!_shouldStop) {
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
				break;
			case KILL:
				handleKill();
				break;
			default:
				throw new SessionErrorException();			
			}
		}
	}

	private FileManager initSession() {
		Message rcvdMsg = _comm.receiveMessage();
		if (MessageType.INTRODUCE != rcvdMsg.getType()) {
			throw new SessionErrorException();
		}
		IntroduceMessage introduce = (IntroduceMessage)rcvdMsg;
		String ip = introduce.getFileManagerIP();
		int port = introduce.getFileManagerPort();
		if (!isKnownFileManager(ip, port)) {
			_comm.sendMessage(ANNOUNCE_MSG);
			return handleAnnounceReply(ip, port);
		} else {
			_comm.sendMessage(OK_MSG);
			return new FileManager(ip, port);
		}
	}
	
	private boolean isKnownFileManager(String ip, int port) {
		return _data.hasFileManager(ip, port);
	}
	
	private FileManager handleAnnounceReply(String ip, int port) {
		FileManager fm = _data.addFileManager(ip, port);
		boolean listEnd = false;
		while(!listEnd) {
			Message rcvdMsg = _comm.receiveMessage();
			MessageType msgType = rcvdMsg.getType(); 
			switch(msgType) {
			case HAVEFILE:
				HaveFileMessage haveFileMsg = (HaveFileMessage)rcvdMsg;
				_data.addFile(fm, haveFileMsg.getFileName());
				break;
			case LISTEND:
				listEnd = true;
				break;
			default:
				// TODO error
				break;
			}
			_comm.sendMessage(OK_MSG);
		}
		listEnd = false;
		while(!listEnd) {
			Message rcvdMsg = _comm.receiveMessage();
			MessageType msgType = rcvdMsg.getType(); 
			switch(msgType) {
			case HAVENAMESERVER:
				HaveNameServerMessage haveNSMsg = (HaveNameServerMessage)rcvdMsg;
				_data.addNameServer(haveNSMsg.getNameServerIP(), haveNSMsg.getNameServerPort());
				break;
			case LISTEND:
				listEnd = true;
				break;
			default:
				// TODO error
				break;
			}
			_comm.sendMessage(OK_MSG);
		}
		return fm;
	}

	private void handleDontHaveFile(Message rcvdMsg) {
		DontHaveFileMessage msg = (DontHaveFileMessage)rcvdMsg;
		_data.removeFile(_fm, msg.getFileName());
		_comm.sendMessage(OK_MSG);
	}
	
	private void handleHaveFile(Message rcvdMsg) {
		HaveFileMessage msg = (HaveFileMessage)rcvdMsg;
		_data.addFile(_fm, msg.getFileName());
		_comm.sendMessage(OK_MSG);
	}
	
	private void handleHaveNameServer(Message rcvdMsg) {
		HaveNameServerMessage msg = (HaveNameServerMessage)rcvdMsg;
		_data.addNameServer(msg.getNameServerIP(), msg.getNameServerPort());
		_comm.sendMessage(OK_MSG);
	}
	
	private void handleNeedFile(Message rcvdMsg) {
		NeedFileMessage msg = (NeedFileMessage)rcvdMsg;
		Iterator<FileManager> it = _data.getFileManagers(msg.getFileName());
		if (null != it) {
			while (it.hasNext()) {
				FileManager fm = it.next();
				FileAddressMessage fileAddressMsg = new FileAddressMessage(fm.getIP(), fm.getPort());
				_comm.sendMessage(fileAddressMsg);
			}
		}
		_comm.sendMessage(LISTEND_MSG);
	}
	
	private void handleNeedServers() {
		Iterator<NameServer> it = _data.getNameServers();
		if (null != it) {
			while (it.hasNext()) {
				NameServer ns = it.next();
				HaveNameServerMessage haveNSMsg = new HaveNameServerMessage(ns.getIP(), ns.getPort());
				_comm.sendMessage(haveNSMsg);
			}
		}
		_comm.sendMessage(LISTEND_MSG);
	}
	
	private void handleBye() {
		_data.clearFileManager(_fm);
		_comm.sendMessage(OK_MSG);
	}
	
	private void handleKill() {
		_comm.sendMessage(OK_MSG);
		// TODO kill
	}
	
}
