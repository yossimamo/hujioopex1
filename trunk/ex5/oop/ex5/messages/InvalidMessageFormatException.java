package oop.ex5.messages;

public class InvalidMessageFormatException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public InvalidMessageFormatException() {
		super("Error: Invalid Message Format");
	}

}
