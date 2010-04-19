//###############  
// FILE : SizeEqualFilter.java  
// WRITER : Uri Greenberg, urig03, 021986039  
// WRITER : Yossi Mamo, ymamo29, 038073722
// EXERCISE : oop ex2 2010  
// DESCRIPTION: a filter which checks if the file equals the given
// parameter.
//###############

package oop.ex2.filters;

import java.io.File;

import oop.ex2.filescript.InvalidFilterParametersException;

/**
 * a filter which checks if the file equals the given
 * parameter.
 * @author Uri Greenberg and Yossi Mamo.
 */
public class SizeEqualFilter extends SizeFilter {

	/// the name of the filter
	public static final String _name = "SIZE_EQUAL_TO";
	
	/**
	 * a constructor that saves the size given as a filter
	 * parameter in a field.
	 * @param size the size given as a filter parameter.
	 * @throws InvalidFilterParametersException upon invalid parameter (not a 
	 * number, negative or non-integer value, etc.)
	 */
	public SizeEqualFilter(String size)
		throws InvalidFilterParametersException {
		super(size);
	}

	/**
	 * returns true if the files size equals the size given as the
	 * filters parameter or false otherwise.
	 * @param file the file.
	 * @return true if the files size equals the size given as the
	 * filters parameter or false otherwise.
	 */
	public boolean isMatch(File file) {
		if (file.length() == _size){
			return true;
		}		
		return false;
	}
}
