package oop.ex2.actions;

import java.io.File;
import java.io.IOException;

public class AddPrefixAction extends Action {
	
	public static final String _name = "ADD_PREFIX";
	
	private String _prefix;
	
	public AddPrefixAction(String prefix) {
		_prefix = prefix;
	}

	public void execute(File file) throws IOException {
		String canonicalName = file.getCanonicalPath();
		int i = canonicalName.lastIndexOf(File.separator);
//		if (-1 == i) {
//			  throw new InvalidActionParametersException();
//		}
		if (i==-1) i=0; // TODO fix
		String newName = canonicalName.substring(0, i) + _prefix + canonicalName.substring(i + 1);
		File newFile = new File(newName);
		file.renameTo(newFile);
	}

}
