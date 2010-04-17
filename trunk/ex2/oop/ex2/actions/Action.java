package oop.ex2.actions;

import java.io.File;

import oop.ex2.filescript.IOFailureException;

public abstract class Action {
	
	public abstract void execute(File file, String srcDirPath) 
		throws IOFailureException;

}
