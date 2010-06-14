package oop.ex5.messages;

import java.io.DataInputStream;
import java.io.IOException;

public abstract class Message {
	
	protected static final String MESSAGE_END = "END";
	
	public Message(DataInputStream in) throws InvalidMessageFormatException {
		readFromStream(in);
		validateEnd(in);
	}
	
	protected abstract void readFromStream(DataInputStream in)
		throws InvalidMessageFormatException;
	
	protected void validateEnd(DataInputStream in)
		throws InvalidMessageFormatException {
		try {
			if (!MESSAGE_END.equals(in.readUTF())) {
				throw new InvalidMessageFormatException();
			}
		} catch (IOException e) {
			throw new InvalidMessageFormatException();
		}
	}

}
