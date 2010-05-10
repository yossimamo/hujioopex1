//###############  
// FILE : Variables.java  
// WRITER : Uri Greenberg, urig03, 021986039  
// WRITER : Yossi Mamo, ymamo29, 038073722
// EXERCISE : oop ex3 2010  
// DESCRIPTION: this class is the programs interface with the variables.
// it holds them in a hashmap.
//###############

package oop.ex3.main;

import oop.ex3.dataStructures.ChainedHashMap;
import oop.ex3.dataStructures.HashString;
import oop.ex3.exceptions.UninitializedVariableException;

/**
 * this class is the programs interface with the variables.
 * it holds them in a hashmap.
 * @author Uri Greenberg and Yossi Mamo.
 */
public class Variables {
	
	// a hashmap holding the variables.
	private ChainedHashMap _hashMap;
	
	/**
	 * creates an empty hashmap for the variables.
	 */
	public Variables() {
		_hashMap = new ChainedHashMap();
	}
	
	/**
	 * returns the variable with the given name.
	 * @param name the name of the variable.
	 * @return the variable with the given name.
	 * @throws UninitializedVariableException in case no such variable exists.
	 */
	public Double getVariable(String name)
				throws UninitializedVariableException {
		HashString key = new HashString(name);
		if (!_hashMap.containsKey(key)) {
			throw new UninitializedVariableException();
		}
		return (Double) _hashMap.get(key);
	}
	
	/**
	 * sets a new variable with the given name and value.
	 * @param name the name of the new variable.
	 * @param value the value of the new variable.
	 */
	public void setVariable(String name, Double value) {
		HashString key = new HashString(name);
		_hashMap.put(key, value);
	}
}
