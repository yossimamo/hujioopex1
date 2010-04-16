package oop.ex2.filters;

import java.io.File;

public class IsWritableFilter extends BooleanFilter {

	/**
	 * a constructor which saves the parameter received as a boolean field.
	 * @param condition the condition received.
	 */
	public IsWritableFilter(String condition) {
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
