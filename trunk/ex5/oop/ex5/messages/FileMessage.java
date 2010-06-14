package oop.ex5.messages;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class FileMessage extends Message {
	
	protected static final String NAME = "FILE";

	public FileMessage(DataInputStream in)
		throws InvalidMessageFormatException, IOException {
		super(in);
	}

	// TODO store the file temporarily on disk or in memory? large files may be
	// problematic
	@Override
	protected void readImp(DataInputStream in)
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

	@Override
	protected void writeImp(DataOutputStream out) throws IOException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected String getName() {
		return NAME;
	}
	
}
