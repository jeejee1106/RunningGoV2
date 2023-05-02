package com.runninggo.toy.domain.course;

import lombok.Getter;

public class CourseResponseDto {

    @Getter
    public static class getSubwayInfoResDto {
        private String lineNum;

        public getSubwayInfoResDto(String lineNum) {
            this.lineNum = lineNum;
        }
    }


}
