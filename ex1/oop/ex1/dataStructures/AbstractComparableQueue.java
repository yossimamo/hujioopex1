//###############  
// FILE : AbstractComparableQueue.java  
// WRITER : Uri Greenberg, urig03, 021986039  
// WRITER : Yossi Mamo, ymamo29, 038073722
// EXERCISE : oop ex1 2010  
// DESCRIPTION:    
// An abstract queue of comparable objects. Supports push, peek, poll, size and
// random iteration.
//###############

package oop.ex1.dataStructures;

import java.util.Iterator;
import java.util.Random;

/**
 * An abstract queue of comparable objects.
 * Supports push, peek, poll, size and random iteration.
 * @author Uri Greenberg and Yossi Mamo
 *
 */
public abstract class AbstractComparableQueue {
    
	/// Elements in the queue
	protected ComparableObject[] _queue;
	
	/// The index pointing to the end of the elements in the array
	protected int _upperIndex;


	/// Default size of the queue
	protected static final int DEFAULT_INITIAL_CAPACITY = 32;
	
	/// Expansion factor of the queue (when reallocating)
	protected static final int QUEUE_EXPANSION_FACTOR = 2;
	
	/// Creates a new empty queue
	public AbstractComparableQueue() {
		_queue = new ComparableObject[DEFAULT_INITIAL_CAPACITY];
        _upperIndex = 0;
	}
	
	/**
	 * @throws EmptyQueueException if queue is empty
	 * @return The top element in the queue (without removing it)
	 */
	public abstract ComparableObject peek() throws EmptyQueueException;
	
	/**
	 * @throws EmptyQueueException if queue is empty
	 * @return The top element in the queue (and remove it)
	 */
	public abstract ComparableObject poll() throws EmptyQueueException;
	
	/**
	 * Push obj into the queue
	 * @param obj Object to push to queue
	 */
	public abstract void push(ComparableObject obj);
	
	/**
	 * returns a new instance of a random iterator
	 * @return a new instance of a random iterator
	 */
	public Iterator randomIterator() {
		return new RandomIterator();
	}
    
	/**
	 * Returns a new instance of a random iterator(seed)
	 * @param seed The seed number for the random iterator
	 * @return A new instance of a random iterator
	 */
    public Iterator randomIterator(long seed) {
    	return new RandomIterator(seed);
    }
    
    /**
     * Returns true if the queue has no more elements in it or false otherwise
     * @return True if the queue has no more elements in it or false otherwise
     */
    protected boolean isQueueEmpty() {
    	if (0 == _upperIndex) {
    		return true;
    	}
    	return false;
    }
    
    /**
     * Returns true if the queue has no more room for elements in it or 
     * false otherwise.
     * @return True if the queue has no more room for elements in it or 
     * false otherwise.
     */
    protected boolean isQueueFull() {
    	if (_queue.length == _upperIndex) {
    		return true;
    	}
    	return false;
    }
    
    /**
     * Reallocates the queue with a larger size  
     */
    protected void enlargeQueue(){
    	ComparableObject[] tempArr =
    	    new ComparableObject[_queue.length*QUEUE_EXPANSION_FACTOR];
    	for (int i=0; i<_upperIndex; i++){
    		tempArr[i] = _queue[i];
    	}
    	_queue = tempArr;
    }
    
    /**
	 * A class that represents an iterator that brings the next element 
	 * from the array in a random method.
	 * @author Uri Greenberg and Yossi Mamo
	 */
	private class RandomIterator implements Iterator {
	    
	    /// A boolean array which holds true if the element in this index 
		/// number was already iterated or false otherwise
	    private boolean[] _alreadyIterated;
	    
	    /// An instance of the java.util.Random class
	    private Random _rand;
	    
	     /// A constructor of a new random iterator
	    public RandomIterator() {
	       init();
	    }
	    
	    /**
	     * A constructor of a new random iterator with a long seed number
	     * @param seedNum the seed number for the random class
	     */
	    public RandomIterator(long seedNum) {
	        init();
	        _rand.setSeed(seedNum);
	    }

	    /**
	     * Returns true if the array has elements that hasn't been iterated
	     * or false otherwise
	     * @return True if the array has elements that hasn't been iterated
	     * or false otherwise
	     */
		public boolean hasNext() {
			for (int i=0; i<_alreadyIterated.length; i++){
				if (false == _alreadyIterated[i]){
					return true;
				}
			}
			return false;
		}

		/**
		 * Returns the next object in the array (in a random selection)
		 * @throws NoMoreElementsException if no more elements left to be 
		 * iterated.
		 * @return The next object in the array (in a random selection)
		 */
		public ComparableObject next() throws NoMoreElementsException  {
			if (hasNext() == false){
				throw new NoMoreElementsException();
			}
			int position = 0;
			do {
				position = _rand.nextInt(_upperIndex);
			}
			while (_alreadyIterated[position]);
			_alreadyIterated[position] = true;
			return _queue[position];
		}

		/**
		 * Not implemented 
		 */
		public void remove() {
		}
		
		/**
		 * Creates and initializes the boolean array to false
		 */
		private void init() {
		    _rand = new Random();
			_alreadyIterated = new boolean[_upperIndex];
	        for (int i=0; i<_alreadyIterated.length; i++){
	        	_alreadyIterated[i] = false;	       
	        }
		}
	}
	
}