package oop.ex5.messages;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ListEndMessage extends Message {
	
	protected static final String NAME = "LISTEND";

	public ListEndMessage(DataInputStream in)
			throws InvalidMessageFormatException, IOException {
		super(in);
	}

	@Override
	protected void readImp(DataInputStream in)
			throws InvalidMessageFormatException {
		// Intentionally left blank
	}

	@Override
	protected void writeImp(DataOutputStream out) throws IOException {
		// Intentionally left blank		
	}
	
	@Override
	protected String getName() {
		return NAME;
	}

}
