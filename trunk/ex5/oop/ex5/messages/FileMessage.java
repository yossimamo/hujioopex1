package oop.ex5.messages;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class FileMessage extends Message {
	
	protected static final String NAME = "FILE";
	protected static final MessageType TYPE = MessageType.FILE;
	
	byte[] _fileContents;
	
	public FileMessage(byte[] fileContents) throws IOException {
		_fileContents = new byte[fileContents.length];
		System.arraycopy(fileContents, 0, _fileContents, 0, fileContents.length);
	}

	public FileMessage(DataInputStream in)
		throws InvalidMessageFormatException, IOException {
		super(in);
	}
	
	public MessageType getType() {
		return TYPE;
	}
	
	public byte[] getFileContents() {
		return _fileContents;
	}

	@Override
	protected void readImp(DataInputStream in)
			throws InvalidMessageFormatException {
		try {
			long length = in.readLong();
			_fileContents = new byte[(int)length];
			if (length != in.read(_fileContents, 0, (int)length)) {
				throw new InvalidMessageFormatException();
			}
		} catch (IOException e) {
			throw new InvalidMessageFormatException();
		}
		
	}

	@Override
	protected void writeImp(DataOutputStream out) throws IOException {
		out.writeLong(_fileContents.length);
		out.write(_fileContents);
	}
	
	@Override
	protected String getName() {
		return NAME;
	}
	
}
