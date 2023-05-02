package com.runninggo.toy.domain.login;

import lombok.Getter;

public class LoginResponseDto {

    @Getter
    public static class FindIdResDto {
        private String id;
        private String join_date;

        public FindIdResDto(String id, String join_date) {
            this.id = id;
            this.join_date = join_date;
        }
    }


}
