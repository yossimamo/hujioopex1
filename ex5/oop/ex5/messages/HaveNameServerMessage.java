package oop.ex5.messages;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import oop.ex5.common.NameServer;

public class HaveNameServerMessage extends Message {
	
	protected static final String NAME = "HAVENAMESERVER";
	protected static final MessageType TYPE = MessageType.HAVENAMESERVER;
	
	private String _nameServerIP;
	private int _nameServerPort;

	public HaveNameServerMessage(NameServer nameServer) {
		_nameServerIP = nameServer.getIP();
		_nameServerPort = nameServer.getPort();
	}
	
	public HaveNameServerMessage(DataInputStream in)
			throws InvalidMessageFormatException, IOException {
		super(in);
	}
	
	public MessageType getType() {
		return TYPE;
	}
	
	public String getNameServerIP() {
		return _nameServerIP;
	}
	
	public int getNameServerPort() {
		return _nameServerPort;
	}

	@Override
	protected void readImp(DataInputStream in)
			throws InvalidMessageFormatException {
		try {
			_nameServerIP = in.readUTF();
			_nameServerPort = in.readInt();
		} catch (IOException e) {
			throw new InvalidMessageFormatException();
		}		
	}

	@Override
	protected void writeImp(DataOutputStream out) throws IOException {
		out.writeUTF(_nameServerIP);
		out.writeInt(_nameServerPort);
	}
	
	@Override
	protected String getName() {
		return NAME;
	}

}
