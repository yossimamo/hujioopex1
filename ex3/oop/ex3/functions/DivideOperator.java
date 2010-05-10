//###############  
// FILE : DivideOperator.java  
// WRITER : Uri Greenberg, urig03, 021986039  
// WRITER : Yossi Mamo, ymamo29, 038073722
// EXERCISE : oop ex3 2010  
// DESCRIPTION: this class is an operator which divides the first number
// in the input by the second number in the input.
//###############

package oop.ex3.functions;

import java.util.LinkedList;
import oop.ex3.exceptions.IllegalArgumentException;
import oop.ex3.exceptions.IllegalMathOperationException;

/**
 * this class is an operator which divides the first number
 * in the input by the second number in the input.
 * @author Uri Greenberg and Yossi Mamo
 */
public class DivideOperator extends Operator {
	
	//the name of the operator
	public static final String NAME = "Divide";
	
	// the sign of the operator
	private static final String SIGN = "/";

	/**
	 * constructs a new divide operator. 
	 */
	public DivideOperator() {
		_sign = SIGN;
	}

	/**
	 * calculates the first number in the input divided by the second one.
	 * @param input a linked list holding the input value/s.
	 * @throws IllegalArgumentException in case the arguments are illegal 
	 * (wrong number of parameters, wrong value, etc.)
	 * @return the first number in the input divided by the second one.
	 */
	public Double calculate(LinkedList<Double> input)
							throws IllegalArgumentException {
		checkInputOperator(input);
		if (input.getLast() == 0){
			throw new IllegalMathOperationException();  
		}
		return input.getFirst() / input.getLast();
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
