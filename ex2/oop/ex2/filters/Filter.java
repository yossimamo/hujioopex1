package oop.ex2.filters;

import java.io.File;

public abstract class Filter {
	
	/**
	 * receives a file and checks to see if it satisfies the filters condition. 
	 * @param file a file.
	 * @return true if the file satisfies the filters condition and false 
	 * otherwise.
	 */
	public abstract boolean isMatch(File file);

}
