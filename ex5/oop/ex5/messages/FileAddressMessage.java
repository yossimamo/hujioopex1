package oop.ex5.messages;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import oop.ex5.nameserver.FileManager;

public class FileAddressMessage extends Message {
	
	protected static final String NAME = "FILEADDRESS";
	protected static final MessageType TYPE = MessageType.FILEADDRESS;
	
	private FileManager _fileManager;

	public FileAddressMessage(FileManager fileManager) {
		_fileManager = fileManager;
	}
	
	public FileAddressMessage(DataInputStream in)
			throws InvalidMessageFormatException, IOException {
		super(in);
	}
	
	public MessageType getType() {
		return TYPE;
	}
	
	public FileManager getFileManager() {
		return _fileManager;
	}
	
	@Override
	protected void readImp(DataInputStream in)
			throws InvalidMessageFormatException {
		try {
			String ip = in.readUTF();
			int port = in.readInt();
			_fileManager = new FileManager(ip, port);
		} catch (IOException e) {
			throw new InvalidMessageFormatException();
		}		
	}

	@Override
	protected void writeImp(DataOutputStream out) throws IOException {
		out.writeUTF(_fileManager.getIP());
		out.writeInt(_fileManager.getPort());
	}
	
	@Override
	protected String getName() {
		return NAME;
	}

}
