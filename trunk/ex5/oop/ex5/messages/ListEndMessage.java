package oop.ex5.messages;

import java.io.DataInputStream;

public class ListEndMessage extends Message {

	public ListEndMessage(DataInputStream in)
			throws InvalidMessageFormatException {
		super(in);
	}

	@Override
	protected void readFromStream(DataInputStream in)
			throws InvalidMessageFormatException {
		// Intentionally left blank
	}

}
