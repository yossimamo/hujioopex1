//###############  
// FILE : CrosswordTerms.java  
// WRITER : Uri Greenberg, urig03, 021986039  
// WRITER : Yossi Mamo, ymamo29, 038073722
// EXERCISE : oop ex4 2010  
// DESCRIPTION: An interface for handling the terms and the definitions.
//###############

package oop.ex4.crosswords;

import java.util.Iterator;

/**
 * An interface for handling the terms and the definitions.
 * @author Uri Greenberg and Yossi Mamo.
 *
 */
public interface CrosswordTerms {
	
	/**
	 * Returns the number of terms in the dictionary.
	 * @return The number of terms in the dictionary.
	 */
	public int getNumberOfTerms();
	
	/**
	 * Returns true iff all terms have been inserted into the crossword.
	 * @return True iff all terms have been inserted into the crossword.
	 */
	public boolean isFullyOccupied();
	
	/**
	 * Returns the length of the longest word in the dictionary that hadn't 
	 * been inserted into the crossword.
	 * @return The length of the longest word in the dictionary that hadn't 
	 * been inserted into the crossword.
	 */
	public int getMaxAvailableTermLength();
	
	/**
	 * Returns the length of the shortest word in the dictionary that hadn't 
	 * been inserted into the crossword.
	 * @return The length of the shortest word in the dictionary that hadn't 
	 * been inserted into the crossword.
	 * @return
	 */
	public int getMinAvailableTermLength();
	
	/**
	 * Returns the definition associated with the given term.
	 * @param term A term.
	 * @return The definition associated with the given term.
	 */
	public String getTermDefinition(String term);
	
	/**
	 * This method is called when an entry is being added to the crossword.
	 * the method updates the dictionary databases accordingly.
	 * @param entry the entry which is being added to the crossword.
	 */
	public void addEntry(CrosswordEntry entry);
	
	/**
	 * This method is called when an entry is being removed from the crossword.
	 * the method updates the dictionary databases accordingly.
	 * @param entry the entry which is being removed from the crossword.
	 */
	public void removeEntry(CrosswordEntry entry);
	
	/**
	 * Returns an iterator which iterates on term that haven't been
	 * inserted into the crossword and that their
	 * length is between startLength and endLength.
	 * @param startLength the length of terms the iterator will
	 * start iterating from.
	 * @param endLength the length of terms the iterator will
	 * iterate up until.
	 * @return An iterator which iterates on term that haven't been
	 * inserted into the crossword and that their
	 * length is between startLength and endLength.
	 */
	public Iterator<String> getIterator(int startLength, int endLength);
}
