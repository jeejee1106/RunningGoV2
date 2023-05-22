package com.runninggo.toy.domain.config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class AuthDto {

    public static class TokenInfoDto {

        private String grantType; //JWT 대한 인증 타입으로, 여기서는 Bearer를 사용
        private String accessToken;
        private String refreshToken;

        public TokenInfoDto() {
        }

        public TokenInfoDto(String grantType, String accessToken, String refreshToken) {
            this.grantType = grantType;
            this.accessToken = accessToken;
            this.refreshToken = refreshToken;
        }
    }

    public static class UserAuthDto extends User {

        private String email;
        private String name;
        private boolean fromSocial;

        public UserAuthDto(String username, String password, boolean fromSocial, Collection<? extends GrantedAuthority> authorities){
            //User 클래스의 생성자를 호출한다.
            super(username, password, authorities);

            this.email=username;
            this.fromSocial=fromSocial;
        }
    }
}
