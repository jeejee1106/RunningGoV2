package com.runninggo.toy.domain;

import lombok.Getter;

public class JoinResponseDto {

    public static class IdCheckResDto {
        public IdCheckResDto(boolean isDuplicateId) {
            this.isDuplicateId = isDuplicateId;
        }

        private boolean isDuplicateId;

        public boolean getCount() {
            return isDuplicateId;
        }
    }


}
