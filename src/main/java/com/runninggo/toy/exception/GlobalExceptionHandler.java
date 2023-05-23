package com.runninggo.toy.exception;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
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
    public ErrorResult illegalArgumentException(IllegalArgumentException e) {
        log.error("[ExceptionHandler] BAD_REQUEST 발생", e);
        return new ErrorResult("[400] BAD_REQUEST", e.getMessage());
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorResult bindException(BindException e, BindingResult bindingResult) {
        log.error("[ExceptionHandler] BindException 발생 : ", e);
        return new ErrorResult("[400] BindException", bindingResult.getFieldError().getDefaultMessage());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorResult noHandlerFoundException(NoHandlerFoundException e) {
        log.error("[ExceptionHandler] NOT_FOUND 발생 : ", e);
        return new ErrorResult("[404] NOT_FOUND", e.getMessage());
    }

    @ExceptionHandler(JwtException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public ErrorResult jwtException(Exception e) {
        log.error("[ExceptionHandler] UNAUTHORIZED 발생 : ", e);
        return new ErrorResult("[403] UNAUTHORIZED", e.getMessage());
    }

    @ExceptionHandler(MalformedJwtException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public ErrorResult MalformedJwtException(Exception e) {
        log.error("[ExceptionHandler] MalformedJwtException 발생 : ", e);
        return new ErrorResult("[403] MalformedJwtException", e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResult exception(Exception e) {
        log.error("[ExceptionHandler] INTERNAL_SERVER_ERROR 발생 : ", e);
        return new ErrorResult("[500] INTERNAL_SERVER_ERROR", e.getMessage());
    }
}