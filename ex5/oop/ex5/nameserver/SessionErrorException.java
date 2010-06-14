package oop.ex5.nameserver;

public class SessionErrorException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public SessionErrorException() {
		super("Error: Session Error");
	}

}
