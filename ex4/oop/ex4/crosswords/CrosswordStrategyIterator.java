//###############  
// FILE : CrosswordStrategyIterator.java  
// WRITER : Uri Greenberg, urig03, 021986039  
// WRITER : Yossi Mamo, ymamo29, 038073722
// EXERCISE : oop ex4 2010  
// DESCRIPTION: An interface for an iterator with a strategy type.
//###############

package oop.ex4.crosswords;

import java.util.Iterator;

/**
 * An interface for an iterator with a strategy type.
 * @author Uri Greenberg and Yossi Mamo.
 *
 */
public abstract class CrosswordStrategyIterator implements Iterator<CrosswordEntry> {

	/**
	 * Returns true iff the iterator has another element.
	 * @return true iff the iterator has another element.
	 */
	abstract public boolean hasNext();
	
	/**
	 * Returns the next element in the iteration.
	 * @return The next element in the iteration.
	 */
	abstract public CrosswordEntry next();
	
	/**
	 * not implemented.
	 */
	abstract public void remove();

}
