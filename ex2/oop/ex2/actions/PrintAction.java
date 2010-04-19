//###############  
// FILE : PrintAction.java  
// WRITER : Uri Greenberg, urig03, 021986039  
// WRITER : Yossi Mamo, ymamo29, 038073722
// EXERCISE : oop ex2 2010  
// DESCRIPTION: an action which prints the full file's name into the given
// stream.
//###############

package oop.ex2.actions;

import java.io.File;
import java.io.IOException;
import oop.ex2.filescript.IOFailureException;
import oop.ex2.filescript.InvalidActionParametersException;

/**
 * an action which prints the full file's name into the given stream.
 * @author Uri Greenberg and Yossi Mamo.
 */
public class PrintAction extends Action {
	
	/// the name of the action.
	public static final String _name = "PRINT_TO";
	
	/// the name of the stream.
	private static final String STDERR = "STDERR";
	
	/// the name of the stream.
	private static final String STDOUT = "STDOUT";
	
	/// holds true if the stream is out and false otherwise.
	private boolean _isSTDOUT;

	/**
	 * constructs a new print action according to the given stream.
	 * @param streamName the name of the stream to be used.
	 * @throws InvalidActionParametersException in case the name of the 
	 * stream is not legal (not STDOUT or STDERR).
	 */
	public PrintAction(String streamName) 
			throws InvalidActionParametersException  {
		if (streamName.equals(STDOUT)) {
			_isSTDOUT = true;
		}
		else {
			if (streamName.equals(STDERR)) {
				_isSTDOUT = false;
			}
			else {
				throw new InvalidActionParametersException();
			}
		}
	}

	/**
	 * prints the full file's name into the given stream.
	 * @param file the file to execute the action on.
	 * @param srcDirPath the full path of the source directory (used only in
	 * the CopyTo action)
	 * @throws IOFailureException in case there is an I/O error while
	 * trying to get the file's canonical path.
	 */
	public void execute(File file, String srcDirPath)
								throws IOFailureException {
		try {
			if (_isSTDOUT) {
				System.out.println(file.getCanonicalPath());
			}
			else {
				System.err.println(file.getCanonicalPath());
			}
		} catch (IOException e) {
			throw new IOFailureException();
		}
	}
}
