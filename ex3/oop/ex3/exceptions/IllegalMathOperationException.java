package oop.ex3.exceptions;

public class IllegalMathOperationException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	public IllegalMathOperationException() {
		super("Error: Illegal math operation");
	}

}
