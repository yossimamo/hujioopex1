package oop.ex5.messages;

import java.io.DataInputStream;
import java.io.IOException;

public class NeedFileMessage extends Message {
	
	private String _fileName;

	public NeedFileMessage(DataInputStream in)
			throws InvalidMessageFormatException {
		super(in);
	}

	@Override
	protected void readFromStream(DataInputStream in)
			throws InvalidMessageFormatException {
		try {
			_fileName = in.readUTF();
		} catch (IOException e) {
			throw new InvalidMessageFormatException();
		}
	}

}
