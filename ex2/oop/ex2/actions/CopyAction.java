package oop.ex2.actions;

import java.io.*;

public class CopyAction extends Action {
	
	public static final String _name = "COPY_TO";
	
	private String _target;

	public CopyAction(String target) {
		_target = target;
	}

	public void execute(File file) throws IOException {
		File newFile = new File (_target);
		newFile.mkdirs();
		OutputStream output = new FileOutputStream(newFile);
		InputStream input = new FileInputStream(file);
		int result;
		while ((result = input.read()) != -1) {
			output.write(result);
		}
		output.close();
		input.close();
	}
}
