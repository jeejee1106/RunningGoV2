package com.runninggo.toy.auth;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

//JWT 토큰 생성, 토큰 복호화 및 정보 추출, 토큰 유효성 검증의 기능을 구현할 클래스

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Value("${spring.jwt.secretKey}")
    private String secretKey;
    private long tokenValidTime = 1000L * 60 * 30; // 30분
    private final UserDetailsService userDetailsService;

    /**
     * 토큰 생성 메서드
     */
    public String createToken(String id) { //토큰의 키가 되는 Subject를 중복되지 않는 고유한 값인 member의 id(pk)로 지정
        Claims claims = Jwts.claims().setSubject(id);
        Date now = new Date();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenValidTime)) //만료시간은 지금 시간으로부터 30분
                .signWith(SignatureAlgorithm.HS256, secretKey) //서명할 때 사용되는 알고리즘은 HS256, 키는 위에서 지정한 값으로 진행
                .compact();
    }

    /**
     * 토큰으로 인증 객체(Authentication)을 얻기 위한 메소드
     * UserDetailsService를 구현한 구현체인 MemberDetailsService에서
     * loadUserByUsername 메소드를 구현하여 실제 DB에 저장되어 있는 회원 객체를 끌고와 인증처리를 진행
     *
     * 즉, 토큰을 통해 이미 인증된 객체를 memberDetailsService에서 불러와 인증객체를 얻어준다.
     */
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(getMemberId(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }


    /**
     * id를 얻기 위해 실제로 토큰을 디코딩하는 부분
     * 지정한 Secret Key를 통해 서명된 JWT를 해석하여 Subject를 끌고와 리턴하여 이를 통해 인증 객체를 끌고올 수 있다.
     */
    public String getMemberId(String token) {
        try {
            return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject(); //지정한 Secret Key를 통해 서명된 JWT를 해석하여 Subject를 끌고와 리턴하여 이를 통해 인증 객체를 끌고올 수 있다.
        } catch(ExpiredJwtException e) {
            return e.getClaims().getSubject();
        }
    }

    /**
     * 토큰이 만료되었는지 확인해주는 메소드
     * token을 디코딩하여 만료시간을 끌고와 현재시간과 비교해 확인해준다.
     */
    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch(Exception e) {
            return false;
        }
    }

    // 토큰 정보를 검증하는 메서드
    //흠 얘는 왜 에러가 나지? 위 메서드랑 비슷한 역할일 것 같다..
//    public boolean validateToken(String token) {
//        try {
//            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
//            return true;
//        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
//            log.info("Invalid JWT Token", e);
//        } catch (ExpiredJwtException e) {
//            log.info("Expired JWT Token", e);
//        } catch (UnsupportedJwtException e) {
//            log.info("Unsupported JWT Token", e);
//        } catch (IllegalArgumentException e) {
//            log.info("JWT claims string is empty.", e);
//        }
//        return false;
//    }

    /**
     * 토큰은 HTTP Header에 저장되어 계속적으로 이용되어진다.
     * 토큰을 사용하기 위해 실제로 Header에서 꺼내오는 메소드이다.
     */
    public String resolveToken(HttpServletRequest req) {
        return req.getHeader("X-AUTH-TOKEN");
    }
}
