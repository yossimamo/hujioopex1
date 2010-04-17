package oop.ex2.actions;

import java.io.File;
import java.io.IOException;

import oop.ex2.filescript.IOFailureException;

public class AddSuffixAction extends Action {
	
	public static final String _name = "ADD_SUFFIX";
	
	private String _suffix;

	public AddSuffixAction(String suffix) {
		_suffix = suffix;
	}

	public void execute(File file, String srcDirPath) throws IOFailureException {
		try {
			System.out.println(file.getCanonicalPath() + _suffix);
			File newName = new File(file.getCanonicalPath() + _suffix);
			System.out.println(file.renameTo(newName));
		} catch (IOException e){
			throw new IOFailureException();
		}
	}
	
}
