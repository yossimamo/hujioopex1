package oop.ex1.processToolKit;

import java.util.Date;
import java.util.Iterator;

import oop.ex1.dataStructures.ComparableObject;
import oop.ex1.dataStructures.EmptyQueueException;
import oop.ex1.dataStructures.LIFOComparableQueue;

/**
 * 
 * @author Yossi and Uri
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
     * @param priority The process' priority
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
            if (DEFAULT_PRIORITY < parent.getPriority()) {
                _priority = parent.getPriority();
            } else {
                _priority = priority;
            }
            parent._subProcesses.push(this);            
        }
    }
    
    /**
     * 
     * @param priority
     */
    public void setPriority(int priority)  {
        _priority = priority;
    }
    
    /**
     * 
     * @return
     */
    public int getPriority() {
        return _priority;
    }
    
    /**
     * 
     */
    public void run() {
        System.out.println("Running " + toString());
    }
    
    /**
     * 
     * @return
     */
    public Iterator iterateSubProcesses() {
        return _subProcesses.LIFOIterator();
    }
    
    /**
     * 
     */
    public boolean equals(ComparableObject other) {
        if (other instanceof Process) {
            if (((Process)other)._name.equals(_name)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * 
     */
    public int compare(ComparableObject other) {
        if (!(other instanceof Process)) {
            throw new ClassCastException();
        }
        Process otherProc = (Process)other;
        if (0 == _priority - otherProc._priority) {
            if (0 == getLevel() - otherProc.getLevel()) {
                return _name.compareTo(otherProc._name);
            } else {
                return getLevel() - otherProc.getLevel();
            }
        } else {
            return (_priority - otherProc._priority);
        }
    }
    
    /**
     * 
     * @return
     * @throws NoMoreProcessesException
     */
    public Process pollSubProcess() throws NoMoreProcessesException {
        try {
            return (Process)_subProcesses.poll();
        } catch (EmptyQueueException e) {
            throw new NoMoreProcessesException();
        }
    }
    
    /**
     * 
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
     * 
     * @return
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
     * 
     * @return
     */
    public Process getParent() {
        return _parent;
    }
    
    /**
     * 
     * @param time
     */
    public void setTerminationTime(Date time) {
        _terminationTime = time;
    }
    
    /**
     * 
     * @return
     */
    public Date getTerminationTime() {
        return _terminationTime;
    }

}
