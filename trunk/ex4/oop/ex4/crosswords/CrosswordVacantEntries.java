//###############  
// FILE : CrosswordVacantEntries.java  
// WRITER : Uri Greenberg, urig03, 021986039  
// WRITER : Yossi Mamo, ymamo29, 038073722
// EXERCISE : oop ex4 2010  
// DESCRIPTION: A class handling all vacant entries.
//###############

package oop.ex4.crosswords;

import java.util.Iterator;

/**
 * A class handling all vacant entries.
 * @author Uri Greenberg and Yossi Mamo.
 */
public interface CrosswordVacantEntries {
	
	/**
	 * Returns the total number of entries in the crossword. 
	 * @return The total number of entries in the crossword. 
	 */
	public int getNumberOfEntries();
	
	/**
	 * Returns the sum of all lengths of all entries.
	 * @return The sum of all lengths of all entries.
	 */
	public int getTotalEntryLengthsSum();
	
	/**
	 * returns the max capacity of the vacant entry at the given position.
	 * @param pos A position in the crossword.
	 * @return The max capacity of the vacant entry at the given position.
	 */
	public int getMaxCapacity(CrosswordPosition pos);
	
	/**
	 * Returns the length of the longest vacant entry in the crossword.
	 * @return The length of the longest vacant entry in the crossword.
	 */
	public int getMaxVacantEntryLength();
	
	/**
	 * Returns the length of the shortest vacant entry in the crossword.
	 * @return The length of the shortest vacant entry in the crossword.
	 */
	public int getMinVacantEntryLength();
	
	/**
	 * Returns true if there are no more vacant entries left or false
	 * otherwise.
	 * @return True if there are no more vacant entries left or false
	 * otherwise.
	 */
	public boolean isFullyOccupied();
	
	/**
	 * This method is called when a new entry is added to the crossword.
	 * it updates the database accordingly.
	 * @param entry The entry that is being added to the crossword.
	 */
	public void addEntry(CrosswordEntry entry);
	
	/**
	 * This method is called when a new entry is removed from the crossword.
	 * it updates the database accordingly.
	 * @param entry The entry that is being removed from the crossword.
	 */
	public void removeEntry(CrosswordEntry entry);
	
	/**
	 * Returns an iterator which iterates on vacant entries that their
	 * length is between startLength and endLength.
	 * @param startLength the length of vacant entries the iterator will
	 * start iterating from.
	 * @param endLength the length of vacant entries the iterator will
	 * iterate up until.
	 * @return An iterator which iterates on vacant entries that their
	 * length is between startLength and endLength.
	 */
	public Iterator<CrosswordVacantEntry> getIterator(int startLength,
															int endLength);
}

