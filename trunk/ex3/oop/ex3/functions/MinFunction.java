package oop.ex3.functions;

import java.util.LinkedList;

import oop.ex3.exceptions.IllegalParametersException;

public class MinFunction extends Function {

	public static final String NAME = "Min";
	
	public MinFunction() {
	}

	public Double calculate(LinkedList<Double> input)
								throws IllegalArgumentException {
		if (input.isEmpty()) {
			throw new IllegalParametersException();
		}
		Double min = input.getFirst();
		for (int i=1; i<input.size() ; i++) {
			min = Math.min(min, input.get(i));
		}
		return min;
	}

	public String getFunctionName() {
		return NAME;
	}
}
