package oop.ex3.functions;

import java.util.LinkedList;

public class PlusOperator extends Operator {
	
	public static final String NAME = "Plus";
	private static final String SIGN = "+";

	public PlusOperator() {
		_sign = SIGN;
	}

	public Double calculate(LinkedList<Double> input)
							throws IllegalArgumentException {
		checkInputOperator(input);
		return input.getFirst() + input.getLast();
	}

	public String getFunctionName() {
		return NAME;
	}
}
