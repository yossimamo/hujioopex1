package oop.ex2.filescript;

public class UnsupportedFilterException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public UnsupportedFilterException() {
		super("Error:Unsupported filter");
	}

}
