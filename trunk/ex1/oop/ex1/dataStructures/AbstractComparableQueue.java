package oop.ex1.dataStructures;

/**
 * An abstract queue of comparable objects.
 * Supports push,peek,poll, size and random iteration.
 * @author OOP
 *
 */
public abstract class AbstractComparableQueue {
	/**
	* Elements in the queue.
	*/
	protected ComparableObject[] _queue;

	/**
	* Default size of the queue.
	*/
	protected static final int DEFAULT_INITIAL_CAPACITY = 32;
    
	/**
         * The number of elements in the queue.
         */
        protected int _size = 0;

	/**
	 * @throws EmptyQueueException if queue is empty.
	 * @return The top element in the queue (without removing it).
	 */
	public abstract ComparableObject peek() throws EmptyQueueException;
	
	/**
	 * @throws EmptyQueueException if queue is empty.
	 * @return The top element in the queue (and remove it).
	 */
	public abstract ComparableObject poll() throws EmptyQueueException;
	
	/**
	 * Push obj into the queue.
	 * @param obj Object to push to queue.
	 */
	public abstract void push(ComparableObject obj);
}


