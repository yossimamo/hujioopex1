package oop.ex5.messages;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import oop.ex5.common.FileManager;

public class IntroduceMessage extends Message {
	
	protected static final String NAME = "INTRODUCE";
	protected static final MessageType TYPE = MessageType.INTRODUCE;

	private FileManager _fileManager;
	
	public IntroduceMessage(FileManager fileManager) {
		_fileManager = fileManager;
	}
	
	public IntroduceMessage(DataInputStream in)
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
