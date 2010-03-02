package oop.ex1.dataStructures;

public class ObjectNotFoundException extends Exception {
    
    private static final long serialVersionUID = 1L;

    public ObjectNotFoundException() {
        super("Object not found in queue");
    }

}
