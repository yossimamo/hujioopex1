//###############  
// FILE : CrosswordStrategy.java  
// WRITER : Uri Greenberg, urig03, 021986039  
// WRITER : Yossi Mamo, ymamo29, 038073722
// EXERCISE : oop ex4 2010  
// DESCRIPTION: An interface for a strategy type.
//###############

package oop.ex4.crosswords;

import java.util.Iterator;

/**
 * An interface for a strategy type.
 * @author Uri Greenberg and Yossi Mamo
 *
 */
public interface CrosswordStrategy {
	
	/**
	 * Returns an iterator which iterates according to the strategy.
	 * @return An iterator which iterates according to the strategy.
	 */
	public Iterator<CrosswordEntry> getIterator();
	
	/**
	 * Returns an upper bound quality which is being calculated according 
	 * to the strategy.
	 * @param currentQuality The current quality of the crossword.
	 * @return An upper bound quality which is being calculated according 
	 * to the strategy.
	 */
	public int getUpperBoundQuality(int currentQuality);

}
