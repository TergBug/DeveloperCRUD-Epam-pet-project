package org.mycode.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
public class RepoStorageException extends RuntimeException {
    public RepoStorageException() {
        super();
    }

    public RepoStorageException(String message) {
        super(message);
    }
}
