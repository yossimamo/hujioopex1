package oop.ex2.actions;

import java.io.File;
import java.io.IOException;

public abstract class Action {
	
	public abstract void execute(File file, String srcDirPath)
		throws IOException;

}
