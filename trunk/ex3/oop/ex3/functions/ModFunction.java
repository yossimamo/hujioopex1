//###############  
// FILE : ModFunction.java  
// WRITER : Uri Greenberg, urig03, 021986039  
// WRITER : Yossi Mamo, ymamo29, 038073722
// EXERCISE : oop ex3 2010  
// DESCRIPTION: this class is a function which returns the modulo of two 
// numbers.
//###############

package oop.ex3.functions;

import java.util.LinkedList;
import oop.ex3.exceptions.IllegalArgumentException;

import oop.ex3.exceptions.IllegalMathOperationException;

/**
 * this class is a function which returns the modulo of two 
 * numbers.
 * @author Uri Greenberg and Yossi Mamo
 *
 */
public class ModFunction extends Operator {
	
	// the number of arguments the function is supposed to get in the input.
	public static final int NUM_OF_ARGS = 2;
	
	//the name of the function
	public static final String NAME = "Mod";
	
	// the sign of the overloaded operator
	private static final String SIGN = "%";

	/**
	 * an empty constructor.
	 */
	public ModFunction() {
		_sign = SIGN;
	}

	/**
	 * calculates the first number modulo the second one.
	 * @param input a linked list holding the input value/s.
	 * @throws IllegalArgumentException in case the arguments are illegal 
	 * (wrong number of parameters, wrong value, etc.)
	 * @return the first number modulo the second number.
	 */
	public Double calculate(LinkedList<Double> input)
								throws IllegalArgumentException {
		checkInput(input, NUM_OF_ARGS);
		if (input.getLast() == 0){
			throw new IllegalMathOperationException();  
		}
		Double result = input.getFirst() % input.getLast();
		checkOutput(result);
		return result;
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
