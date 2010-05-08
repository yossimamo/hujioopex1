package oop.ex3.functions;

import java.util.LinkedList;

public class TimesOperator extends Operator {
	
	public static final String NAME = "Times";
	private static final String SIGN = "*";

	
	public TimesOperator() {
		_sign = SIGN;
	}

	public Double calculate(LinkedList<Double> input)
							throws IllegalArgumentException {
		checkInputOperator(input);
		return input.getFirst() * input.getLast();
	}

	public String getFunctionName() {
		return NAME;
	}
}
