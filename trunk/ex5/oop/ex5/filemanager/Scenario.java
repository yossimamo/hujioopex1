package oop.ex5.filemanager;

import java.util.Iterator;

import oop.ex5.common.CommLayer;
import oop.ex5.common.NameServer;
import oop.ex5.messages.*;
import oop.ex5.messages.Message.MessageType;

public abstract class Scenario {
	protected static final Message NO_MESSAGE = null;
	
	protected AbstractDataBase _dataBase;
	
	public Scenario(AbstractDataBase dataBase) {
		_dataBase = dataBase;
	}
	public abstract void executeScenario();
	
	protected void sendMsgToAllNameServers(Message msg) {
		Iterator<NameServer> it = _dataBase.nameServersIterator();
		while (it.hasNext()) {
			NameServer nameServer = it.next();
			CommLayer comm = new CommLayer(nameServer.getIP(), nameServer.getPort());
			introduce(comm);
			if (msg != NO_MESSAGE) {
				comm.sendMessage(msg);
				receiveOKMessage(comm);
			}
			comm.close();
		}
	}
	
	protected void introduce(CommLayer comm) {
		comm.sendMessage(new IntroduceMessage(_dataBase.getIP(), _dataBase.getPort()));
		switch (comm.receiveMessage().getType()) {
		case ANNOUNCE :
			announce(comm);
			break;
		case OK :
			break;
		default :
			throw new //TODO
			
		}
	}
	private void announce(CommLayer comm) {
		String[] files = _dataBase.getFiles();
		for (int i=0 ; i < files.length ; i++) {
			comm.sendMessage(new HaveFileMessage(files[i]));
			receiveOKMessage(comm);
		}
		comm.sendMessage(new ListEndMessage());
		receiveOKMessage(comm);
		Iterator<NameServer> it = _dataBase.nameServersIterator();
		while (it.hasNext()) {
			comm.sendMessage(new HaveNameServerMessage(it.next()));
			receiveOKMessage(comm);
		}
		comm.sendMessage(new ListEndMessage());
		receiveOKMessage(comm);
	}
	private void receiveOKMessage(CommLayer comm) {
		if (comm.receiveMessage().getType() != MessageType.OK) {
			throw new //TODO
		}
	}

}
