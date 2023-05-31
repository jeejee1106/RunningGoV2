package com.runninggo.toy.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.runninggo.toy.exception.ErrorResult;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 클라이언트 요청 시 JWT 인증을 하기 위해 설치하는 커스텀 필터로, UsernamePasswordAuthenticationFilter 이전에 실행된다.
 * 이전에 실행된다는 뜻은 JwtAuthenticationFilter를 통과하면 UsernamePasswordAuthenticationFilter 이후의 필터는 통과한 것으로 본다는 뜻이다.
 * 쉽게 말해서, Username + Password를 통한 인증을 Jwt를 통해 수행한다는 것이다.
 */

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info(">>>>토큰필터에 들어옴");
        // 1. Request Header 에서 JWT 토큰 추출 -> JwtTokenProvider에 있는 resolveToken()과 비교해보기!
        String token = resolveToken((HttpServletRequest) request);

        // 2. validateToken 으로 토큰 유효성 검사
        try {
            if (token != null && jwtTokenProvider.validateToken(token)) {
                log.info(">>>>>토큰이 유효함. 또는 토큰이 존재함");
                // 토큰이 유효할 경우 토큰에서 Authentication 객체를 가지고 와서
                Authentication authentication = jwtTokenProvider.getAuthentication(token);
                //SecurityContext 에 저장
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (JwtException ex) {
            log.info(">>>>>토큰이 유효하지 않음.");
            // JwtAuthenticationFilter에서 예외 발생하면 바로 setErrorResponse 호출
            setErrorResponse((HttpServletRequest) request, (HttpServletResponse) response, ex);
        } finally {
            chain.doFilter(request, response);
        }
//        if (token != null && jwtTokenProvider.validateToken(token)) {
//            log.info(">>>>>토큰이 유효함. 또는 토큰이 존재함");
//            // 토큰이 유효할 경우 토큰에서 Authentication 객체를 가지고 와서
//            Authentication authentication = jwtTokenProvider.getAuthentication(token);
//            //SecurityContext 에 저장
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//        } else {
//
//        }
        //doFilter 메소드를 통해 다음 필터로 넘어가고, 실제 AuthenticationFilter에서 이미 인증되어 있는 객체를 통해 인증이 되게 된다.
    }

    // Request Header 에서 토큰 정보 추출
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
            return bearerToken.substring(7);
        }
        return null;
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
