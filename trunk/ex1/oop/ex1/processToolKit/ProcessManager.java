//###############  
// FILE : ProcessManager.java  
// WRITER : Uri Greenberg, urig03, 021986039  
// WRITER : Yossi Mamo, ymamo29, 038073722
// EXERCISE : oop ex1 2010  
// DESCRIPTION:    
// A process manager, responsible for managing and running all the processes.
// Holds a priority queue of all the processes in the system.
//###############

package oop.ex1.processToolKit;

import java.util.Date;
import java.util.Iterator;

import oop.ex1.dataStructures.ArrayPriorityQueue;
import oop.ex1.dataStructures.EmptyQueueException;
import oop.ex1.dataStructures.ObjectNotFoundException;

/**
 * A process manager, responsible for managing and running all the processes.
 * Holds a priority queue of all the processes in the system.
 * @author Uri Greenberg and Yossi Mamo
 */
public class ProcessManager {
    
    /// Priority queue of processes
    private ArrayPriorityQueue _processQueue;
    
    /**
     * Constructs a new ProcessManager
     */
    public ProcessManager() {
        _processQueue = new ArrayPriorityQueue();
    }

    /**
     * Adds a process and all its subprocesses to the process queue.
     * Assumes the given process does not have a parent and does not already
     * exist in the tree.
     * @param process The process to add
     */
    public void addProcessTree(Process process) {
        _processQueue.push(process);
        Iterator subProcs = process.iterateSubProcesses();
        while (subProcs.hasNext()) {
            addProcessTree((Process)subProcs.next());
        }
    }
    
    /**
     * Updates the priority of the given process to the given priority. This
     * method will also update the priorities of the process' parents if they
     * are smaller than the given priority, and the priorities of the process'
     * subprocesses if they are bigger than the given priority (all in order
     * to maintain the heap property of the priority queue).
     * @param process The process to update
     * @param priority The requested priority
     * @throws ObjectNotFoundException If the given process was not found in
     * the queue.
     */
    public void updatePriority(Process process, int priority) 
        throws ObjectNotFoundException {
        int oldPriority = process.getPriority();
        if (oldPriority != priority) {
            process.setPriority(priority);
            _processQueue.updatePriority(process);
            // If the new priority is bigger than any of the children's
            // priorities, update their priority as well.
            Iterator subProcs = process.iterateSubProcesses();
            while (subProcs.hasNext()) {
                Process subProc = (Process)subProcs.next();
                if (subProc.getPriority() < priority) {
                    updatePriority(subProc, priority);
                }
            }
            // If the new priority is smaller than the parent's priority,
            // update the parent's priority as well.
            Process parent = process.getParent();
            if (null != parent) {
                if (parent.getPriority() > priority) {
                    updatePriority(parent, priority);
                }
            }
        }
    }
    
    /**
     * Runs all processes by their order.
     * @throws DisorderException If a child is set to run after its parent
     * has already terminated.
     */
    public void runAllProcesses() throws DisorderException {
        try {
            Process process = (Process)_processQueue.poll();
            while (null != process) {
                Date now = new Date();
                Process parent = process.getParent();
                if ((null != parent) && (null != parent.getTerminationTime())) {
                    throw new DisorderException();
                }
                process.run();
                process.setTerminationTime(new Date());
                // Next!
                process = (Process)_processQueue.poll();
            }
        }
        catch (EmptyQueueException e) {
            return;
        }
    }
    
}
