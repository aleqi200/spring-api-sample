package de.groupon.sample.exception;

public class AlreadyExistsException extends Exception{

    public AlreadyExistsException() {
        super();
    }

    public AlreadyExistsException(String s) {
        super(s);
    }

    public AlreadyExistsException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public AlreadyExistsException(Throwable throwable) {
        super(throwable);
    }
}
