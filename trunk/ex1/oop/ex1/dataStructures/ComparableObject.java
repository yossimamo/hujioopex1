package oop.ex1.dataStructures;

/**
 * An interface which forces its elements to be able to compare themselves to other
 * comparable elements.
 * @author OOP
 *
 */
public interface ComparableObject {
	/**
	 * @param other Object to compare to.
	 * @return > 0 - this element is bigger, < 0 - other is bigger, 0 - they are equal. 
	 */
	int compare(ComparableObject other);
	
	/**
	 * Must be consistent with compare.
	 * @param other object to check equality against.
	 * @return true iff this element equals other.
	 */
	boolean equals(ComparableObject other);
}
