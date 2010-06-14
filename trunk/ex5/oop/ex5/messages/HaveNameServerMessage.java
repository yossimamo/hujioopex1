package oop.ex5.messages;

import java.io.DataInputStream;
import java.io.IOException;

public class HaveNameServerMessage extends Message {
	
	private String _nameServerIP;
	private int _nameServerPort;

	public HaveNameServerMessage(DataInputStream in)
			throws InvalidMessageFormatException {
		super(in);
	}

	@Override
	protected void readFromStream(DataInputStream in)
			throws InvalidMessageFormatException {
		try {
			_nameServerIP = in.readUTF();
			_nameServerPort = in.readInt();
		} catch (IOException e) {
			throw new InvalidMessageFormatException();
		}		
	}

}
