package oop.ex2.filters;

import java.io.File;
import java.io.IOException;

public class SubdirWildcardFilter extends WildcardFilter {
	
	private static final String _name = "SUBDIR_WILDCARD";

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
	 */
	public boolean isMatch(File file) {
		try {
			return contains(file.getCanonicalPath());
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

}
