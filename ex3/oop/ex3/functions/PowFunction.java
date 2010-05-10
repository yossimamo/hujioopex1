//###############  
// FILE : PowFunction.java  
// WRITER : Uri Greenberg, urig03, 021986039  
// WRITER : Yossi Mamo, ymamo29, 038073722
// EXERCISE : oop ex3 2010  
// DESCRIPTION: this class is a function which returns the power of two
// numbers.
//###############

package oop.ex3.functions;

import java.util.LinkedList;
import oop.ex3.exceptions.IllegalArgumentException;

/**
 * this class is a function which returns the power of two
 * numbers.
 * @author Uri Greenberg and Yossi Mamo
 *
 */
public class PowFunction extends Operator {

	// the number of arguments the function is supposed to get in the input.
	public static final int NUM_OF_ARGS = 2;
	
	//the name of the function
	public static final String NAME = "Pow";
	
	// the sign of the overloaded operator
	private static final String SIGN = "**";
	
	/**
	 * an empty constructor.
	 */
	public PowFunction() {
		_sign = SIGN;
	}

	/**
	 * calculates the first number powered by the second one.
	 * @param input a linked list holding the input value/s.
	 * @throws IllegalArgumentException in case the arguments are illegal 
	 * (wrong number of parameters, wrong value, etc.)
	 * @return the first number powered by the second one.
	 */
	public Double calculate(LinkedList<Double> input)
								throws IllegalArgumentException {
		checkInput(input, NUM_OF_ARGS);
		Double solution = Math.pow(input.getFirst(), input.getLast());
		checkOutput(solution);
		return solution;
	}

	/**
	 * returns the name of the function.
	 * @return the name of the function
	 */
	public String getFunctionName() {
		return NAME;
	}

	public boolean isAssignmentAllowed() {
		return false;
	}
}
