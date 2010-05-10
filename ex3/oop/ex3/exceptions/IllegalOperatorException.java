package oop.ex3.exceptions;

public class IllegalOperatorException extends IllegalArgumentException {
	
	private static final long serialVersionUID = 1L;
	
	public IllegalOperatorException() {
		super("Error: Illegal operator");
	}

}
