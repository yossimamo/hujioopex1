//###############  
// FILE : InvalidCommandLineParametersException.java  
// WRITER : Uri Greenberg, urig03, 021986039  
// WRITER : Yossi Mamo, ymamo29, 038073722
// EXERCISE : oop ex2 2010  
// DESCRIPTION: Represents an error thrown upon invalid command line parameters
//###############

package oop.ex2.filescript;

/**
 * An error thrown upon invalid command line parameters
 * @author Uri Greenberg and Yossi Mamo
 */
public class InvalidCommandLineParametersException extends Exception {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructs a new InvalidCommandLineParametersException object
	 */
	public InvalidCommandLineParametersException() {
		super("Error:Invalid command line parameters");
	}

}
