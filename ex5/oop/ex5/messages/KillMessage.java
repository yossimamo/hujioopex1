package oop.ex5.messages;

import java.io.DataInputStream;

public class KillMessage extends Message {

	public KillMessage(DataInputStream in) throws InvalidMessageFormatException {
		super(in);
	}

	@Override
	protected void readFromStream(DataInputStream in)
			throws InvalidMessageFormatException {
		// Intentionally left blank
	}

}
