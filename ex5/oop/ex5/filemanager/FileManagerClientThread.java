package oop.ex5.filemanager;

import java.io.File;
import java.io.FileInputStream;
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

	private void handleFileRequest(String fileName) throws IOException {
		//TODO use the synchronized File 
		if (_data.containsFile(fileName)) {
			SynchronizedFile syncFile = _data.getFileObject(fileName);
			File file = new File(syncFile.getLocalPath());
			byte[] fileContents = new byte[(int)file.length()];
			try {
				FileInputStream in = new FileInputStream(file);
				in.read(fileContents);
				in.close();
			} catch (IOException e) {
				_comm.sendMessage(Message.ERROR_MSG);
			}
			_comm.sendMessage(new FileMessage(fileContents));
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
