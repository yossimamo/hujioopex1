//###############  
// FILE : IllegalParametersException.java
// WRITER : Uri Greenberg, urig03, 021986039  
// WRITER : Yossi Mamo, ymamo29, 038073722
// EXERCISE : oop ex3 2010  
// DESCRIPTION: Represents an error thrown upon an illegal parameters given 
// as input.
//###############

package oop.ex3.exceptions;

/**
 * an error thrown upon an illegal parameters given 
 * as input.
 * @author Uri Greenberg and Yossi Mamo
 */
public class IllegalParametersException extends IllegalArgumentException {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new IllegalParametersException object 
	 */
	public IllegalParametersException() {
		super("error: wrong number of valid parameters");
	}

}
