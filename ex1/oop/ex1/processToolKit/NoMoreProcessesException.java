package oop.ex1.processToolKit;

public class NoMoreProcessesException extends Exception {
    
    private static final long serialVersionUID = 1L;

    public NoMoreProcessesException() {
        super("Process has no subprocesses");
    }

}
