package oop.ex3.functions;

import java.util.LinkedList;

public class PowFunction extends Function {

	public static final int NUM_OF_ARGS = 2;
	
	public static final String NAME = "Pow";
	
	public PowFunction() {
	}

	public Double calculate(LinkedList<Double> input)
								throws IllegalArgumentException {
		checkInput(input, NUM_OF_ARGS);
		Double solution = Math.pow(input.getFirst(), input.getLast());
		checkOutput(solution);
		return solution;
	}

	public String getFunctionName() {
		return NAME;
	}
}
