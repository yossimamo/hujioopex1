package oop.ex5.filemanager;

import java.io.IOException;
import java.net.Socket;

import oop.ex5.common.ClientThread;
import oop.ex5.common.CommLayer;
import oop.ex5.messages.*;
import oop.ex5.messages.Message.MessageType;

public class FileManagerClientThread extends ClientThread {
	
	FileManagerData _data;
	CommLayer _comm;
	
	public FileManagerClientThread(Socket socket, FileManagerData data)
	throws IOException {
		_comm = new CommLayer(socket);
		_data = data;
	}
	
	@Override
	public void run() {
		try {
			Message rcvdMsg = _comm.receiveMessage();
			MessageType msgType = rcvdMsg.getType();
			switch(msgType) {
			case NEEDFILE:
				NeedFileMessage msg = (NeedFileMessage)rcvdMsg;
				handleFileRequest(msg.getFileName());
				break;
			default:
				sendErrorMessage();
			}
		} catch (InvalidMessageFormatException e) {
			sendErrorMessage();
		} catch (InvalidMessageNameException e) {
			sendErrorMessage();
		} catch (IOException e) {
			sendErrorMessage();
		} finally {
			_comm.close();
		}
	}

	private void handleFileRequest(String fileName) {
		if (_data.hasFile(fileName)) {
			_comm.sendMessage(new FileMessage(_data.getFileObject(fileName)));
		} else {
			_comm.sendMessage(Message.ERROR_MSG);
		}	
	}
	
	private void sendErrorMessage() {
		try {
			_comm.sendMessage(Message.ERROR_MSG);
		} catch (IOException e) {
			// Fail silently, nothing to do
		}		
	}

}
