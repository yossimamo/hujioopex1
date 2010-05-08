package oop.ex3.functions;

import java.util.LinkedList;

public abstract class Operator extends Function {

	public static final int NUM_OF_ARGS = 2;
	protected String _sign;

	public abstract Double calculate(LinkedList<Double> input)
									throws IllegalArgumentException;

	protected void checkInputOperator(LinkedList<Double> input)
								throws IllegalArgumentException {
		checkInput(input, NUM_OF_ARGS);
	}
	
	public String getSign() {
		return _sign;
	}
}
