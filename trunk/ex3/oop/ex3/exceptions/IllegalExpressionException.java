package oop.ex3.exceptions;

public class IllegalExpressionException extends IllegalArgumentException {
	
	private static final long serialVersionUID = 1L;
	
	public IllegalExpressionException() {
		super("Error: Illegal expression");
	}

}
