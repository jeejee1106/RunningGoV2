package com.runninggo.toy.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    MessageSourceAccessor messageSource;

    public GlobalExceptionHandler(MessageSourceAccessor messageSource) {
        this.messageSource = messageSource;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResult illegalExHandler(IllegalArgumentException e) {
        log.error("[ExceptionHandler] BAD_REQUEST 발생", e);
        return new ErrorResult("[400] BAD_REQUEST", e.getMessage());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorResult handle404(NoHandlerFoundException e) {
        log.error("[ExceptionHandler] NOT_FOUND 발생 : ", e);
        return new ErrorResult("[404] NOT_FOUND", e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResult defaultExceptionHandler(Exception e) {
        log.error("[ExceptionHandler] INTERNAL_SERVER_ERROR 발생 : ", e);
        return new ErrorResult("[500] INTERNAL_SERVER_ERROR", e.getMessage());
    }
}