//###############  
// FILE : CompoundFilter.java  
// WRITER : Uri Greenberg, urig03, 021986039  
// WRITER : Yossi Mamo, ymamo29, 038073722
// EXERCISE : oop ex2 2010  
// DESCRIPTION: an abstract class which represents a compound filter.
// it holds two fields (of filters) which are common to all compound filters.
//###############

package oop.ex2.filters;

import java.io.File;

import oop.ex2.filescript.IOFailureException;

/**
 * an abstract class which represents a compound filter.
 * it holds two fields (of filters) which are common to all compound filters.
 * @author Uri Greenberg and Yossi Mamo.
 */
public abstract class CompoundFilter extends Filter {
	
	/// the first filter
	protected Filter _firstFilter;
	
	/// the second filter.
	protected Filter _secondFilter;
	
	/**
	 * constructs a compound filter. saves the two filters in fields.
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
	 * @throws IOFailureException in case an I/O error occurs while working
	 * with the given file.
	 */
	public abstract boolean isMatch(File file) throws IOFailureException;

}
