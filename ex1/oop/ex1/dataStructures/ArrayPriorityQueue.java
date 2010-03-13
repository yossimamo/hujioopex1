package oop.ex1.dataStructures;

/**
 * this class represents an implementation of a priority queue.
 * @author uri greenberg and yossi mammo.
 *
 */
public class ArrayPriorityQueue extends AbstractPriorityQueue {

	/**
	 * pushes a new element into the queue and relocates it in the right place
	 * in the array.
	 * @param obj the object to be pushed into the queue.
	 */
    public void push(ComparableObject obj) {
    	if (isQueueFull()){
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
     * returns the element with the highest priority in the queue.
     * @throws EmptyQueueException
     * @return the element with the highest priority in the queue.
     */
    public ComparableObject peek() throws EmptyQueueException {
    	if (isQueueEmpty()){
    		throw new EmptyQueueException();
    	}
    	return _queue[0];
    }
    
    

    /**
     * returns and removes the element with the highest priority in the queue.
     * @throws EmptyQueueException
     * @return the element with the highest priority in the queue.
     */
    public ComparableObject poll() throws EmptyQueueException {
    	if (isQueueEmpty()){
    		throw new EmptyQueueException();
    	}
    	ComparableObject max= _queue[0];
    	_queue[0] = _queue[_upperIndex - 1];
    	_upperIndex--;
    	maxHeapify(0);
    	return max;
    }
    
    /**
     * finds object in queue and moves it up or down as requiered by its
     * priority.
     * @throws ObjectNotFoundException
     * @param obj the object we wish to search and move.
     * 
     */
    public void updatePriority(ComparableObject obj)
    		throws ObjectNotFoundException {
    	int i= findIndex(obj);
    	maxHeapify(i);
    	while (i>0){
    		if(_queue[i].compare(_queue[(i-1)/2]) > 0){
    			swap(i,(i-1)/2);
    			i=(i-1)/2;
    		}
    		else {
    			break;
    		}
    	}
    }


    /**
     * receives an index number and reposition the object in this index, in 
     * its place according to the priority as long as its lower thaan the
     * index position in the tree.
     * @param index the index number of the element in the queue to be
     * repositioned.
     */
    private void maxHeapify(int index){
    	int largest;
    	int leftSonIndex= index * 2 + 1;
    	int rightSonIndex = index*2 + 2;
    	if (leftSonIndex < _upperIndex){
    		if (rightSonIndex >= _upperIndex){
    			rightSonIndex = _upperIndex - 1;
    		}
    		if (_queue[leftSonIndex].compare(_queue[index]) > 0){
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
     * receives two indexes in the queue and swaps the elements in them.
     * @param i first index.
     * @param j second index.
     */
    private void swap(int i, int j){
    	ComparableObject temp= _queue[i];
    	_queue[i]= _queue[j];
    	_queue[j]= temp;
    }
    
    
    /**
     * returns the index number of the object received or throws an exception
     * if it is not in the queue.
     * @param obj the object we wish to find in the queue.
     * @return the index number of the object received
     * @throws ObjectNotFoundException
     */
    private int findIndex(ComparableObject obj) throws ObjectNotFoundException{
    	for (int i=1; i< _upperIndex; i++){
    		if (obj == _queue[i]){
    			return i;
    		}
    	}
    	throw new ObjectNotFoundException();
    }
}
