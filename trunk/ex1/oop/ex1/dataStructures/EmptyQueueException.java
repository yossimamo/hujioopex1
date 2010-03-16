//###############  
// FILE : EmptyQueueException.java  
// WRITER : Uri Greenberg, urig03, 021986039  
// WRITER : Yossi Mamo, ymamo29, 038073722
// EXERCISE : oop ex1 2010  
// DESCRIPTION:    
// An exception thrown upon attempts to access an empty queue.
//###############

package oop.ex1.dataStructures;

/**
 * An exception thrown upon attempts to access an empty queue.
 * @author Uri Greenberg and Yossi Mamo
 */
public class EmptyQueueException extends Exception {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * Constructs a new EmptyQueueException
     */
    public EmptyQueueException() {
        super("Tried to access an empty queue");
    }

}
