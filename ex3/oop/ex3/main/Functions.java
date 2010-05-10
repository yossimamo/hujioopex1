//###############  
// FILE : Functions.java  
// WRITER : Uri Greenberg, urig03, 021986039  
// WRITER : Yossi Mamo, ymamo29, 038073722
// EXERCISE : oop ex3 2010  
// DESCRIPTION: this class is the programs interface with the functions.
// it looks them up if needed and saves them in a hashmap it holds.
//###############

package oop.ex3.main;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import oop.ex3.dataStructures.ChainedHashMap;
import oop.ex3.dataStructures.HashString;
import oop.ex3.exceptions.IllegalFunctionException;
import oop.ex3.functions.Function;
import oop.ex3.exceptions.IllegalArgumentException;

/**
 * this class is the programs interface with the functions.
 * it looks them up if needed and saves them in a hashmap it holds.
 * @author Uri Greenberg and Yossi Mamo.
 */
public class Functions {
	
	// a hashmap holding the functions that were already used.
	private ChainedHashMap _hashMap;
	
	/**
	 * creates a new hashmap.
	 */
	public Functions() {
		_hashMap = new ChainedHashMap();
	}
	
	/**
	 * receives the name of the function and returns the function it self.
	 * if it has been used before it will be brought from the hashmap and if
	 * not it will be brought directly from the package.
	 * @param name the name of the function.
	 * @return the function it self.
	 * if it has been used before it will be brought from the hashmap and if
	 * not it will be brought directly from the package.
	 * @throws IllegalArgumentException in case the function is not found
	 * or the function has no legal constructors.
	 */
	@SuppressWarnings("unchecked")
	public Function getFunction(String name)
				throws IllegalArgumentException {
		HashString key = new HashString(name);
		if (_hashMap.containsKey(key)) {
			return (Function) _hashMap.get(key);
		}
		try {
			// creates a class of the function if exists in the package. 
			Class function =
				Class.forName("oop.ex3.functions." + name + "Function");
			//gets the constructors from the class.
			Constructor[] constructors = function.getConstructors();
			//throws an exception if there are no constructors.
			if (constructors.length == 0) {
				throw new IllegalFunctionException();
			}
			Function newFunc = (Function) constructors[0].newInstance();
			_hashMap.put(key, newFunc);
			return newFunc;
		} 
		catch (ClassNotFoundException e) {
			throw new IllegalFunctionException();
		} 
		catch (IllegalArgumentException e) {
			throw new IllegalFunctionException();
		} 
		catch (InstantiationException e) {
			throw new IllegalFunctionException();
		} 
		catch (IllegalAccessException e) {
			throw new IllegalFunctionException();
		} 
		catch (InvocationTargetException e) {
			throw new IllegalFunctionException();	
		}
	}
}
