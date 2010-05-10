package oop.ex3.functions;

import java.util.LinkedList;

import oop.ex3.exceptions.IllegalMathOperationException;

public class ModFunction extends Function {
	
	public static final int NUM_OF_ARGS = 2;
	
	public static final String NAME = "Mod";

	public ModFunction() {
	}

	public Double calculate(LinkedList<Double> input)
								throws IllegalArgumentException {
		checkInput(input, NUM_OF_ARGS);
		if (input.getLast() == 0){
			throw new IllegalMathOperationException();  
		}
		return input.getFirst() % input.getLast();
	}

	public String getFunctionName() {
		return NAME;
	}
}
