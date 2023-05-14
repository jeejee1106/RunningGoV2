package com.runninggo.toy.domain.config;

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
}
