//###############  
// FILE : NotFilter.java  
// WRITER : Uri Greenberg, urig03, 021986039  
// WRITER : Yossi Mamo, ymamo29, 038073722
// EXERCISE : oop ex2 2010  
// DESCRIPTION: a filter which holds another filter as a field and is
// satisfied only if the filter in the field is not.
//###############

package oop.ex2.filters;

import java.io.File;

import oop.ex2.filescript.IOFailureException;

/**
 * a filter which holds another filter as a field and is
 * satisfied only if the filter in the field is not.
 * @author Uri Greenberg and Yossi Mamo.
 */
public class NotFilter extends Filter {
	
	// a filter.
	private Filter _filter;
	
	/**
	 * constructs a new Not Filter, saving the given filter in a field.
	 * @param filter the given filter.
	 */
	public NotFilter(Filter filter) {
		_filter = filter;
	}

	/**
	 * receives a file and checks to see if it doesn't satisfy the filters
	 * condition. 
	 * @param file a file.
	 * @return true if the file doesn't satisfy the  filters condition
	 * and false otherwise.
	 * @throws IOFailureException in case an I/O error occurs while working
	 * with the given file.
	 */
	public boolean isMatch(File file) throws IOFailureException {
		return !_filter.isMatch(file);
	}
}
