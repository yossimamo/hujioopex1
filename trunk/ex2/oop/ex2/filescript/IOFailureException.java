package oop.ex2.filescript;

public class IOFailureException extends Exception {
	
private static final long serialVersionUID = 1L;
	
	public IOFailureException() {
		super("Error:I/O failure");
	}
}
