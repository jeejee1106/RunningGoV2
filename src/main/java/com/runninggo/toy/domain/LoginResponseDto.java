package com.runninggo.toy.domain;

import lombok.Getter;
import lombok.Setter;

public class LoginResponseDto {

    @Getter
    @Setter
    public static class FindIdResDto {
        private String id;
        private String join_date;

        public FindIdResDto(String id, String join_date) {
            this.id = id;
            this.join_date = join_date;
        }
    }


}
