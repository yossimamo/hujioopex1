//###############  
// FILE : Operators.java  
// WRITER : Uri Greenberg, urig03, 021986039  
// WRITER : Yossi Mamo, ymamo29, 038073722
// EXERCISE : oop ex3 2010  
// DESCRIPTION: this class is the programs interface with the operators.
// it holds them in a hashmap.
//###############

package oop.ex3.main;

import oop.ex3.dataStructures.ChainedHashMap;
import oop.ex3.dataStructures.HashString;
import oop.ex3.exceptions.IllegalOperatorException;
import oop.ex3.functions.*;

/**
 * this class is the programs interface with the operators.
 * it holds them in a hashmap.
 * @author Uri Greenberg and Yossi Mamo.
 */
public class Operators {
	
	// a hashmap holding the operators.
	private ChainedHashMap _hashMap;
	
	/**
	 * creates a new hashmap and inserts all the operators into it.
	 */
	public Operators() {
		_hashMap = new ChainedHashMap();
		// Add standard operators
		Operator op = new PlusOperator();
		HashString key = new HashString(op.getSign());
		_hashMap.put(key, op);
		op = new MinusOperator();
		key = new HashString(op.getSign());
		_hashMap.put(key, op);
		op = new TimesOperator();
		key = new HashString(op.getSign());
		_hashMap.put(key, op);
		op = new DivideOperator();
		key = new HashString(op.getSign());
		_hashMap.put(key, op);
		// Add overloaded operators
		op = new PowFunction();
		key = new HashString(op.getSign());
		_hashMap.put(key, op);
		op = new ModFunction();
		key = new HashString(op.getSign());
		_hashMap.put(key, op);
	}
	
	/**
	 * returns the object of the given sign operator.
	 * @param operatorSign the sign of the operator.
	 * @return the object of the given sign operator.
	 * @throws IllegalOperatorException in case the operator does not
	 * exist.
	 */
	public Operator getOperator(String operatorSign)
					throws IllegalOperatorException{
		HashString key = new HashString(operatorSign);
		if (!_hashMap.containsKey(key)) {
			throw new IllegalOperatorException();
		}
		return (Operator) _hashMap.get(key);
	}
}
