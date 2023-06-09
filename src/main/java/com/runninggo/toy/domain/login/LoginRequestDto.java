package com.runninggo.toy.domain.login;

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

        public LoginReqDto() {} //@RequestBody가 붙으면 기본생성자가 꼭 필효하다고 한다.

        public LoginReqDto(String id, String pass) {
            this.id = id;
            this.pass = pass;
        }
    }

    @Getter
    public static class FindIdReqDto {

        @NotBlank(message = "필수입력 항목입니다.")
        private String name;

        @NotBlank(message = "필수입력 항목입니다.")
        private String email;

        @NotBlank(message = "필수입력 항목입니다.")
        private String hp;

        public FindIdReqDto() {}

        public FindIdReqDto(String name, String email, String hp) {
            this.name = name;
            this.email = email;
            this.hp = hp;
        }
    }

    @Getter
    public static class FindPassReqDto {

        @NotBlank(message = "필수입력 항목입니다.")
        private String id;

        @NotBlank(message = "필수입력 항목입니다.")
        private String email;

        private String pass;

        private void setPass(String pass) {
            this.pass = pass;
        }

        public void setEncodedRandomPass(String encRandomPass) {
            setPass(encRandomPass);
        }

        public FindPassReqDto() {}

        public FindPassReqDto(String id, String email) {
            this.id = id;
            this.email = email;
        }
    }

}
