package oop.ex1.dataStructures;

/**
 * This class represents an implementation of a priority queue
 * @author Uri Greenberg and Yossi Mamo
 */
public class ArrayPriorityQueue extends AbstractPriorityQueue {
    
    /// Index of the first item in the queue
    private static final int FIRST_ITEM_INDEX = 0;
    
	/**
	 * Constructs a new priority queue 
	 */
    public ArrayPriorityQueue(){
		super();
	}
	
	/**
	 * Pushes a new element into the queue and relocates it in the right place
	 * in the array.
	 * @param obj the object to be pushed into the queue
	 */
    public void push(ComparableObject obj) {
    	if (isQueueFull()) {
    		enlargeQueue();
    	}
    	_queue[_upperIndex]= obj;
    	_upperIndex++;
    	try {
			updatePriority(obj);
		} 
    	catch (ObjectNotFoundException e) {
		}
    }

    /**
     * Returns the element with the highest priority in the queue
     * @throws EmptyQueueException if the queue is empty
     * @return the element with the highest priority in the queue
     */
    public ComparableObject peek() throws EmptyQueueException {
    	if (isQueueEmpty()) {
    		throw new EmptyQueueException();
    	}
    	return _queue[FIRST_ITEM_INDEX];
    }

    /**
     * returns and removes the element with the highest priority in the queue.
     * @throws EmptyQueueException if the queue is empty.
     * @return the element with the highest priority in the queue.
     */
    public ComparableObject poll() throws EmptyQueueException {
    	if (isQueueEmpty()) {
    		throw new EmptyQueueException();
    	}
    	ComparableObject max = _queue[FIRST_ITEM_INDEX];
    	_queue[FIRST_ITEM_INDEX] = _queue[_upperIndex-1];
    	_upperIndex--;
    	maxHeapify(FIRST_ITEM_INDEX);
    	return max;
    }
    
    /**
     * Finds object in queue and moves it up or down as required by its
     * priority
     * @throws ObjectNotFoundException if the object does not exist in the
     * queue 
     * @param obj The object we wish to search and move
     * 
     */
    public void updatePriority(ComparableObject obj)
    		throws ObjectNotFoundException {
    	int i = findIndex(obj);
    	maxHeapify(i);
    	// Moving the element upwards to its rightful position
    	// (swaps every time the parent is smaller than the element itself)
    	while (i > 0) {
    		if(_queue[i].compare(_queue[(i-1)/2]) > 0) {
    			swap(i,(i-1)/2);
    			i = (i-1)/2;
    		}
    		else {
    			break;
    		}
    	}
    }

    /**
     * Receives an index number and repositions the object in this index, in 
     * its place according to the priority as long as its lower than the
     * index position in the tree
     * @param index The index number of the element in the queue to be
     * repositioned
     */
    private void maxHeapify(int index) {
    	int largest;
    	int leftSonIndex = index * 2 + 1;
    	int rightSonIndex = index * 2 + 2;
    	if (leftSonIndex < _upperIndex) {
    		if (rightSonIndex >= _upperIndex) {
    			rightSonIndex = _upperIndex - 1;
    		}
    		if (_queue[leftSonIndex].compare(_queue[index]) > 0) {
    			largest = leftSonIndex;
    		}
    		else {
    			largest = index;
    		}
    		if (_queue[rightSonIndex].compare(_queue[largest]) > 0){
    			largest = rightSonIndex;
    		}
    		if (largest != index){
    			swap(largest, index);
    			maxHeapify(largest);
    		}
    	}
    }
    
    /**
     * Receives two indexes in the queue and swaps the elements in them
     * @param i First index
     * @param j Second index
     */
    private void swap(int i, int j){
    	ComparableObject temp = _queue[i];
    	_queue[i] = _queue[j];
    	_queue[j] = temp;
    }
    
    /**
     * Returns the index number of the object received or throws an exception
     * if it is not in the queue
     * @param obj The object we wish to find in the queue
     * @return The index number of the object received
     * @throws ObjectNotFoundException If the object does not exist in the 
     * queue
     */
    private int findIndex(ComparableObject obj)
        throws ObjectNotFoundException {
    	for (int i=0; i< _upperIndex; i++){
    		if (obj == _queue[i]){
    			return i;
    		}
    	}
    	throw new ObjectNotFoundException();
    }
    
}
