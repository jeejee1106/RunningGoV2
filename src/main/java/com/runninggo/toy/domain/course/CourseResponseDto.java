package com.runninggo.toy.domain.course;

import lombok.Getter;

import java.time.LocalDateTime;

public class CourseResponseDto {

    @Getter
    public static class getSubwayInfoResDto {
        private String lineNum;

        public getSubwayInfoResDto(String lineNum) {
            this.lineNum = lineNum;
        }
    }

    @Getter
    public static class GetCourseResDto {
        private Integer idx;
        private String id;
        private String local1;
        private String local2;
        private String subwayNm;
        private String level;
        private String storageYn;
        private String storage;
        private Double distance;
        private String description;
        private String rcmndReason;
        private String imgUrl;
        private String delYn;
        private LocalDateTime createDt;
        private LocalDateTime updateDt;

        public GetCourseResDto(Integer idx, String id, String local1, String local2, String subway_nm, String level,
                               String storageYn, String storage, Double distance, String description, String rcmndReason,
                               String imgUrl, String delYn, LocalDateTime createDt, LocalDateTime updateDt) {
            this.idx = idx;
            this.id = id;
            this.local1 = local1;
            this.local2 = local2;
            this.subwayNm = subway_nm;
            this.level = level;
            this.storageYn = storageYn;
            this.storage = storage;
            this.distance = distance;
            this.description = description;
            this.rcmndReason = rcmndReason;
            this.imgUrl = imgUrl;
            this.delYn = delYn;
            this.createDt = createDt;
            this.updateDt = updateDt;
        }
    }

}
