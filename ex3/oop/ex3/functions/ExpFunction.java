package oop.ex3.functions;

import java.util.LinkedList;

public class ExpFunction extends Function {
	
	public static final int NUM_OF_ARGS = 1;
	
	public static final String NAME = "Exp";

	public ExpFunction() {
	}

	public Double calculate(LinkedList<Double> input)
							throws IllegalArgumentException {
		checkInput(input, NUM_OF_ARGS);
		Double solution = Math.exp(input.getFirst());
		checkOutput(solution);
		return solution;
	}

	public String getFunctionName() {
		return NAME;
	}
}
