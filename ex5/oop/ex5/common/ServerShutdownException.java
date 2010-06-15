package oop.ex5.common;

public class ServerShutdownException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public ServerShutdownException() {
		super("Server shutdown exception");
	}

}
