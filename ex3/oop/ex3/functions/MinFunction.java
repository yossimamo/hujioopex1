//###############  
// FILE : MinFunction.java  
// WRITER : Uri Greenberg, urig03, 021986039  
// WRITER : Yossi Mamo, ymamo29, 038073722
// EXERCISE : oop ex3 2010  
// DESCRIPTION: this class is a function which returns the minimum number
// of all the inputs numbers.
//###############

package oop.ex3.functions;

import java.util.LinkedList;
import oop.ex3.exceptions.IllegalArgumentException;

import oop.ex3.exceptions.IllegalParametersException;

/**
 * this class is a function which returns the minimum number
 * of all the inputs numbers.
 * @author Uri Greenberg and Yossi Mamo
 *
 */
public class MinFunction extends Function {

	//the name of the function
	public static final String NAME = "Min";
	
	/**
	 * an empty constructor.
	 */
	public MinFunction() {
	}

	/**
	 * returns the minimum number of the input numbers.
	 * @param input a linked list holding the input value/s.
	 * @throws IllegalArgumentException in case the arguments are illegal 
	 * (wrong number of parameters, wrong value, etc.)
	 * @return the minimum number of the input numbers.
	 */
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

	/**
	 * returns the name of the function.
	 * @return the name of the function
	 */
	public String getFunctionName() {
		return NAME;
	}
}
