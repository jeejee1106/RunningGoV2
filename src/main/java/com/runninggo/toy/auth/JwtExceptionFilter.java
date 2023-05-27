package com.runninggo.toy.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.runninggo.toy.exception.ErrorResult;
import io.jsonwebtoken.JwtException;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtExceptionFilter extends OncePerRequestFilter {

    /*
    인증 오류가 아닌, JWT 관련 오류는 이 필터에서 따로 잡아낸다.
    이를 통해 JWT 만료 에러와 인증 에러를 따로 잡아낼 수 있다.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        try {
            chain.doFilter(request, response); // JwtAuthenticationFilter로 이동
        } catch (JwtException ex) {
            // JwtAuthenticationFilter에서 예외 발생하면 바로 setErrorResponse 호출
            setErrorResponse(request, response, ex);
        }
    }

    public void setErrorResponse(HttpServletRequest req, HttpServletResponse res, Throwable ex) throws IOException {

        res.setContentType(MediaType.APPLICATION_JSON_VALUE);

//        final Map<String, Object> body = new HashMap<>();
        ErrorResult errorResult = new ErrorResult("401", ex.getMessage());
//        body.put("status", HttpServletResponse.SC_UNAUTHORIZED);
//        body.put("error", "Unauthorized");
        // ex.getMessage() 에는 jwtException을 발생시키면서 입력한 메세지가 들어있다.
//        body.put("message", ex.getMessage());
//        body.put("path", req.getServletPath());
        final ObjectMapper mapper = new ObjectMapper();
//        mapper.writeValue(res.getOutputStream(), body);
        mapper.writeValue(res.getOutputStream(), errorResult);
        res.setStatus(HttpServletResponse.SC_UNAUTHORIZED); //현재 200OK를 반환함.. 상태코드를 400으로 낼 순 없을까?
    }
}
