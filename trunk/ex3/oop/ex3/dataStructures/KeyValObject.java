//###############  
// FILE : KeyValObject.java  
// WRITER : Uri Greenberg, urig03, 021986039  
// WRITER : Yossi Mamo, ymamo29, 038073722
// EXERCISE : oop ex3 2010  
// DESCRIPTION: this class represents an object holding a key and a value.
// the hashmap hold object from this type.
//###############

package oop.ex3.dataStructures;

/**
 * this class represents an object holding a key and a value.
 * the hashmap hold object from this type.
 * @author Uri Greenberg and Yossi Mamo.
 *
 */
public class KeyValObject {
	
	// the key of this object.
	private HashObject _key;
	
	// the value of this object.
	private Object _value;

	/**
	 * constructs a new KeyValObject from the HashObject (key) and Object
	 * (value) received.
	 * @param key the key.
	 * @param value the value.
	 */
	public KeyValObject(HashObject key , Object value) {
		_key = key;
		_value = value;
	}
	
	/**
	 * returns the key.
	 * @return the key.
	 */
	public HashObject getKey() {
		return _key;
	}
	
	/**
	 * returns the value.
	 * @return the value.
	 */
	public Object getValue() {
		return _value;
	}
	
	/**
	 * sets a new value.
	 * @param value the new value.
	 */
	public void setValue(Object value) {
		_value = value;
	}
}
