//###############  
// FILE : IsWritableFilter.java  
// WRITER : Uri Greenberg, urig03, 021986039  
// WRITER : Yossi Mamo, ymamo29, 038073722
// EXERCISE : oop ex2 2010  
// DESCRIPTION: a filter which checks if the file is writable.
//###############

package oop.ex2.filters;

import java.io.File;

import oop.ex2.filescript.InvalidFilterParametersException;

/**
 * a filter which checks if the file is writable.
 * @author Uri Greenberg and Yossi Mamo.
 */
public class IsWritableFilter extends BooleanFilter {

	// the filters name.
	public static final String _name = "IS_WRITABLE";
	
	/**
	 * a constructor which saves the parameter received as a boolean field.
	 * @param condition the condition received.
	 * @throws InvalidFilterParametersException Upon invalid parameter (not
	 * YES or NO) 
	 */
	public IsWritableFilter(String condition)
		throws InvalidFilterParametersException {
		super(condition);
	}

	/**
	 * receives a file and checks to see if it satisfies the filters condition. 
	 * @param file a file.
	 * @return true if the file satisfies the filters condition and false 
	 * otherwise.
	 */
	public boolean isMatch(File file) {
		return (file.canWrite() == _condition);
	}
}
