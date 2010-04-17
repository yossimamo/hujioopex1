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
		/*Pattern patt = Pattern.compile("^\\((.+)\\)$");
		Matcher matcher = patt.matcher("(NOT (((FILE_WILDCARD:56* AND FILE_WILDCARD:*peg) AND FILE_WILDCARD:*6.p*) OR FILE_WILDCARD:ufo.jpeg)))");
		//Matcher matcher = patt.matcher("(SIZE_MORE_THAN:100 AND (SIZE_MORE_THAN:200 AND (SIZE_MORE_THAN:300 AND SIZE_MORE_THAN:400)))");
		//Matcher matcher = patt.matcher("(SIZE_MORE_THAN:100 AND (NOT (NOT SIZE_LESS_THAN:1000)))");
		if (!matcher.matches()) {
			System.out.println("no parentheses"); // not good
			return;
		}
		String s = matcher.group(1);
		patt = Pattern.compile("(NOT) (.+)$|(.+?) (AND|OR) (.+)$");
		matcher = patt.matcher(s);
		int i = 0;
		while (matcher.find(i)) {
			if (i == 0) {
				System.out.println(matcher.group(1)); // try matching this
				System.out.println(matcher.group(2));
				System.out.println(matcher.group(3)); // try matching this
				System.out.println(matcher.group(4));
				System.out.println(matcher.group(5));
			}
			if (0 != i) {
				System.out.println(s.substring(0, i) + matcher.group(1)); // try matching this
				System.out.println(matcher.group(3)); // try matching this
			}
			i = matcher.start(3);
		}*/
		
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
