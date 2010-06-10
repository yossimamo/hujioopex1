package oop.ex5.messages;

import java.io.DataInputStream;
import java.util.StringTokenizer;


public abstract class Message {
	
	protected static final String MSG_TOKEN_DELIMITERS = " ";
	protected static final String MSG_END = "END";
	protected final String STR;
	protected String _name;
	
	public Message(DataInputStream str)
		throws InvalidMessageFormatException,
			   InvalidMessageNameException,
			   InvalidMessageParamsException {
	}
	
	public abstract String toString();
	
	protected String extractName() {
		StringTokenizer tok = new StringTokenizer(STR, MSG_TOKEN_DELIMITERS);
		return tok.nextToken();
	}
	
	protected abstract void verifyName(StringTokenizer tok) throws InvalidMessageNameException;
	protected abstract void loadParams(StringTokenizer tok) throws InvalidMessageParamsException;
	protected void verifyEnd(StringTokenizer tok) throws InvalidMessageFormatException {
		String str = tok.nextToken();
		if (!str.equals(MSG_END)) {
			throw new InvalidMessageFormatException();
		}
	}

}
