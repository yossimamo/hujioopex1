//###############  
// FILE : OrFilter.java  
// WRITER : Uri Greenberg, urig03, 021986039  
// WRITER : Yossi Mamo, ymamo29, 038073722
// EXERCISE : oop ex2 2010  
// DESCRIPTION: a class which represents an Or compound filter.
// for the file to satisfy the filter it must satisfy one of the filters in
// the fields.
//###############

package oop.ex2.filters;

import java.io.File;

import oop.ex2.filescript.IOFailureException;

/**
 * a class which represents an Or compound filter.
 * for the file to satisfy the filter it must satisfy one of the filters in
 * the fields.
 * @author Uri Greenberg and Yossi Mamo.
 */
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
	 * @throws IOFailureException in case an I/O error occurs while working
	 * with the given file.
	 */
	public boolean isMatch(File file) throws IOFailureException {
		return (_firstFilter.isMatch(file)) || (_secondFilter.isMatch(file));
	}
}
