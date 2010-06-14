package oop.ex5.messages;

import java.io.DataInputStream;

public class OkMessage extends Message {

	public OkMessage(DataInputStream in) throws InvalidMessageFormatException {
		super(in);
	}

	@Override
	protected void readFromStream(DataInputStream in)
			throws InvalidMessageFormatException {
		// Intentionally left blank
	}

}
