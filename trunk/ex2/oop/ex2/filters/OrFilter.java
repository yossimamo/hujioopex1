package oop.ex2.filters;

import java.io.File;

import oop.ex2.filescript.IOFailureException;

public class OrFilter extends CompoundFilter {

	/**
	 * a constructor of a compound filter. saves the two filters in fields.
	 * @param firstFilter the first filter.
	 * @param secondFilter the second filter.
	 */
	public OrFilter(Filter firstFilter, Filter secondFilter) {
		super(firstFilter, secondFilter);
	}

	/**
	 * receives a file and checks to see if it satisfies one of the filters
	 * conditions. 
	 * @param file a file.
	 * @return true if the file satisfies one of the  filters condition
	 * and false otherwise.
	 * @throws IOFailureException 
	 */
	public boolean isMatch(File file) throws IOFailureException {
		return (_firstFilter.isMatch(file)) || (_secondFilter.isMatch(file));
	}

}
