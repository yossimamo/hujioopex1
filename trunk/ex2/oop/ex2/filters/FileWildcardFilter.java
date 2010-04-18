//###############  
// FILE : FileWildcardFilter.java  
// WRITER : Uri Greenberg, urig03, 021986039  
// WRITER : Yossi Mamo, ymamo29, 038073722
// EXERCISE : oop ex2 2010  
// DESCRIPTION: a filter which checks if the wildcard expression fits the
// name of the file.
//###############

package oop.ex2.filters;

import java.io.File;

/**
 * a filter which checks if the wildcard expression fits the
 * name of the file.
 * @author Uri Greenberg and Yossi Mamo.
 */
public class FileWildcardFilter extends WildcardFilter {
	
	// the name of the filter.
	public static final String _name = "FILE_WILDCARD";

	/**
	 * constructs a new filter from the wildcard expression. it determines
	 * its type and remove the "*" from the String.
	 * @param wildcardString the string to be searched for in the file.
	 */
	public FileWildcardFilter(String wildcardString) {
		super(wildcardString);
	}

	/**
	 * receives a file and checks to see if the files name
	 * matches the wildcard expression. 
	 * @param file a file.
	 * @return true if the files name matches the wildcard expression
	 * and false otherwise.
	 */
	public boolean isMatch(File file) {
		return contains(file.getName());
	}
}
