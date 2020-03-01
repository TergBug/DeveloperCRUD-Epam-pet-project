package org.mycode.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class NotUniqueEntryException extends RuntimeException {
    public NotUniqueEntryException() {
        super();
    }

    public NotUniqueEntryException(String message) {
        super(message);
    }
}
