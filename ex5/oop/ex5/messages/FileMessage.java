package oop.ex5.messages;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class FileMessage extends Message {
	
	protected static final String NAME = "FILE";
	protected static final MessageType TYPE = MessageType.FILE;
	private static final int CHUNK_SIZE = 1024;
	
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
			int offset = 0;
			while (_fileContents.length > offset) {
				int sizeToRead = Math.min(CHUNK_SIZE, _fileContents.length - offset);
				int actuallyReadBytes = in.read(_fileContents, offset, sizeToRead);
				if (actuallyReadBytes <= 0) {
					throw new InvalidMessageFormatException();
				}
				offset += actuallyReadBytes;
			}
		} catch (IOException e) {
			throw new InvalidMessageFormatException();
		}
		
	}

	@Override
	protected void writeImp(DataOutputStream out) throws IOException {
		out.writeLong(_fileContents.length);
		int offset = 0;
		while (_fileContents.length > offset) {
			int sizeToWrite = Math.min(CHUNK_SIZE, _fileContents.length - offset);
			out.write(_fileContents, offset, sizeToWrite);
			offset += sizeToWrite;
		}
	}
	
	@Override
	protected String getName() {
		return NAME;
	}
	
}
