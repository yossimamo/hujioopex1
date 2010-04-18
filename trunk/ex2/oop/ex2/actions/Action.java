//###############  
// FILE : Action.java  
// WRITER : Uri Greenberg, urig03, 021986039  
// WRITER : Yossi Mamo, ymamo29, 038073722
// EXERCISE : oop ex2 2010  
// DESCRIPTION: an abstract class which represents an action. has a method
// which is common to all actions, execute.
//###############

package oop.ex2.actions;

import java.io.File;
import oop.ex2.filescript.IOFailureException;

/**
 * an abstract class which represents an action. has a method
 * which is common to all actions, execute.
 * @author Uri Greenberg and Yossi Mamo.
 *
 */
public abstract class Action {
	
	/**
	 * executes the action on the file given as a parameter.
	 * @param file the file to execute the action on.
	 * @param srcDirPath the full path of the source directory (used only in
	 * the CopyTo action)
	 * @throws IOFailureException in case there is an I/O error while
	 * working with the given file.
	 */
	public abstract void execute(File file, String srcDirPath) 
		throws IOFailureException;
}
