package oop.ex1.dataStructures;

import java.util.Iterator;
import java.util.Random;

import com.sun.java.swing.plaf.nimbus.LoweredBorder;

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
	 * the index pointing to the beginning of the elements in the array.
	 */
	protected int _lowerIndex;
	/**
	 * the index pointing to the end of the elements in the array.
	 */
	protected int _upperIndex;
	/**
	 * the number of elements in the array.
	 */
	protected int _numOfElements;

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
    	if (_upperIndex == _lowerIndex){
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
    	if (_numOfElements == _queue.length - 1){
    		return true;
    	}
    	return false;
    }
    
    /**
     * enlarges the queue by a factor of 2. it rearranges the elements in
     * the array so the first one will be in place 0 and the rest after him.  
     */
    protected void enlargeQueue(){
    	ComparableObject[] tempArr= new ComparableObject[ _queue.length * 2];
    	if (_lowerIndex < _upperIndex){
    		for (int i=_lowerIndex; i<_upperIndex; i++){
    			tempArr[i - _lowerIndex]= _queue[i];
    		}
    	}
    	else {
    		int j=0;
    		for (int i=_lowerIndex; i<_queue.length; i++){
    			tempArr[j]= _queue[i];
    			j++;
    		}
    		for (int i=0; i<_upperIndex; i++){
    			tempArr[j]= _queue[i];
    			j++;
    		}
    	}
    	_lowerIndex= 0;
    	_upperIndex= _numOfElements - 1;
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
	        _alreadyIterated = new boolean[_queue.length];
	    }
	    
	    /**
	     * a constructor of a new random iterator. with a long seed number.
	     * @param seedNum - the seed number for the random class.
	     */
	    public RandomIterator(long seedNum) {
	    	_alreadyIterated = new boolean[_queue.length];
	        _rand.setSeed(seedNum);
	    }

	    /**
	     * returns true if the array has elements that hasnt been iterated
	     * or false otherwise.
	     * @return true if the array has elements that hasnt been iterated
	     * or false otherwise.
	     */
		public boolean hasNext() {
			if (_lowerIndex<=_upperIndex){
				for (int i=_lowerIndex; i<_upperIndex; i++){
					if (_alreadyIterated[i]==false){
						return true;
					}
				}
				return false;
			}
			else {
				for (int i=0; i<_upperIndex; i++){
					if (_alreadyIterated[i]==false){
						return true;
					}
				}
				for (int i=_lowerIndex; i<_alreadyIterated.length; i++){
					if (_alreadyIterated[i]==false){
						return true;
					}
				}
				return false;
			}
		}

		/**
		 * returns the next object in the array (in a random selection).
		 * @return the next object in the array (in a random selection).
		 */
		public Object next() {
			int position;
			do {
				position= _lowerIndex + _rand.nextInt(_numOfElements);
				if (position > _alreadyIterated.length - 1){
					position=-_alreadyIterated.length;
				}
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
	    
	}
}


