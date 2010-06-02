//###############  
// FILE : CrosswordStrategyIterator.java  
// WRITER : Uri Greenberg, urig03, 021986039  
// WRITER : Yossi Mamo, ymamo29, 038073722
// EXERCISE : oop ex4 2010  
// DESCRIPTION: An interface for an iterator with a strategy type.
//###############

package oop.ex4.crosswords;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * An interface for an iterator with a strategy type.
 * @author Uri Greenberg and Yossi Mamo.
 *
 */
public abstract class CrosswordStrategyIterator implements Iterator<CrosswordEntry> {

	protected CrosswordEntry _next;
	
	/**
	 * Returns true iff the iterator has another element.
	 * @return true iff the iterator has another element.
	 */
	public boolean hasNext() {
		if (null != _next) {
			return true;
		} else {
			try {
				_next = next();
				return true;
			} catch (NoSuchElementException e) {
				return false;
			}
		}
	}
	
	/**
	 * Returns the next element in the iteration.
	 * @return The next element in the iteration.
	 */
	public CrosswordEntry next() {
		if (null != _next) {
			CrosswordEntry ret = _next;
			_next = null;
			return ret;
		} else {
			return findNextMove();
		}
	}
	
	/**
	 * not implemented.
	 */
	public void remove() {
		throw new UnsupportedOperationException();
	}
	
	abstract protected CrosswordEntry findNextMove();

}
