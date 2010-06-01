//###############  
// FILE : ContinuousIterator.java  
// WRITER : Uri Greenberg, urig03, 021986039  
// WRITER : Yossi Mamo, ymamo29, 038073722
// EXERCISE : oop ex4 2010  
// DESCRIPTION: A Generic interface of an Iterator which iterates on a number
//  of non-continuous segments.
//###############

package oop.ex4.crosswords;

import java.util.Iterator;

/**
 * A Generic interface of an Iterator which iterates on a number of
 * non-continuous segments.
 * @author Uri Greenberg and Yossi Mamo.
 * @param <D>
 * @param <E>
 */
public abstract class ContinuousIterator<D extends
					PartitionedDataCollection<E>, E> implements Iterator<E> {
	
	/**
	 * Returns true iff the iterator has another element.
	 * @return true iff the iterator has another element.
	 */
	abstract public boolean hasNext();
	
	/**
	 * Returns the next element in the iteration.
	 * @return The next element in the iteration.
	 */
	abstract public E next();
	
	/**
	 * not implemented.
	 */
	abstract public void remove();

}
