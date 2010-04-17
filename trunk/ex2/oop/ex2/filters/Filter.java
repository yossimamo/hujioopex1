package oop.ex2.filters;

import java.io.File;

import oop.ex2.filescript.IOFailureException;

public abstract class Filter {
	
	/**
	 * receives a file and checks to see if it satisfies the filters condition. 
	 * @param file a file.
	 * @return true if the file satisfies the filters condition and false 
	 * otherwise.
	 * @throws IOFailureException 
	 */
	public abstract boolean isMatch(File file) throws IOFailureException;

}
