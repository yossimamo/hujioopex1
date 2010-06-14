package oop.ex5.messages;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.UTFDataFormatException;

public class DontHaveFileMessage extends Message {
	
	protected static final String NAME = "DONTHAVEFILE";
	
	private String _fileName;

	public DontHaveFileMessage(DataInputStream in)
			throws InvalidMessageFormatException, IOException {
		super(in);
	}

	@Override
	protected void readImp(DataInputStream in)
			throws InvalidMessageFormatException, IOException {
		try {
			_fileName = in.readUTF();
		} catch (EOFException e) {
			throw new InvalidMessageFormatException();
		}
		catch (UTFDataFormatException e) {
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
