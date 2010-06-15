package oop.ex5.nameserver;

public class EndSessionException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public EndSessionException() {
		super("Error: Ending Session");
	}

}
