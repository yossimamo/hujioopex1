package oop.ex2.actions;

import java.io.File;
import java.io.IOException;

public class AddPrefixAction extends Action {
	
	public static final String _name = "ADD_PREFIX";
	
	private String _prefix;
	
	public AddPrefixAction(String prefix) {
		_prefix = prefix;
	}

	public void execute(File file, String srcDirPath) throws IOException {
		String canonicalName = file.getCanonicalPath();
		int prefixInsertIndex = canonicalName.length() - file.getName().length();
		String newName = canonicalName.substring(0, prefixInsertIndex) +
				_prefix + canonicalName.substring(prefixInsertIndex);
		File newFile = new File(newName);
		file.renameTo(newFile);
	}

}
