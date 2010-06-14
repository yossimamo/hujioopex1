package oop.ex5.messages;

import java.io.DataInputStream;
import java.io.IOException;

public class FileMessage extends Message {

	public FileMessage(DataInputStream in) throws InvalidMessageFormatException {
		super(in);
	}

	// TODO store the file temporarily on disk or in memory? large files may be
	// problematic
	@Override
	protected void readFromStream(DataInputStream in)
			throws InvalidMessageFormatException {
		try {
			long length = in.readLong();
			byte[] fileContents = new byte[(int)length];
			if (length != in.read(fileContents, 0, (int)length)) {
				throw new InvalidMessageFormatException();
			}
		} catch (IOException e) {
			throw new InvalidMessageFormatException();
		}
		
	}
	
}
