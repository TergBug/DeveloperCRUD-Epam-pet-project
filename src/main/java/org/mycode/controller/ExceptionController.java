package org.mycode.controller;

import org.mycode.exceptions.NoSuchEntryException;
import org.mycode.exceptions.NotUniqueEntryException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import java.sql.SQLException;

@RestControllerAdvice
public class ExceptionController {
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchEntryException.class)
    public ModelAndView handleNoSuchEntryException() {
        return new ModelAndView("error404");
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NotUniqueEntryException.class)
    public ModelAndView handleNotUniqueEntryException() {
        return new ModelAndView("error400");
    }

    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(SQLException.class)
    public ModelAndView handleRepoStorageException() {
        return new ModelAndView("error500");
    }
}
