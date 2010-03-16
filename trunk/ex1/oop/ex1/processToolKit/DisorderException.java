//###############  
// FILE : DisorderException.java  
// WRITER : Uri Greenberg, urig03, 021986039  
// WRITER : Yossi Mamo, ymamo29, 038073722
// EXERCISE : oop ex1 2010  
// DESCRIPTION:    
// An exception thrown when attempting to run a child process after its parent
//###############

package oop.ex1.processToolKit;

/**
 * An exception thrown when attempting to run a child process after its parent
 * @author Uri Greenberg and Yossi Mamo
 */
public class DisorderException extends Exception {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * Constructs a new DisorderException
     */
    public DisorderException() {
        super("A process was run after its parent");
    }

}