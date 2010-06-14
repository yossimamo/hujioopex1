package oop.ex5.common;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.Socket;

import oop.ex5.messages.Message;

public class ProcessThread extends Thread {

	
	private MessageSocket _msgSocket;
	private IHandlesReceivedMassages _serverLogicHandler;
	private boolean _shutDown = false;

	public ProcessThread(Socket socket, IHandlesReceivedMassages serverLogicHandler) throws IOException {
		_msgSocket = new MessageSocket(socket);
		_serverLogicHandler = serverLogicHandler;
		
	}
	
	public void run() {
		
		try {
			while(!_shutDown) {
				Message incomingMessage = _msgSocket.readNextMessage();
				_serverLogicHandler.HandleReceivedMessage(this, incomingMessage);
			}
		} catch (IllegalMessageException e) {
			_serverLogicHandler.HandleReceivedMessage(this, new ErrorMessage());
		}
		catch (InterruptedIOException e) {
			
		}
		
		finally {
			try {
				_msgSocket.close();
			} catch (IOException e) {
				
			}
		}
	}
	
	public void sendMessage(Message message) {
		_msgSocket.writeMassage(message);
	}
	
	public void shutDown() {
		_shutDown = true;
	}
	
}
