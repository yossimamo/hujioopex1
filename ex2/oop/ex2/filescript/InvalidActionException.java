//###############  
// FILE : InvalidActionException.java  
// WRITER : Uri Greenberg, urig03, 021986039  
// WRITER : Yossi Mamo, ymamo29, 038073722
// EXERCISE : oop ex2 2010  
// DESCRIPTION: Represents an error thrown upon an invalid action given as input.
//###############

package oop.ex2.filescript;

/**
 * An error thrown upon an invalid action given as input to the program.
 * @author Uri Greenberg and Yossi Mamo
 */
public class InvalidActionException extends Exception {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructs a new InvalidActionException object 
	 */
	public InvalidActionException() {
		super("Error:Invalid action");
	}

}
