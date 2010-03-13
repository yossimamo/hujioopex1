package oop.ex1.dataStructures;

import java.util.Iterator;
import java.util.Random;

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
	 * the index pointing to the end of the elements in the array.
	 */
	protected int _upperIndex;

	/**
	* Default size of the queue.
	*/
	protected static final int DEFAULT_INITIAL_CAPACITY = 32;
    
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
	
	/**
	 * returns a new instance of a random iterator.
	 * @return  a new instance of a random iterator.
	 */
	public Iterator randomIterator(){
		return new RandomIterator();
	}
    
	/**
	 * returns a new instance of a random iterator(seed).
	 * @param seed - the seed number for the random iterator.
	 * @return a new instance of a random iterator.
	 */
    public Iterator randomIterator(long seed){
    	return new RandomIterator(seed);
    }
    
    /**
     * returns true if the queue has no more elements in it or false otherwise.
     * @return true if the queue has no more elements in it or false otherwise.
     */
    protected boolean isQueueEmpty(){
    	if (_upperIndex == 0){
    		return true;
    	}
    	return false;
    }
    
    /**
     * returns true if the queue has no more room for elements in it or 
     * false otherwise.
     * @return true if the queue has no more room for elements in it or 
     * false otherwise.
     */
    protected boolean isQueueFull(){
    	if (_upperIndex == _queue.length){
    		return true;
    	}
    	return false;
    }
    
    /**
     * enlarges the queue by a factor of 2.  
     */
    protected void enlargeQueue(){
    	ComparableObject[] tempArr= new ComparableObject[ _queue.length * 2];
    	for (int i=0; i<_upperIndex; i++){
    		tempArr[i]= _queue[i];
    	}
    	_queue= tempArr;
    }
	
    
    
    /**
	 * a class that represents an iterator that brings the next element 
	 * from the array in a random method.
	 * @author uri greenberg and yossi mammo
	 *
	 */
	private class RandomIterator implements Iterator {
	    
		/**
		 * a boolean array which holds true if the element in this index 
		 * number was already iterated or false otherwise.
		 */
	    private boolean[] _alreadyIterated;
	    
	    /**
	     * an instance of the java.util.Random class.
	     */
	    private Random _rand;
	    
	    /**
	     * a constructor of a new random iterator.
	     */
	    public RandomIterator() {
	       init();
	    }
	    
	    /**
	     * a constructor of a new random iterator. with a long seed number.
	     * @param seedNum - the seed number for the random class.
	     */
	    public RandomIterator(long seedNum) {
	        _rand.setSeed(seedNum);
	        init();
	    }

	    /**
	     * returns true if the array has elements that hasn't been iterated
	     * or false otherwise.
	     * @return true if the array has elements that hasn't been iterated
	     * or false otherwise.
	     */
		public boolean hasNext() {
			for (int i=0; i<_alreadyIterated.length; i++){
				if (_alreadyIterated[i]==false){
					return true;
				}
			}
			return false;
		}

		/**
		 * returns the next object in the array (in a random selection).
		 * @return the next object in the array (in a random selection).
		 */
		public Object next() throws NoMoreElementsException  {
			if (isQueueEmpty()){
				throw new NoMoreElementsException();
			}
			int position;
			do {
				position=_rand.nextInt(_upperIndex);
			}
			while (_alreadyIterated[position]);
			_alreadyIterated[position]= true;
			return _queue[position];
		}

		/**
		 * 
		 */
		public void remove() {
		}
		
		/**
		 * creates Initializes the boolean array to false.
		 */
		private void init(){
			_alreadyIterated = new boolean[_upperIndex];
	        for (int i=0; i<_alreadyIterated.length; i++){
	        	_alreadyIterated[i]= false;	       
	        }
		}
	    
	}
}


