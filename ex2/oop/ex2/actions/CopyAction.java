package oop.ex2.actions;

import java.io.*;

import oop.ex2.filescript.IOFailureException;

public class CopyAction extends Action {
	
	public static final String _name = "COPY_TO";
	
	private String _target;

	public CopyAction(String target) {
		_target = target;
	}

	public void execute(File file, String srcDirPath) throws IOFailureException {
		try {
			String newTarget = 
				_target + file.getCanonicalPath().substring(srcDirPath.length());
			File newFile = new File(newTarget);
			int fileNameIndex =  newTarget.length() - newFile.getName().length();
			File newFileDir = new File(newTarget.substring(0, fileNameIndex));
			newFileDir.mkdirs();
			newFile.createNewFile();
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
