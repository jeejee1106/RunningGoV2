package com.runninggo.toy.domain.course;

import lombok.Getter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.*;
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

        @NotBlank(message = "필수입력 항목입니다.")
        private String storageYn;

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

        public InsertCourseReqDto(String id, String local1, String local2, String subwayNm, String level,String storageYn, String storage, Double distance, String description, String rcmndReason, String imgUrl, String delYn, LocalDateTime createDt) {
            this.id = id;
            this.local1 = local1;
            this.local2 = local2;
            this.subwayNm = subwayNm;
            this.level = level;
            this.storageYn = storageYn;
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
    public static class GetAllCourseReqDto {

        private Integer idx;
        private String id;
        private String local1;
        private String local2;
        private String subwayNm;
        private String level;
        private String storageYn;
        private String delYn;
        private LocalDateTime createDt;

        public GetAllCourseReqDto(Integer idx, String id, String local1, String local2, String subwayNm, String level, String storageYn, String delYn, LocalDateTime createDt) {
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

    @Getter
    public static class PatchCourseReqDto {

        private String idx;

        private String id;

        @NotBlank(message = "필수입력 항목입니다.")
        private String local1;

        @NotBlank(message = "필수입력 항목입니다.")
        private String local2;

        @NotBlank(message = "필수입력 항목입니다.")
        private String subwayNm;

        @NotBlank(message = "필수입력 항목입니다.")
        private String level;

        @NotBlank(message = "필수입력 항목입니다.")
        private String storageYn;

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

        //값을 함부로 바꾸는 것을 방지하기 위해 setter는 private로 막아놓고 아래 public 메서드를 이용하게 한다.(더 명확한 메서드명으로 명명)
        private void setId(String id) {
            this.id = id;
        }

        //어떤 작업을 하는 메서드인지 알 수 있게 메서드명을 확실하게 지어보자.
        public void setIdForJwtInfo(String id) {
            setId((id));
        }

        //idx도 마찬가지
        private void setIdx(String idx) {
            this.idx = idx;
        }

        public void setIdxByPathVariable(String idx) {
            setIdx((idx));
        }

        public PatchCourseReqDto(String idx, String id, String local1, String local2, String subwayNm, String level,String storageYn, String storage, Double distance, String description, String rcmndReason, String imgUrl, String delYn) {
            this.idx = idx;
            this.id = id;
            this.local1 = local1;
            this.local2 = local2;
            this.subwayNm = subwayNm;
            this.level = level;
            this.storageYn = storageYn;
            this.storage = storage;
            this.distance = distance;
            this.description = description;
            this.rcmndReason = rcmndReason;
            this.imgUrl = imgUrl;
            this.delYn = delYn;
        }
    }
}
