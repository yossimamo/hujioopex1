package oop.ex1.dataStructures;

import java.util.Iterator;

/**
 * a class which represents a stack of comparable objects.
 * @author uri greenberg and yossi mammo.
 *
 */

public class LIFOComparableQueue extends AbstractComparableQueue {
        
	/**
	 * creates a new empty stack.
	 */
    public LIFOComparableQueue() {
        _queue= new ComparableObject[DEFAULT_INITIAL_CAPACITY];
        _lowerIndex= 0;
        _upperIndex= 0;
        _numOfElements= 0;
    }

    /**
     * pushes a comparable object into the stack. it enlarges the queue if it
     * is full.
     */
    public void push(ComparableObject obj) {
    	if (isQueueFull()){
    		enlargeQueue();
    	}
    	_queue[_upperIndex]= obj;
    	_upperIndex++;
    	_numOfElements++;
    }

    /**
     * returns the next element in the stack.
     * @return the next element in the stack.
     */
    public ComparableObject peek() throws EmptyQueueException {
    	if (isQueueEmpty()){
    		throw new EmptyQueueException;
    	}
    	return _queue[_upperIndex - 1];
    }

    /**
     * removes and return the next element in the stack.
     * @return the next element in the stack.
     */
    public ComparableObject poll() throws EmptyQueueException {
    	if (isQueueEmpty()){
    		throw EmptyQueueException;
    	}
    	_upperIndex--;
    	_numOfElements--;
    	return  _queue[_upperIndex];
    }
    
    /**
     * returns a new instance of a LIFO iterator.
     * @return a new instance of a LIFO iterator.
     */
    public Iterator LIFOIterator() {
        return new LIFOIterator();
    }

    /**
     * a class which iterates the elements by their order in the LIFO queue.
     * @author uri greenberg and yossi mammo.
     *
     */
    private class LIFOIterator implements Iterator {
    	
    	/**
    	 * an index pointing to the element which was last iterated.
    	 */
    	private int _iteratorIndex;
    	
    	/**
    	 * constructs a new iterator.
    	 */
    	public LIFOIterator(){
    		_iteratorIndex= _upperIndex;
    	}
    	

    	/**
    	 * returns true if there is another element to be iterated or false
    	 * otherwise.
    	 * @return true if there is another element to be iterated or false
    	 * otherwise.
    	 */
		public boolean hasNext() {
			if (_iteratorIndex == 0){
				return false;
			}
			return true;
		}

		/**
		 * iterates the next element.
		 * @return the next element that hasn't been returned yet.
		 */
		public Object next() {
			_iteratorIndex =- 1;
			return _queue[_iteratorIndex];
		}


		/**
		 * 
		 */
		public void remove() {

		}
        
    }

	
}
