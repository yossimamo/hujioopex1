package oop.ex4.crosswords;

import oop.ex4.search.SearchBoard;

import java.util.Collection;

/**
 * 
 * An empty Crossword consists of: - a dictionary (loaded from dictionary file)
 * - a shape (loaded from shape file)
 * 
 * The partially or fully "filled" Crossword in addition 
 * should be able to return a list of crossword entries
 * 
 * You can assume (and don't have to check) that call to SearchBoard's interface
 * methods and call to getCrosswordEntries() will always come after calls to
 * attachDictionary and attachShape methods and
 * that attachDictionary and attachShape will be called only once per crossword
 * 
 * 
 * 
 * @author Dima
 */
public interface Crossword extends SearchBoard<CrosswordEntry>  {

	/**
	 * Initializes all structures associated with crossword dictionary 
	 * Assumes valid and non-NULL dictionary object
	 * 
	 * @param dictionary
	 *            - the (loaded from file) dictionary object
	 */
	public void attachDictionary(CrosswordDictionary dictionary);

	/**
	 * Initializes all structures associated with crossword shape 
	 * Assumes valid and non-NULL shape object
	 * 
	 * @param shape
	 *            - the (loaded from file) shape object
	 */
	public void attachShape(CrosswordShape shape);

	/**
	 * Retrieves list of crossword entries associated with this Crossword
	 * The set of filled entries should satisfy both of exercise requirements 
	 * 
	 * @return Collection of filled entries
	 */
	Collection<CrosswordEntry> getCrosswordEntries();	
}
