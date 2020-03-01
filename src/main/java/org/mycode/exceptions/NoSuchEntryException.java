package org.mycode.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class NoSuchEntryException extends RuntimeException {
    public NoSuchEntryException() {
        super();
    }

    public NoSuchEntryException(String message) {
        super(message);
    }
}
