//###############  
// FILE : NoMoreElementsException.java  
// WRITER : Uri Greenberg, urig03, 021986039  
// WRITER : Yossi Mamo, ymamo29, 038073722
// EXERCISE : oop ex1 2010  
// DESCRIPTION:    
// An exception thrown upon attempts to iterate a collection after reaching the
// end of the iteration.
//###############

package oop.ex1.dataStructures;

/**
 * An exception thrown upon attempts to iterate a collection after reaching the
 * end of the iteration.
 * @author Uri Greenberg and Yossi Mamo
 *
 */
public class NoMoreElementsException extends java.util.NoSuchElementException {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * Constructs a new NoMoreElementsException
     */
    public NoMoreElementsException() {
        super("Tried to iterate after iteration finished");
    }

}
