package com.runninggo.toy.domain.course;

import lombok.Getter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

public class CourseRequestDto {

    @Getter
    public static class InsertCourseReqDto {

        @NotBlank(message = "필수입력 항목입니다.")
        @Pattern(regexp = "^[a-z0-9]{5,20}$", message = "5~20자의 영문 소문자, 숫자만 사용 가능합니다.")
        private String id;

        @NotBlank(message = "필수입력 항목입니다.")
        private String local1;

        @NotBlank(message = "필수입력 항목입니다.")
        private String local2;

        @NotBlank(message = "필수입력 항목입니다.")
        private String subwayNm;

        @NotBlank(message = "필수입력 항목입니다.")
        private String level;

        @Size(max = 50, message = "50자 이하로 입력해주세요")
        private String storage;

        @NotNull(message = "필수입력 항목입니다.")
        @Range(min = 1, max = 99, message = "1 이상 99 이하의 숫자만 입력해주세요.")
        private Double distance;

        @NotBlank(message = "필수입력 항목입니다.")
        @Size(min = 50, max = 1000, message = "50자 이상 1000자 이하로 작성해주세요.")
        private String description;

        @NotBlank(message = "필수입력 항목입니다.")
        @Size(min = 50, max = 1000, message = "50자 이상 1000자 이하로 작성해주세요.")
        private String rcmndReason;

        private String imgUrl;

        @NotBlank(message = "필수입력 항목입니다.")
        private String delYn;

        private LocalDateTime createDt;

        public InsertCourseReqDto(String id, String local1, String local2, String subwayNm, String level, String storage, Double distance, String description, String rcmndReason, String imgUrl, String delYn, LocalDateTime createDt) {
            this.id = id;
            this.local1 = local1;
            this.local2 = local2;
            this.subwayNm = subwayNm;
            this.level = level;
            this.storage = storage;
            this.distance = distance;
            this.description = description;
            this.rcmndReason = rcmndReason;
            this.imgUrl = imgUrl;
            this.delYn = delYn;
            this.createDt = createDt;
        }
    }

    @Getter
    public static class GetCourseReqDto {

        private Integer idx;
        private String id;
        private String local1;
        private String local2;
        private String subwayNm;
        private String level;
        private String storageYn;
        private String delYn;
        private LocalDateTime createDt;

        public GetCourseReqDto(Integer idx, String id, String local1, String local2, String subwayNm, String level, String storageYn, String delYn, LocalDateTime createDt) {
            this.idx = idx;
            this.id = id;
            this.local1 = local1;
            this.local2 = local2;
            this.subwayNm = subwayNm;
            this.level = level;
            this.storageYn = storageYn;
            this.delYn = delYn;
            this.createDt = createDt;
        }
    }
}
