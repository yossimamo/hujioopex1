//###############  
// FILE : Filter.java  
// WRITER : Uri Greenberg, urig03, 021986039  
// WRITER : Yossi Mamo, ymamo29, 038073722
// EXERCISE : oop ex2 2010  
// DESCRIPTION: an abstract class which represents a filter. has a method
// which is common to all filters, isMatch.
//###############

package oop.ex2.filters;

import java.io.File;

import oop.ex2.filescript.IOFailureException;

/**
 * an abstract class which represents a filter. has a method
 * which is common to all filters, isMatch.
 * @author Uri Greenberg and Yossi Mamo.
 */
public abstract class Filter {
	
	/**
	 * receives a file and checks to see if it satisfies the filters condition. 
	 * @param file a file.
	 * @return true if the file satisfies the filters condition and false 
	 * otherwise.
	 * @throws IOFailureException in case an I/O error occurs while working
	 * with the given file.
	 */
	public abstract boolean isMatch(File file) throws IOFailureException;
}
