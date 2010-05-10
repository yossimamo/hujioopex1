package oop.ex3.exceptions;

public abstract class IllegalArgumentException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	public IllegalArgumentException(String error) {
		super(error);
	}
}
