//###############  
// FILE : IOFailureException.java  
// WRITER : Uri Greenberg, urig03, 021986039  
// WRITER : Yossi Mamo, ymamo29, 038073722
// EXERCISE : oop ex2 2010  
// DESCRIPTION: Represents an error thrown upon an I/O failure of any kind
//###############

package oop.ex2.filescript;

/**
 * An error thrown upon an I/O failure of any kind
 * @author Uri Greenberg and Yossi Mamo
 */
public class IOFailureException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructs a new IOFailureException object
	 */
	public IOFailureException() {
		super("Error:I/O failure");
	}
}
