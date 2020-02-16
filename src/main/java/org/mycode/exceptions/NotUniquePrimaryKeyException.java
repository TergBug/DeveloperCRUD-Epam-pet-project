package org.mycode.exceptions;

public class NotUniquePrimaryKeyException extends RuntimeException {
    public NotUniquePrimaryKeyException() {
        super();
    }
    public NotUniquePrimaryKeyException(String message) {
        super(message);
    }
}
