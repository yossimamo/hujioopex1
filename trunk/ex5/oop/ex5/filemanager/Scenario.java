package oop.ex5.filemanager;

import java.io.IOException;
import java.util.Iterator;

import oop.ex5.common.CommLayer;
import oop.ex5.common.NameServer;
import oop.ex5.messages.*;
import oop.ex5.messages.Message.MessageType;

public abstract class Scenario {
	
	protected static final Message NO_MESSAGE = null;
	
	protected FileManagerData _data;
	
	protected CommLayer _comm;
	
	public Scenario(FileManagerData data) {
		_data = data;
	}
	
	public abstract void executeScenario();
	
	protected void sendMsgToAllNameServers(Message msg) {
		Iterator<NameServer> it = _data.nameServersIterator();
		while (it.hasNext()) {
			NameServer nameServer = it.next();
			try {
				_comm = new CommLayer(nameServer.getIP(), nameServer.getPort());
				introduce();
				if (msg != NO_MESSAGE) {
					_comm.sendMessage(msg);
					receiveOKMessage();
				}
			} catch (IOException e) {
				continue;
			} catch (InvalidMessageFormatException e) {
				continue;
			} catch (InvalidMessageNameException e) {
				continue;
			} catch (InvalidMessageContextException e) {
				continue;
			}
			_comm.close();
		}
	}
	
	private void introduce() throws IOException, InvalidMessageFormatException,
							InvalidMessageNameException, InvalidMessageContextException {
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
	
	private void announce() throws IOException, InvalidMessageFormatException, InvalidMessageNameException, InvalidMessageContextException {
		String[] files = _data.getFiles();
		for (int i=0 ; i < files.length ; i++) {
			_comm.sendMessage(new HaveFileMessage(files[i]));
			receiveOKMessage();
		}
		_comm.sendMessage(new ListEndMessage());
		receiveOKMessage();
		Iterator<NameServer> it = _data.nameServersIterator();
		while (it.hasNext()) {
			_comm.sendMessage(new HaveNameServerMessage(it.next()));
			receiveOKMessage();
		}
		_comm.sendMessage(new ListEndMessage());
		receiveOKMessage();
	}
	
	private void receiveOKMessage() throws InvalidMessageFormatException, InvalidMessageNameException, IOException, InvalidMessageContextException {
		if (_comm.receiveMessage().getType() != MessageType.OK) {
			throw new InvalidMessageContextException();
		}
	}

}
