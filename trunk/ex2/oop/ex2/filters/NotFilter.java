package oop.ex2.filters;

import java.io.File;

public class NotFilter extends Filter {
	
	// a filter.
	private Filter _filter;

	/**
	 * receives a file and checks to see if it doesn't satisfy the filters
	 * condition. 
	 * @param file a file.
	 * @return true if the file doesn't satisfy the  filters condition
	 * and false otherwise.
	 */
	public boolean isMatch(File file) {
		return !_filter.isMatch(file);
	}

}
