package oop.ex5.messages;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FileMessage extends Message {
	
	protected static final String NAME = "FILE";
	protected static final MessageType TYPE = MessageType.FILE;
	
	byte[] _fileContents;
	
	public FileMessage(File file) throws IOException {
		FileInputStream in = new FileInputStream(file);
		_fileContents = new byte[(int)file.length()];
		in.read(_fileContents);
		in.close();
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
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected String getName() {
		return NAME;
	}
	
}
