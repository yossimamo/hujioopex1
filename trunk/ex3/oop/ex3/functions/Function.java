//###############  
// FILE : Function.java  
// WRITER : Uri Greenberg, urig03, 021986039  
// WRITER : Yossi Mamo, ymamo29, 038073722
// EXERCISE : oop ex3 2010  
// DESCRIPTION: an abstract class representing all functions and operators.
//###############

package oop.ex3.functions;

import java.util.LinkedList;
import oop.ex3.exceptions.IllegalMathOperationException;
import oop.ex3.exceptions.IllegalParametersException;
import oop.ex3.exceptions.IllegalArgumentException;

/**
 * A function abstract class. 
 * Each function in the interpreter system should implement it.
 *
 * Contains 2 basic methods: 
 * A method which return the function's name and a method which calculate 
 * the result of the function on input data.
 *
 * @author oop
 */

public abstract class Function {
	
	/**
	 * @returns the functions name.
	 */
	public abstract String getFunctionName();
	
	/**
	 * Calculates the function for the given variables. 
	 * @throws IllegalArgumentException in case the arguments are illegal 
	 * (wrong number of parameters, wrong value, etc.)
	 */
	public abstract Double calculate(LinkedList<Double> input)
									throws IllegalArgumentException;
	
	/**
	 * checks if the input is not null, if the number of parameters given
	 * in the input is legal, and if all the parameters are not null.
	 * @param input a linked list holding all the parameters.
	 * @param numOfArgs the number of parameters the specified function
	 * is supposed to get.
	 * @throws IllegalArgumentException in case one of the cases written above
	 * happens.
	 */
	protected void checkInput(LinkedList<Double> input, int numOfArgs)
											throws IllegalArgumentException {
		if (input == null || input.size() != numOfArgs) {
			throw new IllegalParametersException();
		}
		for (int i=0; i < input.size(); i++) {
			if (input.get(i) == null) {
				throw new IllegalParametersException();
			}
		}
	}
	
	/**
	 * checks if there were any illegal calculations.
	 * @param solution the solution given after the calculation was made.
	 * @throws IllegalArgumentException in case any illegal calculation was
	 * made.
	 */
	protected void checkOutput(Double solution)
								throws IllegalArgumentException {
		if (solution.isInfinite() || solution.isNaN()) {
			throw new IllegalMathOperationException();
		}
	}
}
