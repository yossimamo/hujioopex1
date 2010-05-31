//###############  
// FILE : CrosswordEntry.java  
// WRITER : Uri Greenberg, urig03, 021986039  
// WRITER : Yossi Mamo, ymamo29, 038073722
// EXERCISE : oop ex4 2010  
// DESCRIPTION: A crossword entry is a word at a given position in a crossword.
//###############

package oop.ex4.crosswords;

import oop.ex4.search.SearchMove;

/**
 * A crossword entry is a word at a given position in a crossword.
 * 
 * @author Dima
 */
public interface CrosswordEntry extends SearchMove {

	/**
	 * Returns the X/Y/Vertical position of an entry
	 * @return
	 */
	public CrosswordPosition getPosition();
	/**
	 * Returns the corresponding definition for the entry
	 * 
	 * @return the corresponding dictionary definition
	 */
	public String getDefinition();

	/**
	 * Returns the corresponding word for the entry
	 * 
	 * @return the corresponding term
	 */
	public String getTerm();
		
	/**
	 * Retrieves length of the entry (Redundant convenience, may be calculated
	 * through getTerm())
	 * 
	 * @return number of letters in term
	 */
	public int getLength();

		
}
