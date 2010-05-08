package oop.ex3.functions;

import java.util.LinkedList;

public class DivideOperator extends Operator {
	
	public static final String NAME = "Divide";
	private static final String SIGN = "/";

	public DivideOperator() {
		_sign = SIGN;
	}

	public Double calculate(LinkedList<Double> input)
							throws IllegalArgumentException {
		checkInputOperator(input);
		if (input.getLast() == 0){
			throw new IllegalArgumentException();  
		}
		return input.getFirst() / input.getLast();
	}

	public String getFunctionName() {
		return NAME;
	}
}
