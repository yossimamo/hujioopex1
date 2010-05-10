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

public abstract class Operator extends Function {

	public static final int NUM_OF_ARGS = 2;
	protected String _sign;

	public abstract Double calculate(LinkedList<Double> input)
									throws IllegalArgumentException;

	protected void checkInputOperator(LinkedList<Double> input)
								throws IllegalArgumentException {
		checkInput(input, NUM_OF_ARGS);
	}
	
	public String getSign() {
		return _sign;
	}
}
