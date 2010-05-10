//###############  
// FILE : Operator.java  
// WRITER : Uri Greenberg, urig03, 021986039  
// WRITER : Yossi Mamo, ymamo29, 038073722
// EXERCISE : oop ex3 2010  
// DESCRIPTION: an abstract class representing all operators.
//###############

package oop.ex3.functions;

import java.util.LinkedList;
import oop.ex3.exceptions.IllegalArgumentException;

/**
 * an abstract class representing all operators.
 * @author Uri Greenberg and Yossi Mamo
 *
 */
public abstract class Operator extends Function {

	// the number of arguments the operator is supposed to get in the input.
	public static final int NUM_OF_ARGS = 2;
	
	// the sign of the operator.
	protected String _sign;

	/**
	 * calculates the operator for the input number.
	 * @param input a linked list holding the input value/s.
	 * @throws IllegalArgumentException in case the arguments are illegal 
	 * (wrong number of parameters, wrong value, etc.)
	 * @return the calculation of the operator and the two input numbers.
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
	protected void checkInputOperator(LinkedList<Double> input)
								throws IllegalArgumentException {
		checkInput(input, NUM_OF_ARGS);
	}
	
	/**
	 * returns the sign of the operator.
	 * @return the sign of the operator.
	 */
	public String getSign() {
		return _sign;
	}

	public abstract boolean isAssignmentAllowed();
}
