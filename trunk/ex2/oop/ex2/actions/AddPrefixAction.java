//###############  
// FILE : AddPrefixAction.java  
// WRITER : Uri Greenberg, urig03, 021986039  
// WRITER : Yossi Mamo, ymamo29, 038073722
// EXERCISE : oop ex2 2010  
// DESCRIPTION: an action which renames the file, adding prefix at the
// beginning of its name (excluding path).
//###############

package oop.ex2.actions;

import java.io.File;
import java.io.IOException;

import oop.ex2.filescript.IOFailureException;
import oop.ex2.filescript.InvalidActionParametersException;

/**
 * an action which renames the file, adding prefix at the
 * beginning of its name (excluding path).
 * @author Uri Greenberg and Yossi Mamo.
 */
public class AddPrefixAction extends AbstractRenameAction {
	
	/// the name of the Action.
	public static final String _name = "ADD_PREFIX";
	
	/**
	 * constructs a new action with the given prefix String.
	 * @param prefix the String to be added at the beginning of the name.
	 * @throws InvalidActionParametersException in case the string contains
	 * an illegal character (spaces, '\', '/').
	 */
	public AddPrefixAction(String prefix) 
						throws InvalidActionParametersException {
		super(prefix);
	}

	/**
	 * adds the prefix to the beginning of the given file's name
	 * (excluding path).
	 * @param file the file to execute the action on.
	 * @param srcDirPath the full path of the source directory (used only in
	 * the CopyTo action)
	 * @throws IOFailureException in case there is an I/O error while
	 * trying to get the files canonical path.
	 */
	public void execute(File file, String srcDirPath)
					throws IOFailureException {
		try {
			String canonicalName = file.getCanonicalPath();
			int prefixInsertIndex = canonicalName.length()
									- file.getName().length();
			String newName = canonicalName.substring(0, prefixInsertIndex) +
					_string + canonicalName.substring(prefixInsertIndex);
			rename(file, newName);
		} catch (IOException e){
			throw new IOFailureException();
		}
	}
}
