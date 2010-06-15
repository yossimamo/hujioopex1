package oop.ex5.common;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import oop.ex5.messages.InvalidMessageFormatException;
import oop.ex5.messages.InvalidMessageNameException;
import oop.ex5.messages.Message;

public class CommLayer {
	
	private static final int TIMEOUT_MS = 5000;
	
	private Socket _socket;
	private DataInputStream _in;
	private DataOutputStream _out;
	
	// TODO can one constructor be called by the other?
	
	public CommLayer(String hostname, int port) throws IOException {
		Socket s = new Socket();
		s.connect(new InetSocketAddress(hostname, port), TIMEOUT_MS);
		initStreams();
	}
	
	public CommLayer(Socket socket) throws IOException {
		_socket = socket;
		_socket.setSoTimeout(TIMEOUT_MS);
		initStreams();
	}
	
	public void sendMessage(Message msg) throws IOException {
		msg.write(_out);
	}
	
	public Message receiveMessage()
		throws InvalidMessageFormatException, InvalidMessageNameException, IOException {
		return Message.read(_in);
	}
	
	public void close() {
		try {
			_in.close();
			_out.close();
			_socket.close();
		} catch (IOException e) {
			// Fail silently, there is nothing to do
		}
	}
	
	private void initStreams() throws IOException {
		_in = new DataInputStream(_socket.getInputStream());
		_out = new DataOutputStream(_socket.getOutputStream());
	}
	
}
