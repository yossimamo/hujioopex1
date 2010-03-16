//###############  
// FILE : NoMoreProcessesException.java  
// WRITER : Uri Greenberg, urig03, 021986039  
// WRITER : Yossi Mamo, ymamo29, 038073722
// EXERCISE : oop ex1 2010  
// DESCRIPTION:    
// An exception thrown when a certain process has no more subprocesses to
// iterate.
//###############

package oop.ex1.processToolKit;

/**
 * An exception thrown when a certain process has no more subprocesses to
 * iterate.
 * @author Uri Greenberg and Yossi Mamo
 */
public class NoMoreProcessesException extends Exception {
    
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new NoMoreProcessesException
     */
    public NoMoreProcessesException() {
        super("Process has no subprocesses");
    }

}
