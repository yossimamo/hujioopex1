//###############  
// FILE : InvalidSectionException.java  
// WRITER : Uri Greenberg, urig03, 021986039  
// WRITER : Yossi Mamo, ymamo29, 038073722
// EXERCISE : oop ex2 2010  
// DESCRIPTION: Represents an error thrown upon encountering an invalid section
//###############

package oop.ex2.filescript;

/**
 * An error thrown upon encountering an invalid section
 * @author Uri Greenberg and Yossi Mamo
 */
public class InvalidSectionException extends Exception {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructs a new InvalidSectionException object
	 */
	public InvalidSectionException() {
		super("Error:Invalid section");
	}

}
