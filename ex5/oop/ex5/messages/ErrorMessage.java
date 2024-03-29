package oop.ex5.messages;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ErrorMessage extends Message {
	
	protected static final String NAME = "ERROR";
	protected static final MessageType TYPE = MessageType.ERROR;
	
	public ErrorMessage() {
		// Intentionally left blank
	}

	public ErrorMessage(DataInputStream in)
			throws InvalidMessageFormatException, IOException {
		super(in);
	}
	
	public MessageType getType() {
		return TYPE;
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
