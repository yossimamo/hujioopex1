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

import oop.ex3.exceptions.IllegalParametersException;

public class MaxFunction extends Function {
	
	public static final String NAME = "Max";

	public MaxFunction() {
	}

	public Double calculate(LinkedList<Double> input)
								throws IllegalArgumentException {
		if (input.isEmpty()) {
			throw new IllegalParametersException();
		}
		Double max = input.getFirst();
		for (int i=1; i<input.size() ; i++) {
			max = Math.max(max, input.get(i));
		}
		return max;
	}

	public String getFunctionName() {
		return NAME;
	}
}
