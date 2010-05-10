package oop.ex3.exceptions;

public class IllegalParametersException extends IllegalArgumentException {

	private static final long serialVersionUID = 1L;

	public IllegalParametersException() {
		super("error: wrong number of valid parameters");
	}

}
