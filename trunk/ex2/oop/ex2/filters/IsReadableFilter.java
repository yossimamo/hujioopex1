package oop.ex2.filters;

import java.io.File;

import oop.ex2.filescript.InvalidFilterParametersException;

public class IsReadableFilter extends BooleanFilter {
	
	public static final String _name = "IS_READABLE";

	/**
	 * a constructor which saves the parameter received as a boolean field.
	 * @param condition the condition received.
	 * @throws InvalidFilterParametersException Upon invalid parameter (not
	 * YES or NO)
	 */
	public IsReadableFilter(String condition)
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
		return (file.canRead() == _condition);
	}

}
