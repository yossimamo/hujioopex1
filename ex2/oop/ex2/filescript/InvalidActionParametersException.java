package oop.ex2.filescript;

public class InvalidActionParametersException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public InvalidActionParametersException() {
		super("Error:Invalid action parameters");
	}

}
