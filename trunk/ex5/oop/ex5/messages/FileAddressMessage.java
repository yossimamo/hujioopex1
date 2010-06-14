package oop.ex5.messages;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class FileAddressMessage extends Message {
	
	protected static final String NAME = "FILEADDRESS";
	protected static final MessageType TYPE = MessageType.FILEADDRESS;
	
	private String _fileManagerIP;
	private int _fileManagerPort;

	public FileAddressMessage(String fileManagerIP, int fileManagerPort) {
		_fileManagerIP = fileManagerIP;
		_fileManagerPort = fileManagerPort;
	}
	
	public FileAddressMessage(DataInputStream in)
			throws InvalidMessageFormatException, IOException {
		super(in);
	}
	
	public MessageType getType() {
		return TYPE;
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
