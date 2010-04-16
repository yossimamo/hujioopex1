package oop.ex2.filters;

import java.io.File;

import oop.ex2.filescript.InvalidFilterParametersException;

public class SizeEqualFilter extends SizeFilter {

	private static final String _name = "SIZE_EQUAL_TO";
	
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
