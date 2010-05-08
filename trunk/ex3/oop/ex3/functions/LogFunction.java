package oop.ex3.functions;

import java.util.LinkedList;

public class LogFunction extends Function {
	
	public static final int NUM_OF_ARGS = 1;
	
	public static final String NAME = "Log";

	public LogFunction() {
	}

	public Double calculate(LinkedList<Double> input)
									throws IllegalArgumentException {
		checkInput(input, NUM_OF_ARGS);
		if (input.getFirst() <= 0) {
			throw new IllegalArgumentException();
		}
		return Math.log(input.getFirst());
	}

	public String getFunctionName() {
		return NAME;
	}
}
