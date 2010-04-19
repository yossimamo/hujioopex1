//###############  
// FILE : MyFileScript.java  
// WRITER : Uri Greenberg, urig03, 021986039  
// WRITER : Yossi Mamo, ymamo29, 038073722
// EXERCISE : oop ex2 2010  
// DESCRIPTION: A program which allows mass manipulation of files according
// to preconfigured filters and actions.
//###############

package oop.ex2.filescript;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * MyFileScript allows mass manipulation of files in a given source directory,
 * according to a given command file which specifies various filters which select
 * the desired files and and actions to perform on them.
 * Usage: java MyFileScript <source_dir> <command_file>
 * @author Uri Greenberg and Yossi Mamo
 *
 */
public class MyFileScript {
	
	/// Argument related constants
	private static final int SRC_DIR_ARGUMENT_INDEX = 0;
	private static final int CMD_FILE_ARGUMENT_INDEX = 1;
	private static final int ARGUMENTS = 2;

	/**
	 * Entry point of the MyFileScript program
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			if (ARGUMENTS != args.length) {
				throw new InvalidCommandLineParametersException();
			}
			String srcDirName = args[SRC_DIR_ARGUMENT_INDEX];
			File srcDir = new File(srcDirName);
			if (!srcDir.isDirectory()) {
				throw new InvalidCommandLineParametersException();
			}
			String cmdFilename = args[CMD_FILE_ARGUMENT_INDEX];
			File cmdFile = new File(cmdFilename);
			if (!cmdFile.isFile()) {
				throw new InvalidCommandLineParametersException();
			}
			// Read and build the command file
			CommandFile commandFile;
			try {
				commandFile = CommandFile.createFromFile(cmdFile);
			} catch (FileNotFoundException e) {
				throw new InvalidCommandLineParametersException();
			}
			
			// Execute commands
			commandFile.execute(srcDir);
		}
		catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

}
