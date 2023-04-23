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
        log.error("[ExceptionHandler] BAD_REQUEST", e);
        return new ErrorResult("BAD_REQUEST", e.getMessage());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public String handle404(NoHandlerFoundException exception) {
        log.error("404Exception 발생 : ", exception);
        return "/error/custom404";
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public String defaultExceptionHandler(Exception exception) {
        log.error("Server Exception 발생 : ", exception);
        return "/error/custom500";
    }
}