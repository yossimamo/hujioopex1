package oop.ex1.processToolKit;

public class DisorderException extends Exception {
    
    private static final long serialVersionUID = 1L;

    public DisorderException() {
        super("A process was run after its parent");
    }

}
