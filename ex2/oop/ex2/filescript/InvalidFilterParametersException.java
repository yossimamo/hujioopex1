//###############  
// FILE : InvalidFilterParametersException.java  
// WRITER : Uri Greenberg, urig03, 021986039  
// WRITER : Yossi Mamo, ymamo29, 038073722
// EXERCISE : oop ex2 2010  
// DESCRIPTION: Represents an error thrown upon encountering invalid filter params
//###############

package oop.ex2.filescript;

/**
 * An error thrown upon encountering invalid filter params
 * @author Uri Greenberg and Yossi Mamo
 */
public class InvalidFilterParametersException extends Exception {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructs a new InvalidFilterParametersException object
	 */
	public InvalidFilterParametersException() {
		super("Error:Invalid filter parameters");
	}

}
