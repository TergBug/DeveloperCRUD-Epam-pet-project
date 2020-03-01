package org.mycode.exceptions;

public class NotUniqueEntryException extends RuntimeException {
    public NotUniqueEntryException() {
        super();
    }

    public NotUniqueEntryException(String message) {
        super(message);
    }
}
