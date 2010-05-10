package oop.ex3.exceptions;

public class IllegalFunctionException extends IllegalArgumentException {
	
	private static final long serialVersionUID = 1L;
	
	public IllegalFunctionException() {
		super("Error: Illegal function");
	}

}