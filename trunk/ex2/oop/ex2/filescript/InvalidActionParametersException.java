//###############  
// FILE : InvalidActionParametersException.java  
// WRITER : Uri Greenberg, urig03, 021986039  
// WRITER : Yossi Mamo, ymamo29, 038073722
// EXERCISE : oop ex2 2010  
// DESCRIPTION: Represents an error thrown upon invalid action parameters given
// as input.
//###############

package oop.ex2.filescript;

/**
 * An error thrown upon invalid action parameters given as input.
 * @author Uri Greenberg and Yossi Mamo
 */
public class InvalidActionParametersException extends Exception {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructs a new InvalidActionParametersException object 
	 */
	public InvalidActionParametersException() {
		super("Error:Invalid action parameters");
	}

}
