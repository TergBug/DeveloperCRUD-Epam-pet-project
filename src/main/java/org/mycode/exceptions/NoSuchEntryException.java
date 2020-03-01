package org.mycode.exceptions;

public class NoSuchEntryException extends RuntimeException {
    public NoSuchEntryException() {
        super();
    }

    public NoSuchEntryException(String message) {
        super(message);
    }
}
