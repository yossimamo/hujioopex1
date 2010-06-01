//###############  
// FILE : CrosswordPosition.java  
// WRITER : Uri Greenberg, urig03, 021986039  
// WRITER : Yossi Mamo, ymamo29, 038073722
// EXERCISE : oop ex4 2010  
// DESCRIPTION: Specifies a basic position of an entry in a crossword.
//###############

package oop.ex4.crosswords;

/**
 * Specifies a basic position of an entry in a crossword
 * @author Dima
 *
 */
public interface CrosswordPosition {

	/**
	 * @return The x coordinate
	 */
	public abstract int getX();

	/**
	 * 
	 * @return The Y coordinate
	 */
	public abstract int getY();

	/**
	 * 
	 * @return True iff position is a vertical position
	 */
	public abstract boolean isVertical();

}