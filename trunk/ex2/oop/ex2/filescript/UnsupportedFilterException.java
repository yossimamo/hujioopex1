//###############  
// FILE : UnsupportedFilterException.java  
// WRITER : Uri Greenberg, urig03, 021986039  
// WRITER : Yossi Mamo, ymamo29, 038073722
// EXERCISE : oop ex2 2010  
// DESCRIPTION: Represents an error thrown upon encountering an unsupported filter
//###############

package oop.ex2.filescript;

/**
 * An error thrown upon encountering an unsupported filter
 * @author Uri Greenberg and Yossi Mamo
 */
public class UnsupportedFilterException extends Exception {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructs a new UnsupportedFilterException object
	 */
	public UnsupportedFilterException() {
		super("Error:Unsupported filter");
	}

}
