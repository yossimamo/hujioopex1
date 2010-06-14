package oop.ex5.messages;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class IntroduceMessage extends Message {
	
	protected static final String NAME = "INTRODUCE";
	
	private String _fileManagerIP;
	private int _fileManagerPort;
	
	public IntroduceMessage(DataInputStream in)
		throws InvalidMessageFormatException, IOException {
		super(in);
	}

	@Override
	protected void readImp(DataInputStream in)
		throws InvalidMessageFormatException {
		try {
			_fileManagerIP = in.readUTF();
			_fileManagerPort = in.readInt();
		} catch (IOException e) {
			throw new InvalidMessageFormatException();
		}		
	}

	@Override
	protected void writeImp(DataOutputStream out) throws IOException {
		out.writeUTF(_fileManagerIP);
		out.writeInt(_fileManagerPort);
	}
	
	@Override
	protected String getName() {
		return NAME;
	}
	
}
