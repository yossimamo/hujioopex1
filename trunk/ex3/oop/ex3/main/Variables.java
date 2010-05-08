package oop.ex3.main;

import java.util.HashMap;

import oop.ex3.exceptions.UnititializedVariableException;

public class Variables {
	
	private HashMap<String, Double> _hashMap;
	
	public Variables() {
		_hashMap = new HashMap<String, Double>();
	}
	
	public Double getVariable(String name)
					throws UnititializedVariableException {
		//HashString key = new HashString(name);
		if (!_hashMap.containsKey(name)) {
			throw new UnititializedVariableException();
		}
		return (Double) _hashMap.get(name);
	}
	
	public void setVariable(String name, Double value) {
		//HashString key = new HashString(name);
		_hashMap.put(name, value);
	}
}
