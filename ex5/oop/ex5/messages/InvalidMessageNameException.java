package oop.ex5.messages;

public class InvalidMessageNameException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public InvalidMessageNameException() {
		super("Error: Invalid Message Name");
	}

}
