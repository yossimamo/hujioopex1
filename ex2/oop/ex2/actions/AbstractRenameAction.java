//###############  
// FILE : AbstractRenameAction.java  
// WRITER : Uri Greenberg, urig03, 021986039  
// WRITER : Yossi Mamo, ymamo29, 038073722
// EXERCISE : oop ex2 2010  
// DESCRIPTION: an abstract class which represents all actions that rename
// the file.
//###############

package oop.ex2.actions;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import oop.ex2.filescript.IOFailureException;
import oop.ex2.filescript.InvalidActionParametersException;

/**
 * an abstract class which represents all actions that rename
 * the file.
 * @author Uri Greenberg and Yossi Mamo.
 */
public abstract class AbstractRenameAction extends Action {
	
	/// a regex that checks if the string contains illegal
	/// character ( spaces, '\', '/').
	private static final String VALID_PARAM_REGEX = "[^\\s\\\\/]*";

	/// the String to be added to the name.
	protected String _string;
	
	/**
	 * constructs a new action with the given string.
	 * @param string the string to be added to the name.
	 * @throws InvalidActionParametersException in case the string contains
	 * an illegal character (spaces, '\', '/').
	 */
	public AbstractRenameAction(String string)
							throws InvalidActionParametersException {
		Pattern patt = Pattern.compile(VALID_PARAM_REGEX);
		Matcher matcher = patt.matcher(string);
		if (!matcher.matches()) {
			throw new InvalidActionParametersException();
		}
		_string = string;
	}

	/**
	 * adds the string to the given file's name.
	 * @param file the file to execute the action on.
	 * @param srcDirPath the full path of the source directory (used only in
	 * the CopyTo action)
	 * @throws IOFailureException in case there is an I/O error while
	 * trying to get the files canonical path.
	 */
	public abstract void execute(File file, String srcDirPath)
											throws IOFailureException;
	
	/**
	 * receives a file and a new name. and renames the file accordingly.
	 * @param file a file.
	 * @param newName the files new name.
	 */
	protected void rename(File file, String newName){
		File newFile = new File(newName);
		file.renameTo(newFile);
	}
}
