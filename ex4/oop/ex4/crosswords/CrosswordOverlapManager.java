//###############  
// FILE : CrosswordOverlapManager.java  
// WRITER : Uri Greenberg, urig03, 021986039  
// WRITER : Yossi Mamo, ymamo29, 038073722
// EXERCISE : oop ex4 2010  
// DESCRIPTION: This class is an interface in charge of checking the 
// overlapping of words and entries in the crossword.
//###############

package oop.ex4.crosswords;

/**
 * This class is an interface in charge of checking the 
 * overlapping of words and entries in the crossword.
 * @author Uri Greenberg and Yossi Mamo.
 */
public interface CrosswordOverlapManager {
	
	/**
	 * when a new entry is being entered into the crossword this method
	 * is called to update the database accordingly.(assuming the word fits).
	 * @param entry the entry that is being added to the crossword.
	 */
	public void addEntry (CrosswordEntry entry);
	
	/**
	 * when an entry is being removed from the crossword this method
	 * is called to update the database accordingly.(assuming the word is in
	 * the database).
	 * @param entry the entry that is being removed from the crossword.
	 */
	public void removeEntry (CrosswordEntry entry);
	
	/**
	 * receives a term and a vacantEntry and determines if it is possible 
	 * to insert the term into the entry.
	 * @param term a term
	 * @param vacantEntry a vacant entry
	 * @return true if it is possible to insert the term into the entry or 
	 * false otherwise.
	 */
	public boolean isMatch(String term, CrosswordVacantEntry vacantEntry);
	
	/**
	 * receives a term and a vacant entry and returns the number of 
	 * overlapping letters they have. or MISMATCH if they do not fit.
	 * @param term a term
	 * @param vacantEntry a vacant entry in the crossword
	 * @return the number of overlapping letters they have.
	 * or MISMATCH if they do not fit.
	 */
	public int getOverlapCount(String term, CrosswordVacantEntry vacantEntry);

}
