//Doesn't need to handle multiple threads.

package oop.ex5.common;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import oop.ex5.messages.*;

import oop.ex5.messages.Message;

public class MessageSocket {

	private static final String MESSAGE_END = "END";
	private static final int TIME_OUT = 5000;
	private enum MessageType {
		ERROR,
		OK,
		ANNOUNCE,
		KILL,
		BYE,
		FILENOTFOUND,
		NEEDSERVERS,
		LISTEND,
		SESSIONEND,
		INTRODUCE,
		FILEADDRESS,
		HAVEFILE,
		DONTHAVEFILE,
		NEEDFILE,
		HAVENAMESERVER,
		FILE
	};
	
	private Socket _socket;
	private DataInputStream  _dataInputStream;
	private DataOutputStream _dataOutputStream;
	
	public MessageSocket(Socket socket) throws IOException {
		_socket = socket;
		_socket.setSoTimeout(TIME_OUT);
		_dataOutputStream = new DataOutputStream(socket.getOutputStream());
		_dataInputStream = new DataInputStream(socket.getInputStream());
	}
	
	public Message readNextMessage(){
		Message incomingMessage;
		try {
			String messageType = _dataInputStream.readUTF();
			switch (MessageType.valueOf(messageType)) {
			case ERROR :
				incomingMessage = new ErrorMessage();
				break;
			case OK :
				incomingMessage = new OkMessage();
				break;
			case ANNOUNCE :
				incomingMessage = new AnnounceMessage();
				break;
			case KILL :
				incomingMessage = new KillMessage();
				break;
			case BYE :
				incomingMessage = new ByeMessage();
				break;
			case FILENOTFOUND :
				incomingMessage = new FileNotFoundMessage();
				break;
			case NEEDSERVERS :
				incomingMessage = new NeedServersMessage();
				break;
			case LISTEND :
				incomingMessage = new ListEndMessage();
				break;
			case SESSIONEND :
				incomingMessage = new SessionEndMessage();
				break;
			case INTRODUCE :
				incomingMessage = new IntroduceMessage(_dataInputStream.readUTF(), _dataInputStream.readInt());
				break;
			case FILEADDRESS :
				incomingMessage = new FileAddressMessage(_dataInputStream.readUTF(), _dataInputStream.readInt());
				break;
			case HAVEFILE :
				incomingMessage = new HaveFileMessage(_dataInputStream.readUTF());
				break;
			case DONTHAVEFILE :
				incomingMessage = DontHaveHileMessage(_dataInputStream.readUTF());
				break;
			case NEEDFILE :
				incomingMessage = new NeedHileMessage(_dataInputStream.readUTF());
				break;
			case HAVENAMESERVER :
				incomingMessage = new getHaveNameServerMessage(_dataInputStream.readUTF(), _dataInputStream.readInt());
				break;
			case FILE :
				incomingMessage = getFileMessage();
				break;
			}
			if (!_dataInputStream.readUTF().equals(MESSAGE_END)) {
				throw new IllegalMessageException();
			}
			//TODO fix catch, and check if needed to catch IO
		} catch (IllegalArgumentException, IOException) {
			throw new IllegalMessageException();
		}
		return incomingMessage;
	}
	
	private Message getFileMessage() throws IOException {
		//TODO buffer and create a new message
		Byte[] br = new Byte[_dataInputStream.readLong()];
		
	}

	public void writeMassage(Message msg) {
		String messageName = msg.GetMessageName();
		MessageType messageType;
		try {
			messageType = MessageType.valueOf(messageName);
		} catch (IllegalArgumentException e) {
			throw new IllegalMessageException();
		}
		// if an error isn't thrown, such a message exist and we send its beginning.
		_dataOutputStream.writeUTF(messageName);
		switch (messageType) {
		case INTRODUCE :
			_dataOutputStream.writeUTF(msg.getFileManagerIP());
			_dataOutputStream.writeInt(msg.getFileManagerPort());
			break;
		case FILEADDRESS :
			_dataOutputStream.writeUTF(msg.getFileManagerIP());
			_dataOutputStream.writeInt(msg.getFileManagerPort());
			break;
		case HAVEFILE :
			_dataOutputStream.writeUTF(msg.getFileName());
			break;
		case DONTHAVEFILE :
			_dataOutputStream.writeUTF(msg.getFileName());
			break;
		case NEEDFILE :
			_dataOutputStream.writeUTF(msg.getFileName());
			break;
		case HAVENAMESERVER :
			_dataOutputStream.writeUTF(msg.getNameServerIP());
			_dataOutputStream.writeInt(msg.getNameServerPort());
			break;
		case FILE :
			//TODO
			break;
		}
		_dataOutputStream.writeUTF(MESSAGE_END);
	}
	
	public void close() throws IOException {
		_dataInputStream.close();
		_dataOutputStream.close();
		_socket.close();
	}

}
