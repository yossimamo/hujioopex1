package oop.ex2.filters;

import java.io.File;

public class FileWildcardFilter extends WildcardFilter {
	
	private static final String _name = "FILE_WILDCARD";

	/**
	 * saves the given string in a field.
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
