package oop.ex3.exceptions;

public class UnititializedVariableException extends IllegalArgumentException {
	
	private static final long serialVersionUID = 1L;
	
	public UnititializedVariableException() {
		super("Error: Uninitialized variable");
	}
}
