package oop.ex3.exceptions;

public class IllegalArgumentException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	public IllegalArgumentException() {
		super("Error: Invalid argument");
	}

}
