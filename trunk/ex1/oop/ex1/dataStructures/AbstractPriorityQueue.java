//###############  
// FILE : AbstractPriorityQueue.java  
// WRITER : Uri Greenberg, urig03, 021986039  
// WRITER : Yossi Mamo, ymamo29, 038073722
// EXERCISE : oop ex1 2010  
// DESCRIPTION:    
// An abstract priority queue, which in addition to the queue methods,
// supports updatePriority.
//###############

package oop.ex1.dataStructures;

/**
 * An abstract priority queue, which in addition to the queue methods,
 * supports updatePriority.
 * @author OOP
 *
 */
public abstract class AbstractPriorityQueue extends AbstractComparableQueue {
	/**
	 * Finds obj in queue, and move it up/down as required.
	 * @param obj Object for which the priority was updated.
	 * @throws ObjectNotFoundException In case object is not found in queue.
	 */
	public abstract void updatePriority(ComparableObject obj)
	    throws ObjectNotFoundException;
}
