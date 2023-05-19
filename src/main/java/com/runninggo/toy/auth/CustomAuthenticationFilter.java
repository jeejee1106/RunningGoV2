package com.runninggo.toy.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.runninggo.toy.domain.login.LoginRequestDto;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * http.formLogin() 을 사용하면 시큐리티에서는 기본적으로 UsernamePasswordAuthenticationFilter 을 사용하게 된다.
 * 즉, UsernamePasswordAuthenticationFilter는 formLogin시에 사용하는 것이라고 한다.ㅜㅜ
 * 근데 이것을 사용한다고 해도 아래 코드처럼 구현하는 것은 아닌 것 같다..
 * 사용하지 않으므로 일단 주석처리(따로 공부해보자)
 */

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    /**
     * 전송이 오면 AuthenticationFilter로 요청이 먼저 오게 되고, 아이디와 비밀번호를 기반으로 UserPasswordAuthenticationToken을 발급해주어야 함.
     */

//    public CustomAuthenticationFilter(final AuthenticationManager authenticationManager) {
//        super.setAuthenticationManager(authenticationManager);
//    }
//
//    @Override
//    public Authentication attemptAuthentication(final HttpServletRequest request, final HttpServletResponse response) throws AuthenticationException {
//        try{
//            final LoginRequestDto.LoginReqDto user = new ObjectMapper().readValue(request.getInputStream(), LoginRequestDto.LoginReqDto.class);
//            final UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(user.getId(), user.getPass());
//        } catch (IOException exception){
//            //예외 터뜨리기
////            throw new InputNotFoundException();
//        }
//        setDetails(request, authRequest);
//        return this.getAuthenticationManager().authenticate(authRequest);
//    }
}
