package org.mycode.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class NotUniquePrimaryKeyException extends RuntimeException {
    public NotUniquePrimaryKeyException() {
        super();
    }
    public NotUniquePrimaryKeyException(String message) {
        super(message);
    }
}
