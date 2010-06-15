package oop.ex5.messages;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import oop.ex5.common.NameServer;

public class HaveNameServerMessage extends Message {
	
	protected static final String NAME = "HAVENAMESERVER";
	protected static final MessageType TYPE = MessageType.HAVENAMESERVER;
	
	private NameServer _nameServer;

	public HaveNameServerMessage(NameServer nameServer) {
		_nameServer = nameServer;
	}
	
	public HaveNameServerMessage(DataInputStream in)
			throws InvalidMessageFormatException, IOException {
		super(in);
	}
	
	public MessageType getType() {
		return TYPE;
	}
	
	public NameServer getNameServer() {
		return _nameServer;
	}
	
	@Override
	protected void readImp(DataInputStream in)
			throws InvalidMessageFormatException {
		try {
			String ip = in.readUTF();
			int port = in.readInt();
			_nameServer = new NameServer(ip, port);
		} catch (IOException e) {
			throw new InvalidMessageFormatException();
		}		
	}

	@Override
	protected void writeImp(DataOutputStream out) throws IOException {
		out.writeUTF(_nameServer.getIP());
		out.writeInt(_nameServer.getPort());
	}
	
	@Override
	protected String getName() {
		return NAME;
	}

}
