//###############  
// FILE : PartitionedDataCollection.java  
// WRITER : Uri Greenberg, urig03, 021986039  
// WRITER : Yossi Mamo, ymamo29, 038073722
// EXERCISE : oop ex4 2010  
// DESCRIPTION: A Generic interface of Data stored in a partitioned matter. 
//###############

package oop.ex4.crosswords;

import java.util.Iterator;

/**
 * A Generic interface of Data stored in a partitioned matter.
 * @author Uri Greenberg and Yossi Mamo.
 * @param <E> a collection of data stored in a partitioned matter.
 */
public interface PartitionedDataCollection<E> {
	 
	/**
	 * Returns true if the element is used in the crossword or false
	 * otherwise.
	 * @param elem An element from the partitioned collection.
	 * @return  True if the element is used in the crossword or false
	 * otherwise.
	 */
	public boolean isUsed(E elem);
	
	/**
	 * Returns an iterator of a single segment.
	 * @param partitionNumber The number of the partition we wish to iterate
	 * on.
	 * @return An iterator of a single segment.
	 */
	public Iterator<E> getRawDataIterator(int partitionNumber);
	
	/**
	 * Returns the number of partitions in this collection.
	 * @return The number of partitions in this collection.
	 */
	public int getNumOfPartitions();
}
