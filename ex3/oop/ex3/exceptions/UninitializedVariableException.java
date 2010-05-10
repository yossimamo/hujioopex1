//###############  
// FILE : UninitializedVariableException.java
// WRITER : Uri Greenberg, urig03, 021986039  
// WRITER : Yossi Mamo, ymamo29, 038073722
// EXERCISE : oop ex3 2010  
// DESCRIPTION: Represents an error thrown upon an uninitialized variable
// given as input.
//###############

package oop.ex3.exceptions;

/**
 * an error thrown upon an uninitialized variable
 * given as input.
 * @author Uri Greenberg and Yossi Mamo
 */
public class UninitializedVariableException extends IllegalArgumentException {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructs a new UninitializedVariableException object 
	 */
	public UninitializedVariableException() {
		super("Error: Uninitialized variable");
	}
}
