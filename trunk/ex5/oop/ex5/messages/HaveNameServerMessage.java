package oop.ex5.messages;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class HaveNameServerMessage extends Message {
	
	protected static final String NAME = "HAVENAMESERVER";
	
	private String _nameServerIP;
	private int _nameServerPort;

	public HaveNameServerMessage(DataInputStream in)
			throws InvalidMessageFormatException, IOException {
		super(in);
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
