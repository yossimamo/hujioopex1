//###############  
// FILE : PlusOperator.java  
// WRITER : Uri Greenberg, urig03, 021986039  
// WRITER : Yossi Mamo, ymamo29, 038073722
// EXERCISE : oop ex3 2010  
// DESCRIPTION: this class is an operator which sums the first number
// in the input with the second number in the input.
//###############

package oop.ex3.functions;

import java.util.LinkedList;
import oop.ex3.exceptions.IllegalArgumentException;

/**
 * this class is an operator which sums the first number
 * in the input with the second number in the input.
 * @author Uri Greenberg and Yossi Mamo
 */
public class PlusOperator extends Operator {
	
	//the name of the operator
	public static final String NAME = "Plus";
	
	// the sign of the operator
	private static final String SIGN = "+";

	/**
	 * constructs a new plus operator. 
	 */
	public PlusOperator() {
		_sign = SIGN;
	}

	/**
	 * calculates the first number in the input plus the second one.
	 * @param input a linked list holding the input value/s.
	 * @throws IllegalArgumentException in case the arguments are illegal 
	 * (wrong number of parameters, wrong value, etc.)
	 * @return the first number in the input plus the second one.
	 */
	public Double calculate(LinkedList<Double> input)
							throws IllegalArgumentException {
		checkInputOperator(input);
		return input.getFirst() + input.getLast();
	}

	/**
	 * returns the name of the function.
	 * @return the name of the function
	 */
	public String getFunctionName() {
		return NAME;
	}

	public boolean isAssignmentAllowed() {
		return true;
	}
}
