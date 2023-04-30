package com.runninggo.toy.domain.place;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
public class PlaceRequestDto {

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

    @Size(max = 30, message = "30자 이하로 입력해주세요")
    private String storage;

    @Range(min = 1, max = 99, message = "1 이상 99 이하의 숫자만 입력해주세요.")
    private Double distance;

    @NotBlank(message = "필수입력 항목입니다.")
    @Size(min = 50, max = 1000, message = "50자 이상 1000자 이하로 작성해주세요.")
    private String desc;

    @NotBlank(message = "필수입력 항목입니다.")
    @Size(min = 50, max = 1000, message = "50자 이상 1000자 이하로 작성해주세요.")
    private String rcmndReason;

    private String imgUrl;

    @NotBlank(message = "필수입력 항목입니다.")
    private String delYn;

    private LocalDateTime createDt;

}
