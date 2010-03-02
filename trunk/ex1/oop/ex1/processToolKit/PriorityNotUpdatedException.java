package oop.ex1.processToolKit;

public class PriorityNotUpdatedException extends Exception {
    
    private static final long serialVersionUID = 1L;

    public PriorityNotUpdatedException() {
        super("Failed to update queue");
    }

}
