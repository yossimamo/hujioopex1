//###############  
// FILE : CopyAction.java  
// WRITER : Uri Greenberg, urig03, 021986039  
// WRITER : Yossi Mamo, ymamo29, 038073722
// EXERCISE : oop ex2 2010  
// DESCRIPTION: an action which copies a file (and its path from the source
// directory) to the given target directory.
//###############

package oop.ex2.actions;

import java.io.*;
import oop.ex2.filescript.IOFailureException;

/**
 * an action which copies a file (and its path from the source
 * directory) to the given target directory.
 * @author Uri Greenberg and Yossi Mamo.
 */
public class CopyAction extends Action {
	
	// the name of the Action.
	public static final String _name = "COPY_TO";
	
	// the path to copy the file to.
	private String _target;

	/**
	 * constructs a new copy action with the given String as the target.
	 * @param target the target to copy the file to.
	 */
	public CopyAction(String target) {
		_target = target;
	}

	/**
	 * copies the file and its path (excluding the path of the source 
	 * directory) to the target.
	 * @param file the file to execute the action on.
	 * @param srcDirPath the full path of the source directory (used only in
	 * the CopyTo action)
	 * @throws IOFailureException in case there is an I/O error while
	 * trying to get the file's canonical path.
	 */
	public void execute(File file, String srcDirPath) 
								throws IOFailureException {
		try {
			// getting the targets absolute path.
			String newTarget = 
				_target + 
				file.getCanonicalPath().substring(srcDirPath.length());
			File newFile = new File(newTarget);
			// creating the directories in the files new path.
			int fileNameIndex =  newTarget.length() -
								 newFile.getName().length();
			File newFileDir = new File(newTarget.substring(0, fileNameIndex));
			newFileDir.mkdirs();
			// create the new file in the new target.
			newFile.createNewFile();
			// copy the content of the old file into the new one.
			OutputStream output = new FileOutputStream(newFile);
			InputStream input = new FileInputStream(file);
			int result;
			while ((result = input.read()) != -1) {
				output.write(result);
			}
			output.close();
			input.close();
		} catch (IOException e){
			throw new IOFailureException();
		}
	}
}
