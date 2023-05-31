package com.runninggo.toy.config;

import com.runninggo.toy.auth.JwtAuthenticationEntryPoint;
import com.runninggo.toy.auth.JwtAuthenticationFilter;
import com.runninggo.toy.auth.JwtExceptionFilter;
import com.runninggo.toy.auth.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * [스프링 시큐리티의 의존성 추가 시 일어나는 일들]
 * - 서버가 기동되면 스프링 시큐리티의 초기화 작업 및 보안 설정이 이뤄진다.
 * - 별도의 설정이나 구현을 하지 않아도 기본적인 웹 보안 기능이 현재 시스템에 연동된다.
 *   1. 모든 요청은 인증이 되어야 자원에 접근이 가능하다.
 *   2. 인증 방식은 폼 로그인 방식과 httpBasic로그인 방식을 제공한다.
 *   3. 기본 로그인 페이지 제공
 *   4. 기본 계정 한 개 제공 user/랜덤문자열
 */

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity //스프링 시큐리티 필터가 스프링 시큐리티 필터 체인(SpringSecurityFilterChain)에 등록됨
public class SecurityConfig extends WebSecurityConfigurerAdapter { //스프링 시큐리티의 웹 보안 기능 초기화 및 설정 (현재 스프링부트에서 Deprecated됨 - 수정해야함)
//public class SecurityConfig {

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

    private final JwtTokenProvider jwtTokenProvider;
    private final JwtExceptionFilter jwtExceptionFilter;
//    @Bean
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable() //rest api이므로 기본설정 미사용
                .csrf().disable() //rest api이므로 csrf 보안 미사용. rest api를 이용한 서버라면, session 기반 인증과는 다르게 stateless하기 때문에 서버에 인증정보를 보관하지 않음..? 더 공부하기 ->세션, 쿠키의 로그인 방식과 JWT의 가장 큰 차이점은 서버는 클라이언트의 상태를 완전히 저장하지 않는 무상태성(Stateless)을 유지할 수 있다는 점
                .formLogin().disable() //rest api이므로 formLogin 미사용. 보안 검증은 formLogin 방식으로 하겠다. -> 얘를 활성화 시키면 /place/recmndForm 주소를 요청했을 때 403이 아닌 로그인 페이지가 뜬다!!
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // jwt로 인증하므로 세션 미사용
                //여기까지가 rest-api를 위한 설정
//                .cors()//CorsFilter라는 필터가 존재. 이를 활성화 시키는 작업.
                .and()
                .authorizeRequests() //여기부터 인증절차에 대한 설정을 진행하겠다. - 요청에 의한 보안검사 시작
                .antMatchers("/").permitAll() //antMatchers : 특정 URL 에 대해서 어떻게 인증처리를 할지, permitAll : 스프링 시큐리티에서 인증이 되지 않더라도 통과
                .antMatchers("/login/**").permitAll()
                .antMatchers("/join/**").permitAll()
                .anyRequest().authenticated() ///login/**, /join/** 로 들어오는 요청을 제외하고 모두 인증을 하도록!!
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new JwtAuthenticationEntryPoint())
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class); //JWT 설정을 위해서는 formLogin 기능을 빼고 이렇게 사용자가 직접 필터 클래스를 만들어줘야 한다.
//                .addFilterBefore(jwtExceptionFilter, new JwtAuthenticationFilter(jwtTokenProvider).getClass());
//                .passwordParameter("pass") //스프링이 받는 파라미터는 각각 password, username인데, 내가 설정해준 값은 pass, id이기 때문에 이렇게 설정을 바꿔줘야한다.
//                .usernameParameter("id");
//                .headers().frameOptions().disable();
    }

    /**
     * .httpBasic.disable()
     * httpBasic은 기본적으로 disable이지만 켜두면 화면에 얼럿창이 뜬다.
     * 쿠키와 세션을 이용한 방식이 아니라 request header에 id와 password값을 직접 날리는 방식이라 보안에 굉장히 취약하다.
     * REST API에서는 오로지 토큰 방식을 이용하기 때문에 보안에 취약한 httpBasic 방식은 해제한다고 보면 된다.
     *
     * http.csrf().disable()
     * CSRF를 켜두면 서버는 클라이언트 영역에 CSRF 토큰을 보낸다.
     * 그리고 다시 클라이언트에서 서버로 CSRF 토큰이 전달되지 않으면 403 error를 뱉고 인가시켜주지 않는다.
     * REST API에서는 CSRF 방어가 필요가 없고 더불어 CSRF 토큰을 주고 받을 필요가 없기 때문에 CSRF 설정을 해제한다.
     *
     * .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
     * 서버를 Stateless하게 유지한다.
     * 이걸 설정하면 Spring Security에서 세션을 만들지 않는다.
     * 만약에 켜둔다면 JWT Token으로 로그인하더라도 클라이언트에서 Token값을 서버에 전달하지 않더라도 세션 값으로 로그인이 된다.
     *
     * .formLogin().disable()
     * formLogin 기능을 끈다고 해서 form 태그 내에 로그인 기능을 못쓴다는 것은 아니다.
     * formLogin을 끄면 초기 로그인 화면이 사라진다. 이것보다 궁극적인 이유는 JWT의 기능을 만들기 위해서다.
     * formLogin은 세션 로그인 방식에서 로그인을 자동처리 해준다는 장점이 존재했는데, JWT에서는 로그인 방식 내에 JWT 토큰을 생성하는 로직이 필요하기 때문에 로그인 과정을 수동으로 클래스를 만들어줘야 하기 때문에 formLogin 기능을 제외한다.
     * formLogin 기능 자체가 REST API에 반대되는 특징을 가지고 있다. formLogin의 defaultSuccessUrl 메소드로 로그인 성공 시 리다이렉트 할 주소를 입력하게 되는데 REST API에서는 서버가 페이지의 기능을 결정하면 안되기 때문에 결과적으로 필요하지 않은 formLogin은 disable한다.
     */


    //passwordEncoder
    @Bean
    public BCryptPasswordEncoder encodePassword() {
        return new BCryptPasswordEncoder();
    }

}
