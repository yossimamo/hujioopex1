package oop.ex2.filescript;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyFileScript {
	
	private static final int SRC_DIR_ARGUMENT_INDEX = 0;
	private static final int CMD_FILE_ARGUMENT_INDEX = 1;
	private static final int ARGUMENTS = 2;

	/**
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
			
			CommandFile commandFile;
			try {
				commandFile = CommandFile.createFromFile(cmdFile);
			} catch (FileNotFoundException e) {
				throw new InvalidCommandLineParametersException();
			}
			
			commandFile.execute(srcDir);
		}
		catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

}
