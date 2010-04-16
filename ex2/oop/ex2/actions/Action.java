package oop.ex2.actions;

import java.io.File;

public abstract class Action {
	
	private static String _name;
	
	public static String getName() {
		return _name;
	}
	
	public void execute(File file) {
		
	}

}
