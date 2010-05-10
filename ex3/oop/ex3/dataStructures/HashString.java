//###############  
// FILE : HashString.java  
// WRITER : Uri Greenberg, urig03, 021986039  
// WRITER : Yossi Mamo, ymamo29, 038073722
// EXERCISE : oop ex3 2010  
// DESCRIPTION: this class implements HashObject. turning a string into
// a key you can get a hash code of or compare to other keys from string.
//###############

package oop.ex3.dataStructures;

/**
 *  this class implements HashObject. turning a string into
 * a key you can get a hash code of or compare to other keys from strings.
 * @author Uri Greenberg and Yossi Mamo.
 */
public class HashString implements HashObject {

	//the string of this HashString
	private String _string;
	
	/**
	 * constructs a new HashString from the given string.
	 * @param string the string to construct the hashString from.
	 */
	public HashString(String string) {
		_string = string;
	}

	/**
	 * @param other another object
	 * @returns true iff the 2 objects are equals
	 */
	public boolean equals(HashObject other) {
		if (other instanceof HashString) {
			HashString otherHashString = (HashString)other;
			if (otherHashString._string.equals(_string)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @param maxNum the range of the required hash code.
	 * @returns the hashCode of the object, in the range [0-(maxNum-1)].
	 */
	public int hashCode(int maxNum) {
		return Math.abs(_string.hashCode() % maxNum);
	}
}
