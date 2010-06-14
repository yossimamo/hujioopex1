package oop.ex5.messages;

import java.io.DataInputStream;

public class ByeMessage extends Message {

	public ByeMessage(DataInputStream in) throws InvalidMessageFormatException {
		super(in);
	}

	@Override
	protected void readFromStream(DataInputStream in)
			throws InvalidMessageFormatException {
		// Intentionally left blank		
	}

}
