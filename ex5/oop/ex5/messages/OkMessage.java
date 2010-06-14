package oop.ex5.messages;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import oop.ex5.messages.Message.MessageType;

public class OkMessage extends Message {
	
	protected static final String NAME = "OK";
	protected static final MessageType TYPE = MessageType.OK;
	
	public OkMessage() {
		// Intentionally left blank
	}

	public OkMessage(DataInputStream in)
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
