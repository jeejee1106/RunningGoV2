package com.runninggo.toy.domain;

import lombok.Getter;

public class JoinResponseDto {

    public static class IdCheckResDto {
        public IdCheckResDto(int count) {
            this.count = count;
        }

        private int count;

        public int getCount() {
            return count;
        }
    }


}
