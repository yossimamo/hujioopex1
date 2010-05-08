package oop.ex3.main;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import oop.ex3.exceptions.IllegalFunctionException;
import oop.ex3.functions.Function;

public class Functions {
	
	private HashMap<String, Object> _hashMap;
	
	public Functions() {
		_hashMap = new HashMap<String, Object>();
	}
	
	public Function getFunction(String name)
		throws IllegalFunctionException, IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException {
		//HashString key = new HashString(name);
		if (_hashMap.containsKey(name)) {
			return (Function) _hashMap.get(name);
		}
		try {
			Class function =
				Class.forName("oop.ex3.functions." + name + "Function");
			Constructor[] constructors = function.getConstructors();
			return (Function) constructors[0].newInstance();
		} catch (ClassNotFoundException e) {
			throw new IllegalFunctionException();
		}
	}
}
