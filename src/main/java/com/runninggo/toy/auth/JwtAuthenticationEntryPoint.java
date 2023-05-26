package com.runninggo.toy.auth;

import com.runninggo.toy.exception.ErrorResult;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) {
        String exception = (String)request.getAttribute("exception");

        ErrorResult errorResult = new ErrorResult();

        if(exception == null) {
            errorResult.setCode("[403] UNAUTHORIZED");
            errorResult.setMessage("에러 메세지");
        }
        //잘못된 타입의 토큰인 경우
        else if(exception.equals(MalformedJwtException)) {
            errorResult.setCode("[403] UNAUTHORIZED");
            errorResult.setMessage("에러 메세지");
        }
        //토큰 만료된 경우
        else if(exception.equals(Code.EXPIRED_TOKEN.getCode())) {
            errorResult.setCode("[403] UNAUTHORIZED");
            errorResult.setMessage("에러 메세지");
        }
        //지원되지 않는 토큰인 경우
        else if(exception.equals(Code.UNSUPPORTED_TOKEN.getCode())) {
            errorResult.setCode("[403] UNAUTHORIZED");
            errorResult.setMessage("에러 메세지");
        }
        else {
            errorResult.setCode("[403] UNAUTHORIZED");
            errorResult.setMessage("에러 메세지");
        }
    }
}