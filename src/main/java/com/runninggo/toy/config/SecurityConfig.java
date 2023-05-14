package com.runninggo.toy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity //스프링 시큐리티 필터가 스프링 시큐리티 필터 체인(SpringSecurityFilterChain)에 등록됨
public class SecurityConfig extends WebSecurityConfigurerAdapter { //스프링 시큐리티의 웹 보안 기능 초기화 및 설정 (현재 스프링부트에서 Deprecated됨 - 수정해야함)

    /**
     * [@EnableWebSecurity]
     * - @EnableWebSecurity 애노테이션을 WebSecurityConfigurerAdapter를 상속하는 설정 객체에 붙혀주면 SpringSecurityFilterChain에 등록된다.
     * - 스프링 MVC에서 웹 보안을 활성화하기 위한 애너테이션으로 핸들러 메소드에서 @AuthenticationPrincipal 애노테이션이 붙은 매개변수를 이용해 인증처리를 수행한다.
     * - 그리고 자동으로 CSRF 토큰을 스프링의 form binding tag library를 사용해 추가하는 빈을 설정한다.
     *
     * [WebSecurityConfigurerAdapter]
     * - 스프링 시큐리티의 웹 보안 기능 초기화 및 설정
     * - Security Dependency를 추가한 이후 기본적인 security를 설정및 구현하는 클래스
     * - HttpSecurity 라는 세부적인 보안기능을 설정할수 있는 API를 제공하는 클래스를 생성한다.
     */

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable() //rest api이므로 기본설정 미사용
                .csrf().disable() //rest api이므로 csrf 보안 미사용. rest api를 이용한 서버라면, session 기반 인증과는 다르게 stateless하기 때문에 서버에 인증정보를 보관하지 않음..? 더 공부하기
                .formLogin().disable() //rest api이므로 formLogin 미사용. 보안 검증은 formLogin 방식으로 하겠다. -> 얘를 활성화 시키면 /place/recmndForm 주소를 요청했을 때 403이 아닌 로그인 페이지가 뜬다!!
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // jwt로 인증하므로 세션 미사용
//                .cors()//CorsFilter라는 필터가 존재. 이를 활성화 시키는 작업.
                .and()
                .authorizeRequests() //여기부터 인증절차에 대한 설정을 진행하겠다. - 요청에 의한 보안검사 시작
                .antMatchers("/").permitAll() //antMatchers : 특정 URL 에 대해서 어떻게 인증처리를 할지, permitAll : 스프링 시큐리티에서 인증이 되지 않더라도 통과
                .antMatchers("/login/**").permitAll()
                .antMatchers("/join/**").permitAll()
                .anyRequest().authenticated(); ///login/**, /join/** 로 들어오는 요청을 제외하고 모두 인증을 하도록!!
//                .and()
//                .passwordParameter("pass") //스프링이 받는 파라미터는 각각 password, username인데, 내가 설정해준 값은 pass, id이기 때문에 이렇게 설정을 바꿔줘야한다.
//                .usernameParameter("id");
//                .headers().frameOptions().disable();
    }

    //passwordEncoder
    @Bean
    public BCryptPasswordEncoder encodePassword() {
        return new BCryptPasswordEncoder();
    }

}

/**
 * [스프링 시큐리티의 의존성 추가 시 일어나는 일들]
 * - 서버가 기동되면 스프링 시큐리티의 초기화 작업 및 보안 설정이 이뤄진다.
 * - 별도의 설정이나 구현을 하지 않아도 기본적인 웹 보안 기능이 현재 시스템에 연동된다.
 *   1. 모든 요청은 인증이 되어야 자원에 접근이 가능하다.
 *   2. 인증 방식은 폼 로그인 방식과 httpBasic로그인 방식을 제공한다.
 *   3. 기본 로그인 페이지 제공
 *   4. 기본 계정 한 개 제공 user/랜덤문자열
 */
