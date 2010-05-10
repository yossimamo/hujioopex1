//###############  
// FILE : AbsFunction.java  
// WRITER : Uri Greenberg, urig03, 021986039  
// WRITER : Yossi Mamo, ymamo29, 038073722
// EXERCISE : oop ex3 2010  
// DESCRIPTION: 
//###############

package oop.ex3.functions;

import java.util.LinkedList;
import oop.ex3.exceptions.IllegalArgumentException;

public class LogFunction extends Function {
	
	public static final int NUM_OF_ARGS = 1;
	
	public static final String NAME = "Log";

	public LogFunction() {
	}

	public Double calculate(LinkedList<Double> input)
									throws IllegalArgumentException {
		checkInput(input, NUM_OF_ARGS);
		Double solution = Math.log(input.getFirst());
		checkOutput(solution);
		return solution;
	}

	public String getFunctionName() {
		return NAME;
	}
}
