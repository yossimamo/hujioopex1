//###############  
// FILE : IllegalFunctionException.java
// WRITER : Uri Greenberg, urig03, 021986039  
// WRITER : Yossi Mamo, ymamo29, 038073722
// EXERCISE : oop ex3 2010  
// DESCRIPTION: Represents an error thrown upon an illegal function given 
// as input.
//###############

package oop.ex3.exceptions;

/**
 * An error thrown upon an illegal function given as input to the program.
 * @author Uri Greenberg and Yossi Mamo
 */
public class IllegalFunctionException extends IllegalArgumentException {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructs a new IllegalFunctionException object 
	 */
	public IllegalFunctionException() {
		super("Error: Illegal function");
	}

}
