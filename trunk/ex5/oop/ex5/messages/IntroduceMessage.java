package oop.ex5.messages;

import java.io.DataInputStream;
import java.io.IOException;

public class IntroduceMessage extends Message {
	
	private String _fileManagerIP;
	private int _fileManagerPort;
	
	public IntroduceMessage(DataInputStream in)
		throws InvalidMessageFormatException {
		super(in);
	}

	@Override
	protected void readFromStream(DataInputStream in)
		throws InvalidMessageFormatException {
		try {
			_fileManagerIP = in.readUTF();
			_fileManagerPort = in.readInt();
		} catch (IOException e) {
			throw new InvalidMessageFormatException();
		}		
	}
	
}
