package oop.ex3.exceptions;

public class UnititializedVariableException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	public UnititializedVariableException() {
		super("Error: Uninitialized variable");
	}
}
