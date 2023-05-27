package com.runninggo.toy.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.runninggo.toy.exception.ErrorResult;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Spring Security 예외는 사용자 정의 필터를 추가하고 Response body을 구성하여 직접 처리할 수 있다.
 * @ExceptionHandler 및 @ControllerAdvice 를 통해 전역 수준에서 이러한 예외를 처리하려면
 * Security 예외를 가로채는 AuthenticationEntryPoint 에 대한 구현이 필요하다.
 * AuthenticationEntryPoint 는 클라이언트로부터 자격 증명을 요청하는 HTTP 응답을 보내는 데 사용된다.
 */

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        /**
         * AuthenticationException으로 들어오는 예외는 Spring security 내의 ExceptionTranslationFilter에서 던져진다.
         * 이 예외는 AuthenticationEntryPoint로 보내지고 지금 이 클래스에서 응답을 처리하게 된다.
         */

        final Map<String, Object> body = new HashMap<>();
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        // 응답 객체 초기화
        body.put("status", HttpServletResponse.SC_UNAUTHORIZED);
        body.put("error", "Unauthorized");
        body.put("message", authException.getMessage());
        body.put("path", request.getServletPath());
        final ObjectMapper mapper = new ObjectMapper();
        // response 객체에 응답 객체를 넣어줌
        mapper.writeValue(response.getOutputStream(), body);
        response.setStatus(HttpServletResponse.SC_OK);
    }
}