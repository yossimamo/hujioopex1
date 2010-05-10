//###############  
// FILE : IllegalArgumentException.java
// WRITER : Uri Greenberg, urig03, 021986039  
// WRITER : Yossi Mamo, ymamo29, 038073722
// EXERCISE : oop ex3 2010  
// DESCRIPTION: Represents an error thrown upon an illegal argument given 
// as input.
//###############

package oop.ex3.exceptions;

/**
 * An error thrown upon an illegal argument given as input to the program.
 * @author Uri Greenberg and Yossi Mamo
 */
public abstract class IllegalArgumentException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructs a new IllegalArgumentException object
	 * @param error the error.
	 */
	public IllegalArgumentException(String error) {
		super(error);
	}
}
