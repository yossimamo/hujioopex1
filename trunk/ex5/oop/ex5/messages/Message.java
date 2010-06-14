package oop.ex5.messages;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.UTFDataFormatException;

public abstract class Message {
	
	protected static final String END = "END";
	
	protected Message(DataInputStream in)
		throws InvalidMessageFormatException, IOException {
		readImp(in);
		validateEnd(in);
	}
	
	public static Message read(DataInputStream in)
		throws InvalidMessageFormatException, IOException, InvalidMessageNameException {
		String messageName;
		try {
			messageName = in.readUTF();
		} catch (EOFException e) {
			throw new InvalidMessageFormatException();
		} catch (UTFDataFormatException e) {
			throw new InvalidMessageFormatException();
		} 
		if (AnnounceMessage.NAME.equals(messageName)) {
			return new AnnounceMessage(in);
		}
		if (ByeMessage.NAME.equals(messageName)) {
			return new ByeMessage(in);
		}
		if (DontHaveFileMessage.NAME.equals(messageName)) {
			return new DontHaveFileMessage(in);
		}
		if (ErrorMessage.NAME.equals(messageName)) {
			return new ErrorMessage(in);
		}
		if (FileAddressMessage.NAME.equals(messageName)) {
			return new FileAddressMessage(in);
		}
		if (FileMessage.NAME.equals(messageName)) {
			return new FileMessage(in);
		}
		if (FileNotFoundMessage.NAME.equals(messageName)) {
			return new FileNotFoundMessage(in);
		}
		if (HaveFileMessage.NAME.equals(messageName)) {
			return new HaveFileMessage(in);
		}
		if (HaveNameServerMessage.NAME.equals(messageName)) {
			return new HaveNameServerMessage(in);
		}
		if (IntroduceMessage.NAME.equals(messageName)) {
			return new IntroduceMessage(in);
		}
		if (KillMessage.NAME.equals(messageName)) {
			return new KillMessage(in);
		}
		if (ListEndMessage.NAME.equals(messageName)) {
			return new ListEndMessage(in);
		}
		if (NeedFileMessage.NAME.equals(messageName)) {
			return new NeedFileMessage(in);
		}
		if (NeedServersMessage.NAME.equals(messageName)) {
			return new NeedServersMessage(in);
		}
		if (OkMessage.NAME.equals(messageName)) {
			return new OkMessage(in);
		}
		if (SessionEndMessage.NAME.equals(messageName)) {
			return new SessionEndMessage(in);
		}
		throw new InvalidMessageNameException();		
	}
	
	public void write(DataOutputStream out) throws IOException {
		out.writeUTF(getName());
		writeImp(out);
		out.writeUTF(END);
	}
	
	protected abstract void readImp(DataInputStream in)
		throws InvalidMessageFormatException, IOException;
	
	protected abstract void writeImp(DataOutputStream out)
		throws IOException;
	
	protected abstract String getName();
	
	protected void validateEnd(DataInputStream in)
		throws InvalidMessageFormatException, IOException {
		try {
			if (!END.equals(in.readUTF())) {
				throw new InvalidMessageFormatException();
			}
		} catch (EOFException e) {
			throw new InvalidMessageFormatException();
		} catch (UTFDataFormatException e) {
			throw new InvalidMessageFormatException();
		}
	}

}
