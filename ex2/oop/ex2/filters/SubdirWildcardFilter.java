package oop.ex2.filters;

import java.io.File;
import java.io.IOException;

import oop.ex2.filescript.IOFailureException;

public class SubdirWildcardFilter extends WildcardFilter {
	
	public static final String _name = "SUBDIR_WILDCARD";

	/**
	 * saves the given string in a field.
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
	 * @throws IOFailureException 
	 */
	public boolean isMatch(File file) throws IOFailureException {
		try {
			return contains(file.getCanonicalPath());
		} catch (IOException e) {
			throw new IOFailureException();
		}
	}

}
