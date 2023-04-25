package com.runninggo.toy.domain;

import com.runninggo.toy.mail.TempKey;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

public class JoinRequestDto {

    @Getter
    public static class idCheckReqDto{
        @Pattern(regexp = "^[a-z0-9]{5,20}$", message = "5~20자의 영문 소문자, 숫자만 사용 가능합니다.")
        @NotNull(message = "필수입력 항목입니다.")
        private String id;
    }

    @Getter
    public static class JoinReqDto {

        @Pattern(regexp = "^[a-z0-9]{5,20}$", message = "5~20자의 영문 소문자, 숫자만 사용 가능합니다.")
        @NotNull(message = "필수입력 항목입니다.")
        private String id;

        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,20}$",
                message = "8~20자의 영문 대소문자+숫자+특수문자를 사용하세요.")
        @NotNull(message = "필수입력 항목입니다.")
        private String pass;

        @NotNull(message = "필수입력 항목입니다.")
        private String passCheck;

        @Pattern(regexp = "^[가-힣]{2,}$", message = "이름은 한글만 입력 가능합니다.")
        @NotNull(message = "필수입력 항목입니다.")
        private String name;

        @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9-]+[.][A-Za-z]{2,6}$", message = "이메일 형식이 올바르지 않습니다.")
        @NotNull(message = "필수입력 항목입니다.")
        private String email;

        @Pattern(regexp = "^01(?:0|1|[6-9])(-)(\\d{3}|\\d{4})(-)(\\d{4})$", message = "휴대폰 번호 형식이 올바르지 않습니다.")
        @NotNull(message = "필수입력 항목입니다.")
        private String hp;

        private String area;

        private LocalDateTime join_date;

        private String mailKey;

        private void setMailKey(String mailKey) { //setter지만 부분별한 사용(무분별한 값 변경)을 막기위해 private로 지정.
            this.mailKey = mailKey;
        }

        //대신 이렇게 좀 더 명확한 이름의 메서드를 만들고 그 안에 setter를 넣는다!
        public void InsertMailKey() {
            String mailKey = new TempKey().getKey(30,false);
            setMailKey(mailKey);
        }

        private void setPass(String pass) {
            this.pass = pass;
        }

        public void setEncodePass(String pass) {
            setPass(pass);
        }

        public JoinReqDto(String id, String pass, String passCheck, String name, String email, String hp, String area, LocalDateTime join_date, String mailKey) {
            this.id = id;
            this.pass = pass;
            this.passCheck = passCheck;
            this.name = name;
            this.email = email;
            this.hp = hp;
            this.area = area;
            this.join_date = join_date;
            this.mailKey = mailKey;
        }
    }

    @Getter
    public static class UpdateMailAuthReqDto {

        @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9-]+[.][A-Za-z]{2,6}$", message = "이메일 형식이 올바르지 않습니다.")
        @NotNull(message = "필수입력 항목입니다.")
        private String email;

        @NotNull
        private String mailKey;

        public UpdateMailAuthReqDto(String email, String mailKey) {
            this.email = email;
            this.mailKey = mailKey;
        }
    }


}
