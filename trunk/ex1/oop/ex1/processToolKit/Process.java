package oop.ex1.processToolKit;

import java.util.Date;
import java.util.Iterator;

import oop.ex1.dataStructures.ComparableObject;
import oop.ex1.dataStructures.LIFOComparableQueue;

public class Process implements ComparableObject {
    
    private String _name;
    
    private Process _parent;
    
    private LIFOComparableQueue _subProcesses;
    
    private Date _terminationTime;
    
    private int _priority;
    
    public Process(String name, Process parent) {
        
    }
    
    public Process(String name, Process parent, int priority) {
        
    }
    
    public void setPriority(int priority)  {
        
    }
    
    public void run() {
        
    }
    
    public Iterator iterateSubProcesses() {
        
    }
    
    public boolean equals(ComparableObject other) {
        
    }
    
    public int compare(ComparableObject other) {
        
    }
    
    public Process pollSubProcess() throws NoMoreProcessesException {
        
    }
    
    public String toString() {
        
    }

    public int compare(ComparableObject other) {

    }

    public boolean equals(ComparableObject other) {

    }

}
