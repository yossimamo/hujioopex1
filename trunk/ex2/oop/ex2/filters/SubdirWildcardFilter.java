//###############  
// FILE : SubdirWildcardFilter.java  
// WRITER : Uri Greenberg, urig03, 021986039  
// WRITER : Yossi Mamo, ymamo29, 038073722
// EXERCISE : oop ex2 2010  
// DESCRIPTION: a filter which checks if the wildcard expression fits the
// canonical name of the file.
//###############

package oop.ex2.filters;

import java.io.File;
import java.io.IOException;

import oop.ex2.filescript.IOFailureException;

/**
 * a filter which checks if the wildcard expression fits the
 * canonical name of the file.
 * @author Uri Greenberg and Yossi Mamo.
 */
public class SubdirWildcardFilter extends WildcardFilter {
	
	/// the name of the filter
	public static final String _name = "SUBDIR_WILDCARD";

	/**
	 * constructs a new filter from the wildcard expression. it determines
	 * its type and remove the "*" from the String.
	 * @param wildcardString the string to be searched for in the file.
	 */
	public SubdirWildcardFilter(String wildcardString) {
		super(wildcardString);
	}

	/**
	 * receives a file and checks to see if the files full path and name
	 * matches the wildcard expression. 
	 * @param file a file.
	 * @return true if the file full path and name
	 * matches the wildcard expression and false 
	 * otherwise.
	 * @throws IOFailureException in case an I/O error occurs while trying
	 * to get the canonical path of the file.
	 */
	public boolean isMatch(File file) throws IOFailureException {
		try {
			return contains(file.getCanonicalPath());
		} catch (IOException e) {
			throw new IOFailureException();
		}
	}
}
