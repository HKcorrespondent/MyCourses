package org.nju.mycourses.web.interceptor;

import org.nju.mycourses.logic.exception.*;
import org.nju.mycourses.logic.exception.Error;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ServerErrorException;

import javax.persistence.EntityNotFoundException;

@RestControllerAdvice
public class HttpRequestExceptionHandler {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public String NotFoundExceptionHandler(NotFoundException e) {
        return Error.back(e.getMessage()).getMessage();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public String NotFoundExceptionHandler(EntityNotFoundException e) {
        return Error.back(e.getMessage()).getMessage();
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(TokenErrorException.class)
    public String TokenErrorExceptionHandler(TokenErrorException e) {
        return Error.back(e.getMessage()).getMessage();
    }


    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(NoRightException.class)
    public String NoRightExceptionHandler(NoRightException e) {
        return Error.back(e.getMessage()).getMessage();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ExceptionNotValid.class)
    public String NotValidExceptionHandler(ExceptionNotValid e) {
        return Error.back(e.getMessage()).getMessage();
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ServerErrorException.class)
    public String ServerErrorExceptionHandler(ServerErrorException e) {
        return Error.back(e.getMessage()).getMessage();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public String messageErrorHandler(HttpMessageNotReadableException e) {
        return new Error(e.getMessage()).getMessage();
    }
}

