package oop.ex3.dataStructures;

public class KeyValObject {
	
	private HashObject _key;
	
	private Object _value;

	public KeyValObject(HashObject key , Object value) {
		_key = key;
		_value = value;
	}
	
	public HashObject getKey() {
		return _key;
	}
	
	public Object getValue() {
		return _value;
	}
	
	public void setValue(Object value) {
		_value = value;
	}
}
