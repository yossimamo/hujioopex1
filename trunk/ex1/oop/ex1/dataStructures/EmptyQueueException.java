package oop.ex1.dataStructures;

public class EmptyQueueException extends Exception {
    
    private static final long serialVersionUID = 1L;
    
    public EmptyQueueException() {
        super("Tried to access an empty queue");
    }

}
