package com.runninggo.toy.auth;

import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtTokenProvider {

    @Value("${spring.jwt.secretKey}")
    private String secretKey;

    private long tokenValidTime = 1000L * 60 * 30; // 30분

}
