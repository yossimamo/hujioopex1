package oop.ex2.filescript;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;

import oop.ex2.actions.Action;
import oop.ex2.filters.Filter;

public class Section {
	
	private ArrayList<Filter> _filters;
	private ArrayList<String> _comments;
	private Action _action;
	
	public Section() {
		_filters = new ArrayList<Filter>();
		_comments = new ArrayList<String>();
		_action = null;
	}
	
	public void addFilter(Filter filter) {
		_filters.add(filter);
	}
	
	public void addComment(String comment) {
		_comments.add(comment);
	}
	
	public void setAction(Action action) {
		_action = action;
	}
	
	/**
	 * Assumes that the directory is valid and that an action has been set.
	 * @param rootDirectory
	 * @throws IOException 
	 */
	public void execute(File rootDirectory) throws IOException {
		// Print comments
		for (int i = 0; i < _comments.size(); i++) {
			System.out.println(_comments.get(i));
		}
		
		TreeSet<File> fileTree = new TreeSet<File>();
		buildFileTree(rootDirectory, fileTree);
		
		Iterator<File> it = fileTree.iterator();
		while (it.hasNext()) {
			File file = it.next();
			if (matchToFilters(file)) {
				_action.execute(file);
			}
		}
		
	}

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
	
	private boolean matchToFilters(File file) {
		for (int i = 0; i < _filters.size(); i++) {
			if (!_filters.get(i).isMatch(file)) {
				return false;
			}
		}
		return true;
	}
}
