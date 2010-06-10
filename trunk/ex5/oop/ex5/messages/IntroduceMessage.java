package oop.ex5.messages;

import java.util.StringTokenizer;

public class IntroduceMessage extends Message {
	
	private String _ip;
	private String _port;

	public IntroduceMessage(String str)
		throws InvalidMessageFormatException,
			   InvalidMessageNameException,
			   InvalidMessageParamsException {
		super(str);
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void loadParams(StringTokenizer tok)
			throws InvalidMessageParamsException {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void verifyName(StringTokenizer tok)
			throws InvalidMessageNameException {
		// TODO Auto-generated method stub
		
	}

	
}
