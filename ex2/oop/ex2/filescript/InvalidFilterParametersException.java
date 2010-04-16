package oop.ex2.filescript;

public class InvalidFilterParametersException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public InvalidFilterParametersException() {
		super("Error:Invalid filter parameters");
	}

}
