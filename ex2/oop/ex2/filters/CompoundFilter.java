package oop.ex2.filters;

import java.io.File;

public abstract class CompoundFilter extends Filter {
	
	/// the first filter
	protected Filter _firstFilter;
	
	/// the second filter.
	protected Filter _secondFilter;
	
	/**
	 * a constructor of a compound filter. saves the two filters in fields.
	 * @param firstFilter the first filter.
	 * @param secondFilter the second filter.
	 */
	public CompoundFilter(Filter firstFilter, Filter secondFilter){
		_firstFilter = firstFilter;
		_secondFilter = secondFilter;
	}

	/**
	 * receives a file and checks to see if it satisfies the filters
	 * condition. according to the compound type. 
	 * @param file a file.
	 * @return true if the file satisfies the filters condition and false 
	 * otherwise.
	 */
	public abstract boolean isMatch(File file);

}
