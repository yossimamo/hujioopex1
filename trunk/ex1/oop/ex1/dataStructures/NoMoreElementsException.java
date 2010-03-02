package oop.ex1.dataStructures;

public class NoMoreElementsException extends java.util.NoSuchElementException {
    
    private static final long serialVersionUID = 1L;
    
    public NoMoreElementsException() {
        super("Tried to iterate after iteration finished");
    }

}
