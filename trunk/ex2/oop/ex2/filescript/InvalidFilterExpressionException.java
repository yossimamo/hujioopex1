package oop.ex2.filescript;

public class InvalidFilterExpressionException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public InvalidFilterExpressionException() {
		super("Error:Invalid filter expression");
	}

}
