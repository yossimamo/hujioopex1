//###############  
// FILE : Process.java  
// WRITER : Uri Greenberg, urig03, 021986039  
// WRITER : Yossi Mamo, ymamo29, 038073722
// EXERCISE : oop ex1 2010  
// DESCRIPTION:    
// Represents a process with a unique name, priority, termination time, a
// single parent and a stack of subprocesses associated with it.
//###############

package oop.ex1.processToolKit;

import java.util.Date;
import java.util.Iterator;

import oop.ex1.dataStructures.ComparableObject;
import oop.ex1.dataStructures.EmptyQueueException;
import oop.ex1.dataStructures.LIFOComparableQueue;

/**
 * Represents a process with a unique name, priority, termination time, a
 * single parent and a stack of subprocesses associated with it.
 * @author Uri Greenberg and Yossi Mamo
 */
public class Process implements ComparableObject {
    
    /// Name of the process
    private String _name;
    
    /// The process' parent
    private Process _parent;
    
    /// Stack of subprocesses of the current process
    private LIFOComparableQueue _subProcesses;
    
    /// Termination time of the process
    private Date _terminationTime;
    
    /// Priority of the process
    private int _priority;
    
    /// The default given priority
    private static final int DEFAULT_PRIORITY = 0;
    
    /// Arbitrary positive value (for compare() method)
    private static final int ARBITRARY_POSITIVE_VALUE = 1;
    
    /**
     * Constructs a new process with the given name and parent (default
     * priority will be set).
     * @param name The process' name
     * @param parent The process' parent
     */
    public Process(String name, Process parent) {
        init(name, parent, DEFAULT_PRIORITY);
    }
    
    /**
     * Constructs a new process with the given name, parent and priority.
     * @param name The process' name
     * @param parent The process' parent
     * @param priority The process' priority (can be negative)
     */
    public Process(String name, Process parent, int priority) {
        init(name, parent, priority);
    }
    
    /**
     * Helper method which initializes a newly constructed process with the
     * given name, parent and priority.
     * @param name The process' name
     * @param parent The process' parent
     * @param priority The process' priority
     */
    private void init(String name, Process parent, int priority) {
        _name = name;
        _parent = parent;
        _terminationTime = null;
        _subProcesses = new LIFOComparableQueue();
        if (null != parent) {
            if (priority < parent.getPriority()) {
                _priority = parent.getPriority();
            } else {
                _priority = priority;
            }
            parent._subProcesses.push(this);            
        }
        else {
            _priority = priority;
        }
    }
    
    /**
     * Sets the process' priority
     * @param priority The new priority
     */
    public void setPriority(int priority)  {
        _priority = priority;
    }
    
    /**
     * Returns the process' priority 
     * @return the process' priority
     */
    public int getPriority() {
        return _priority;
    }
    
    /**
     * Runs the process 
     */
    public void run() {
        System.out.println("Running " + toString());
    }
    
    /**
     * Returns an iterator on the subprocesses stack
     * @return A LIFOIterator on the subprocesses stack
     */
    public Iterator iterateSubProcesses() {
        return _subProcesses.LIFOIterator();
    }
    
    /**
     * Returns true if this process is equal to the other given process.
     * Equality is determined by checking if the process' names are equal.
     * Null values are supported
     * @param other The other process to compare to
     */
    public boolean equals(ComparableObject other) {
        if (null == other) {
            // Obviously not equal
            return false;
        }
        if (other instanceof Process) {
            if (((Process)other)._name.equals(_name)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Compares two processes to determine which one is bigger. The order is
     * determined by comparing priorities, then the level, and then by names
     * (lexicographically). 
     * Null values are supported and are arbitrarily defined to be smaller than
     * anything else.
     * @param other The other process to compare to
     * @throws ClassCastException if the given object is not a Process
     * @return A positive value if this process is bigger than the other, a
     * negative value if the other one is bigger, or 0 if they are equal.
     */
    public int compare(ComparableObject other) throws ClassCastException {
        if (null == other) {
            // A null value is arbitrarily defined to be the smallest
            // Return arbitrary positive value
            return ARBITRARY_POSITIVE_VALUE;
        }
        if (!(other instanceof Process)) {
            throw new ClassCastException();
        }
        Process otherProc = (Process)other;
        if (0 == _priority - otherProc._priority) {
            if (0 == getLevel() - otherProc.getLevel()) {
                return _name.compareTo(otherProc._name);
            } else {
                return (getLevel() - otherProc.getLevel());
            }
        } else {
            return (_priority - otherProc._priority);
        }
    }
    
    /**
     * Polls the sub-processes stack (removes the subprocess from it)
     * @return The next subprocess in the stack (if exists)
     * @throws NoMoreProcessesException If there are no more subprocesses
     * in the stack
     */
    public Process pollSubProcess() throws NoMoreProcessesException {
        try {
            return (Process)_subProcesses.poll();
        } catch (EmptyQueueException e) {
            throw new NoMoreProcessesException();
        }
    }
    
    /**
     * Returns a string representation of the process and all its subprocesses.
     * The returned string has the following structure:  
     * "name: {subP1.toString(),subP2.toString(),...,subPn.toString()}" or
     * "name" (if process has no subprocesses).
     * @return A string representation of the process and its sons. 
     */
    public String toString() {
        StringBuilder sb = new StringBuilder(_name);
        Iterator it = _subProcesses.LIFOIterator();
        if (!it.hasNext()) {
            return sb.toString();
        }
        sb.append(": {");
        while (it.hasNext()) {
            sb.append(((Process)it.next()).toString());
            if (it.hasNext()) {
                sb.append(',');
            }
        }
        sb.append('}');
        return sb.toString();
    }
    
    /**
     * Returns the process' level, that is, the amount of parents that this
     * process has.
     * @return The process' level
     */
    private int getLevel() {
        int level = 0;
        Process process = this;
        while (process._parent != null) {
            process = process._parent;
            level++;
        }
        return level;
    }
    
    /**
     * Returns the process' parent
     * @return The process' parent (may be null)
     */
    public Process getParent() {
        return _parent;
    }
    
    /**
     * Sets the termination time of the process
     * @param time The desired termination time of the process
     */
    public void setTerminationTime(Date time) {
        _terminationTime = time;
    }
    
    /**
     * Returns the termination time of the process
     * @return The termination time of the process
     */
    public Date getTerminationTime() {
        return _terminationTime;
    }

}
