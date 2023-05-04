package com.runninggo.toy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity //스프링 시큐리티 필터가 스프링 필터 체인에 등록됨
public class SecurityConfig extends WebSecurityConfigurerAdapter { //현재 스프링부트에서 Deprecated됨 - 수정해야함

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and() //CorsFilter라는 필터가 존재. 이를 활성화 시키는 작업.
                .csrf().disable(); //rest api를 이용한 서버라면, session 기반 인증과는 다르게 stateless하기 때문에 서버에 인증정보를 보관하지 않음..? 더 공부하기
//                .formLogin().disable()
//                .headers().frameOptions().disable();
    }

    //passwordEncoder
    @Bean
    public BCryptPasswordEncoder encodePassword() {
        return new BCryptPasswordEncoder();
    }

}
