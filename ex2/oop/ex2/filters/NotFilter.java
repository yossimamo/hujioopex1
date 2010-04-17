package oop.ex2.filters;

import java.io.File;

import oop.ex2.filescript.IOFailureException;

public class NotFilter extends Filter {
	
	// a filter.
	private Filter _filter;
	
	public NotFilter(Filter filter) {
		_filter = filter;
	}

	/**
	 * receives a file and checks to see if it doesn't satisfy the filters
	 * condition. 
	 * @param file a file.
	 * @return true if the file doesn't satisfy the  filters condition
	 * and false otherwise.
	 * @throws IOFailureException 
	 */
	public boolean isMatch(File file) throws IOFailureException {
		return !_filter.isMatch(file);
	}

}
