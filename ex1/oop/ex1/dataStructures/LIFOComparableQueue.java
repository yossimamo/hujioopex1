package oop.ex1.dataStructures;

import java.util.Iterator;

/**
 * A class which represents a stack of comparable objects.
 * @author Uri Greenberg and Yossi Mamo
 *
 */

public class LIFOComparableQueue extends AbstractComparableQueue {
    
    /// Index of the bottom of the stack
    private static final int STACK_BOTTOM_INDEX = 0;
        
	/**
	 * Creates a new empty stack
	 */
    public LIFOComparableQueue() {
        super();
    }

    /**
     * Pushes a comparable object into the stack, and enlarges the queue if it
     * becomes full.
     */
    public void push(ComparableObject obj) {
    	if (isQueueFull()) {
    		enlargeQueue();
    	}
    	_queue[_upperIndex] = obj;
    	_upperIndex++;
    }

    /**
	 * @throws EmptyQueueException If queue is empty
	 * @return The top element in the queue (without removing it)
	 */
    public ComparableObject peek() throws EmptyQueueException {
    	if (isQueueEmpty()) {
    		throw new EmptyQueueException();
    	}
    	return _queue[_upperIndex - 1];
    }

    /**
	 * @throws EmptyQueueException If queue is empty.
	 * @return The top element in the queue (and remove it).
	 */
    public ComparableObject poll() throws EmptyQueueException {
    	if (isQueueEmpty()) {
    		throw new EmptyQueueException();
    	}
    	_upperIndex--;
    	return _queue[_upperIndex];
    }
    
    /**
     * Returns a new instance of a LIFO iterator
     * @return A new instance of a LIFO iterator
     */
    public Iterator LIFOIterator() {
        return new LIFOIterator();
    }

    /**
     * a class which iterates the elements by their order in the LIFO queue.
     * @author Uri Greenberg and Yossi Mamo
     *
     */
    private class LIFOIterator implements Iterator {
    	
    	/**
    	 * 
    	 * An index pointing to the element which was last iterated
    	 */
    	private int _iteratorIndex;
    	
    	/**
    	 * Constructs a new iterator
    	 */
    	public LIFOIterator() {
    		_iteratorIndex = _upperIndex;
    	}

    	/**
    	 * Returns true if there is another element to be iterated or false
    	 * otherwise
    	 * @return True if there is another element to be iterated or false
    	 * otherwise
    	 */
		public boolean hasNext() {
			if (STACK_BOTTOM_INDEX == _iteratorIndex){
				return false;
			}
			return true;
		}

		/**
		 * iterates the next element.
		 * @throws NoMoreElementsException if all the elements have been
		 * iterated.
		 * @return the next element that hasn't been returned yet.
		 */
		public Object next() throws NoMoreElementsException {
			if (hasNext() == false){
				throw new NoMoreElementsException();
			}
			_iteratorIndex--;
			return _queue[_iteratorIndex];
		}

		/**
		 * Not implemented 
		 */
		public void remove() {
		}
        
    }
	
}
