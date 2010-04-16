package oop.ex2.filescript;

public class InvalidCommandLineParametersException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public InvalidCommandLineParametersException() {
		super("Error:Invalid command line parameters");
	}

}
