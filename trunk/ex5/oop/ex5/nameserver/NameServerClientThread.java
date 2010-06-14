package oop.ex5.nameserver;

import java.net.Socket;

import oop.ex5.common.ClientThread;
import oop.ex5.common.CommLayer;
import oop.ex5.messages.*;
import oop.ex5.messages.Message.MessageType;

public class NameServerClientThread extends ClientThread {
	
	private static final Message OK_MSG = new OkMessage();
	private static final Message ANNOUNCE_MSG = new AnnounceMessage();
	private static final Message ERROR_MSG = new ErrorMessage();
	
	private CommLayer _comm;

	public NameServerClientThread(Socket socket, NameServerData data) {
		_comm = new CommLayer(socket);
	}
	
	@Override
	public void run() {
		// TODO change condition
		// TODO make constant Message objects
		while (!_shouldStop) {
			Message rcvdMsg = _comm.receiveMessage();
			MessageType msgType = rcvdMsg.getType(); 
			switch(msgType) {
			case DONTHAVEFILE:
				break;
			case HAVEFILE:
				break;
			case HAVENAMESERVER:
				break;
			case NEEDFILE:
				break;
			case NEEDSERVERS:
				break;
			case BYE:
				break;
			case KILL:
				break;
			default:
				// TODO error
				break;
				
			}
		}
	}
	
	private void initSession() {
		Message rcvdMsg = _comm.receiveMessage();
		if (MessageType.INTRODUCE != rcvdMsg.getType()) {
			// TODO error
		}
		IntroduceMessage introduce = (IntroduceMessage)rcvdMsg;
		String ip = introduce.getFileManagerIP();
		int port = introduce.getFileManagerPort();
		if (!isKnownFileManager(ip, port)) {
			_comm.sendMessage(ANNOUNCE_MSG);
			handleAnnounceReply();
		} else {
			_comm.sendMessage(OK_MSG);
		}
	}

	
}
