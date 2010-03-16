//###############  
// FILE : ObjectNotFoundException.java  
// WRITER : Uri Greenberg, urig03, 021986039  
// WRITER : Yossi Mamo, ymamo29, 038073722
// EXERCISE : oop ex1 2010  
// DESCRIPTION:    
// An exception thrown upon a failed attempt to find a certain object in a
// queue.
//###############

package oop.ex1.dataStructures;

/**
 * Thrown upon a failed attempt to find a certain object in a queue.
 * @author Uri Greenberg and Yossi Mamo
 */
public class ObjectNotFoundException extends Exception {
    
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new ObjectNotFoundException
     */
    public ObjectNotFoundException() {
        super("Object not found in queue");
    }

}
