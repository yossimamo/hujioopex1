//###############  
// FILE : AddSuffixAction.java  
// WRITER : Uri Greenberg, urig03, 021986039  
// WRITER : Yossi Mamo, ymamo29, 038073722
// EXERCISE : oop ex2 2010  
// DESCRIPTION: an action which renames the file, adding suffix at the
// end of its name.
//###############

package oop.ex2.actions;

import java.io.File;
import java.io.IOException;

import oop.ex2.filescript.IOFailureException;
import oop.ex2.filescript.InvalidActionParametersException;

/**
 * an action which renames the file, adding suffix at the end of its name.
 * @author Uri Greenberg and Yossi Mamo.
 */
public class AddSuffixAction extends AbstractRenameAction {
	
	// the name of the Action.
	public static final String _name = "ADD_SUFFIX";

	/**
	 * constructs a new action with the given suffix String.
	 * @param suffix the String to be added at the end of the name.
	 * @throws InvalidActionParametersException in case the string contains
	 * an illegal character ( spaces, '\', '/').
	 */
	public AddSuffixAction(String suffix) 
						throws InvalidActionParametersException {
		super(suffix);
	}

	/**
	 * adds the suffix to the end of the given file's name.
	 * @param file the file to execute the action on.
	 * @param srcDirPath the full path of the source directory (used only in
	 * the CopyTo action)
	 * @throws IOFailureException in case there is an I/O error while
	 * trying to get the file's canonical path.
	 */
	public void execute(File file, String srcDirPath)
							throws IOFailureException {
		try {
			String newName = file.getCanonicalPath() + _string;
			rename(file, newName);
		} catch (IOException e){
			throw new IOFailureException();
		}
	}
}
