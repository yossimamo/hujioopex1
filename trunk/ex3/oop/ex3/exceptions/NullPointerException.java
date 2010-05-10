package oop.ex3.exceptions;

public class NullPointerException extends IllegalArgumentException {

	
	private static final long serialVersionUID = 1L;

	
	public NullPointerException() {
		super("error: null pointer exception");
	}
}
