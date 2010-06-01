//###############  
// FILE : CrosswordVacantEntry.java  
// WRITER : Uri Greenberg, urig03, 021986039  
// WRITER : Yossi Mamo, ymamo29, 038073722
// EXERCISE : oop ex4 2010  
// DESCRIPTION: A crossword vacant entry is an empty entry (an entry you can 
// put word in).
//###############

package oop.ex4.crosswords;

/**
 * A crossword vacant entry is an empty entry (an entry you can 
 * put word in).
 * @author Uri Greenberg and Yossi Mamo.
 *
 */
public abstract class CrosswordVacantEntry implements Comparable<CrosswordVacantEntry> {
	
	/**
	 * Returns the position of the vacant entry.
	 * @return The position of the vacant entry.
	 */
	public abstract CrosswordPosition getPosition();
	
	/**
	 * Returns the max capacity of the vacant entry.
	 * @return The max capacity of the vacant entry.
	 */
	public abstract int getMaxCapacity();
	
	/**
	 * Compares this vacant entry to another. first by max capacity, then
	 * by the position (x,y,is vertical). returns a positive int if this 
	 * vacant entry is "bigger" and a negative int if the other is "bigger".
	 * @param other another CrosswordVacantEntry. 
	 */
	public abstract int compareTo(CrosswordVacantEntry other);
	
}
