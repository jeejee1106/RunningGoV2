package com.runninggo.toy.exception;

import com.runninggo.toy.dao.MemberDao;
import com.runninggo.toy.domain.CommonResponseDto;
import com.runninggo.toy.myinfo.MyInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

//    MessageSourceAccessor messageSource;
//
//    public GlobalExceptionHandler(MessageSourceAccessor messageSource) {
//        this.messageSource = messageSource;
//    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public CommonResponseDto IllegalArgumentException(IllegalArgumentException exception) {
        log.error("IllegalArgumentException 발생 : ", exception);
        CommonResponseDto response = new CommonResponseDto();

        response.setReturnCode("fail.code");
        response.setMessage(exception.getMessage());
        return response; //왜 404가 리턴되는거야 왜............
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