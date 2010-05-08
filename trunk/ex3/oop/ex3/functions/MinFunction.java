package oop.ex3.functions;

import java.util.LinkedList;

public class MinFunction extends Function {

	public static final String NAME = "Min";
	
	public MinFunction() {
	}

	public Double calculate(LinkedList<Double> input)
								throws IllegalArgumentException {
		if (input.isEmpty()) {
			throw new IllegalArgumentException();
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
