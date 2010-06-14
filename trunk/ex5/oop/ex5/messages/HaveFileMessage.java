package oop.ex5.messages;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class HaveFileMessage extends Message {
	
	protected static final String NAME = "HAVEFILE";
	
	private String _fileName;

	public HaveFileMessage(DataInputStream in)
			throws InvalidMessageFormatException, IOException {
		super(in);
	}

	@Override
	protected void readImp(DataInputStream in)
			throws InvalidMessageFormatException {
		try {
			_fileName = in.readUTF();
		} catch (IOException e) {
			throw new InvalidMessageFormatException();
		}
	}

	@Override
	protected void writeImp(DataOutputStream out) throws IOException {
		out.writeUTF(_fileName);		
	}
	
	@Override
	protected String getName() {
		return NAME;
	}

}
