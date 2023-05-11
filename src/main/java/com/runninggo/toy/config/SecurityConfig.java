package com.runninggo.toy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity //스프링 시큐리티 필터가 스프링 시큐리티 필터 체인(SpringSecurityFilterChain)에 등록됨
public class SecurityConfig extends WebSecurityConfigurerAdapter { //현재 스프링부트에서 Deprecated됨 - 수정해야함

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()//CorsFilter라는 필터가 존재. 이를 활성화 시키는 작업.
                .and()
                .csrf().disable() //rest api를 이용한 서버라면, session 기반 인증과는 다르게 stateless하기 때문에 서버에 인증정보를 보관하지 않음..? 더 공부하기
                .authorizeRequests() //여기부터 인증절차에 대한 설정을 진행하겠다. - 요청에 의한 보안검사 시작
                .antMatchers("/").permitAll() //antMatchers : 특정 URL 에 대해서 어떻게 인증처리를 할지, permitAll : 스프링 시큐리티에서 인증이 되지 않더라도 통과
                .antMatchers("/place/recmndForm").authenticated() //authenticated : 요청내에 스프링 시큐리티 컨텍스트 내에서 인증이 완료되어야 api를 사용할 수 있음. 인증되지 않으면 403에러
                .and()
                .formLogin(); //보안 검증은 formLogin 방식으로 하겠다.
//                .headers().frameOptions().disable();
    }

    //passwordEncoder
    @Bean
    public BCryptPasswordEncoder encodePassword() {
        return new BCryptPasswordEncoder();
    }

}
