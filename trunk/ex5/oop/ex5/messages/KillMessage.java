package oop.ex5.messages;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import oop.ex5.messages.Message.MessageType;

public class KillMessage extends Message {
	
	protected static final String NAME = "KILL";
	protected static final MessageType TYPE = MessageType.KILL;
	
	public KillMessage() {
		// Intentionally left blank
	}

	public KillMessage(DataInputStream in)
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
