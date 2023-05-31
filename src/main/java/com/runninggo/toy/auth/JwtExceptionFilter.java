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

@Component
public class JwtExceptionFilter extends OncePerRequestFilter {

    /*
    인증 오류가 아닌, JWT 관련 오류는 이 필터에서 따로 잡아낸다.
    이를 통해 JWT 만료 에러와 인증 에러를 따로 잡아낼 수 있다.

    라고 해서 어떤 블로그에서 작성한 코드를 그대로 가져온거긴한데....
    필터에 대해 다시 공부하고 보니 굳이 이렇게 따로 구현하지 않고 JwtAuthenticationFilter에서 구현해도 되지 않을까 싶어 코드 옮김..
    어떤 방식이 더 효율적인지...?

    어쨋든 230531 현재 해당 필터는 사용하지 않음!
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

        final ObjectMapper mapper = new ObjectMapper();
        ErrorResult errorResult = new ErrorResult("401", ex.getMessage());

//        final Map<String, Object> body = new HashMap<>();
//        body.put("status", HttpServletResponse.SC_UNAUTHORIZED);
//        body.put("error", "Unauthorized");
        // ex.getMessage() 에는 jwtException을 발생시키면서 입력한 메세지가 들어있다.
//        body.put("message", ex.getMessage());
//        body.put("path", req.getServletPath());

//        mapper.writeValue(res.getOutputStream(), body);
        mapper.writeValue(res.getOutputStream(), errorResult);
        res.setStatus(HttpServletResponse.SC_UNAUTHORIZED); //현재 200OK를 반환함.. 상태코드를 400으로 낼 순 없을까?
    }
}
