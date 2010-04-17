package oop.ex2.filters;

import java.io.File;

public class AndFilter extends CompoundFilter {
	
	/**
	 * a constructor of a compound filter. saves the two filters in fields.
	 * @param firstFilter the first filter.
	 * @param secondFilter the second filter.
	 */
	public AndFilter(Filter firstFilter, Filter secondFilter) {
		super(firstFilter, secondFilter);
	}

	/**
	 * receives a file and checks to see if it satisfies both filters
	 * conditions. 
	 * @param file a file.
	 * @return true if the file satisfies both filters condition and false 
	 * otherwise.
	 */
	public boolean isMatch(File file) {
		return (_firstFilter.isMatch(file)) && (_secondFilter.isMatch(file));
	}
}