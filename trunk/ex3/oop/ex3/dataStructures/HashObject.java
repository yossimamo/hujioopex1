package oop.ex3.dataStructures;

/**
 * An object in your HashMap class. 
 * Supplies 2 methods: hashCode of the object and a comparison method
 * to see if 2 object are equal.
 *
 * @author oop
 */
public interface HashObject {
	/**
	 * @param maxNum the range of the required hash code.
	 * @returns the hashCode of the object, in the range [0-(maxNum-1)].
	 */
	public int hashCode(int maxNum);

	/**
	 * @param other another object
	 * @returns true iff the 2 objects are equals
	 */	
	public boolean equals(HashObject other);
}
