package oop.ex5.messages;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class NeedServersMessage extends Message {
	
	protected static final String NAME = "NEEDSERVERS";
	protected static final MessageType TYPE = MessageType.NEEDSERVERS;
	
	public NeedServersMessage() {
		// Intentionally left blank
	}

	public NeedServersMessage(DataInputStream in)
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
