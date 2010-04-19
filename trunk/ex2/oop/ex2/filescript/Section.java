//###############  
// FILE : Section.java  
// WRITER : Uri Greenberg, urig03, 021986039  
// WRITER : Yossi Mamo, ymamo29, 038073722
// EXERCISE : oop ex2 2010  
// DESCRIPTION: Represents a single section inside a command file.
//###############

package oop.ex2.filescript;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;

import oop.ex2.actions.Action;
import oop.ex2.filters.Filter;

/**
 * Represents a single section inside a command file. A section consists of
 * multiple filters, comments and a single action.
 * @author Uri Greenberg and Yossi Mamo
 *
 */
public class Section {
	
	private ArrayList<Filter> _filters;
	private ArrayList<String> _comments;
	private Action _action;
	
	/**
	 * Constructs a new, empty section
	 */
	public Section() {
		_filters = new ArrayList<Filter>();
		_comments = new ArrayList<String>();
		_action = null;
	}
	
	/**
	 * Adds a new filter to the section. Filters are kept in the order of
	 * addition.
	 * @param filter The filter to add
	 */
	public void addFilter(Filter filter) {
		_filters.add(filter);
	}
	
	/**
	 * Adds a new comment to the section. Comments are kept in the order of
	 * addition.
	 * @param comment The comment to add
	 */
	public void addComment(String comment) {
		_comments.add(comment);
	}
	
	/**
	 * Sets the action in the section. Assumes valid input.
	 * @param action The given action
	 */
	public void setAction(Action action) {
		_action = action;
	}
	
	/**
	 * Executes the section on the given root directory, that is matching all
	 * the files (recursively) to the filters, and executing the action on the
	 * files that match at least one of the filters.
	 * Assumes that the given root directory is valid and that an action has
	 * been set.
	 * @param rootDirectory The root directory to work on
	 * @throws IOFailureException Upon an I/O failure of any kind
	 */
	public void execute(File rootDirectory) throws IOFailureException {
		// Print comments
		for (int i = 0; i < _comments.size(); i++) {
			System.out.println(_comments.get(i));
		}
		
		TreeSet<File> fileTree = new TreeSet<File>();
		buildFileTree(rootDirectory, fileTree);
		
		// Match files to filters and execute action
		Iterator<File> it = fileTree.iterator();
		try {
			while (it.hasNext()) {
				File file = it.next();
				if (matchToFilters(file)) {
					_action.execute(file, rootDirectory.getCanonicalPath());
				}
			}
		} catch (IOException e) {
			throw new IOFailureException();
		}
	}

	/**
	 * Iterates over the given root directory recursively and adds all the
	 * files into the fileTree TreeSet
	 * @param rootDirectory The root directory to work on
	 * @param fileTree The TreeSet into which the files are added
	 */
	private void buildFileTree(File rootDirectory, TreeSet<File> fileTree) {
		File[] files = rootDirectory.listFiles();
		for (int i = 0; i < files.length; i++) {
			if (files[i].isFile()) {
				fileTree.add(files[i]);
			} else if (files[i].isDirectory()) {
				buildFileTree(files[i], fileTree);
			}
		}
	}
	
	/**
	 * Attempts to match the given file to the filters in this section.
	 * If the file matches at least one of the matches, this method will
	 * return true, and false otherwise.
	 * @param file The file to match
	 * @return True if a match was found, false otherwise
	 * @throws IOFailureException upon an I/O failure of any kind
	 */
	private boolean matchToFilters(File file) throws IOFailureException {
		boolean foundMatch = false;
		for (int i = 0; i < _filters.size(); i++) {
			if (_filters.get(i).isMatch(file)) {
				foundMatch = true;
			}
		}
		return foundMatch;
	}
}
