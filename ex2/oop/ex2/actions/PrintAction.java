package oop.ex2.actions;

import java.io.File;
import java.io.IOException;

import oop.ex2.filescript.InvalidActionParametersException;

public class PrintAction extends Action {
	
	public static final String _name = "PRINT_TO";
	
	private static final String STDERR = "STDERR";
	
	private static final String STDOUT = "STDOUT";
	
	private boolean _isSTDOUT;


	public PrintAction(String streamName) 
			throws InvalidActionParametersException {
		if (streamName.equals(STDOUT)) {
			_isSTDOUT = true;
		}
		else {
			if (streamName.equals(STDERR)) {
				_isSTDOUT = false;
			}
			else {
				throw new InvalidActionParametersException();
			}
		}
	}

	public void execute(File file) {
		if (_isSTDOUT){
			try {
				System.out.println(file.getCanonicalPath());
			} catch (IOException e) {
				throw new IOFailure();
			}
		}
	}

}
