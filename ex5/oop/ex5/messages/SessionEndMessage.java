package oop.ex5.messages;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class SessionEndMessage extends Message {
	
	protected static final String NAME = "SESSIONEND";
	protected static final MessageType TYPE = MessageType.SESSIONEND;
	
	public SessionEndMessage() {
		// Intentionally left blank
	}

	public SessionEndMessage(DataInputStream in)
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
