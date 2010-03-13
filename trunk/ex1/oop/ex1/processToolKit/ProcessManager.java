package oop.ex1.processToolKit;

import java.util.Date;
import java.util.Iterator;

import oop.ex1.dataStructures.ArrayPriorityQueue;
import oop.ex1.dataStructures.EmptyQueueException;
import oop.ex1.dataStructures.ObjectNotFoundException;

/**
 * 
 * @author Yossi and Uri
 */
public class ProcessManager {
    
    ///
    private ArrayPriorityQueue _processQueue;
    
    /**
     * 
     */
    public ProcessManager() {
        _processQueue = new ArrayPriorityQueue();
    }

    /**
     * Assumes this method is invoked on a process without a parent that
     * does not already exist in the tree.
     */
    public void addProcessTree(Process process) {
        _processQueue.push(process);
        Iterator subProcs = process.iterateSubProcesses();
        while (subProcs.hasNext()) {
            addProcessTree((Process)subProcs.next());
        }
    }
    
    /**
     * 
     * @param process
     * @param priority
     * @throws ObjectNotFoundException
     */
    public void updatePriority(Process process, int priority) 
        throws ObjectNotFoundException {
        int oldPriority = process.getPriority();
        if (oldPriority != priority) {
            process.setPriority(priority);
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
            if (parent.getPriority() > priority) {
                updatePriority(parent, priority);
            }
        }
    }
    
    /**
     * 
     * @throws DisorderException
     */
    public void runAllProcesses() throws DisorderException {
        try {
            Process process = (Process)_processQueue.poll();
            Date now = new Date();
            if (now.after(process.getParent().getTerminationTime())) {
                throw new DisorderException();
            }
            process.run();
            process.setTerminationTime(new Date());
        }
        catch (EmptyQueueException e) {
            return;
        }
    }
    
}
