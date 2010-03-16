package oop.ex1.dataStructures;

/**
 * An interface which forces its implementors to be able to compare themselves
 * to other comparable elements.
 * @author OOP
 *
 */
public interface ComparableObject {
	/**
	 * @param other Object to compare to
	 * @return Positive integer if this element is bigger, negative if the
	 * other is bigger, and 0 if they are equal 
	 */
	int compare(ComparableObject other);
	
	/**
	 * Must be consistent with compare.
	 * @param other object to check equality against.
	 * @return true iff this element equals other.
	 */
	boolean equals(ComparableObject other);
}
