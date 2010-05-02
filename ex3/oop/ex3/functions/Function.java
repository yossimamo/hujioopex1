package oop.ex3.functions;

import java.util.LinkedList;

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
	@SuppressWarnings("unchecked")
	public abstract Double calculate(LinkedList<Double> input) throws IllegalArgumentException;
}
