package com.runninggo.toy.domain;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

public class LoginRequestDto {

    @Getter
    public static class LoginReqDto {

        @NotBlank(message = "필수입력 항목입니다.")
        private String id;

        @NotBlank(message = "필수입력 항목입니다.")
        private String pass;

        private void setPass(String pass) {
            this.pass = pass;
        }

        public void setEncodedPass(String encodedPass) {
            setPass(encodedPass);
        }

        public LoginReqDto(String id, String pass) {
            this.id = id;
            this.pass = pass;
        }
    }

}
