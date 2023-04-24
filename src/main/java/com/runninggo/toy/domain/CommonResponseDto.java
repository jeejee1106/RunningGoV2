package com.runninggo.toy.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommonResponseDto<T> {

    private String returnCode;
    private String message;
    private T result;
    private List<T> resultList;

    public CommonResponseDto() {}

    public CommonResponseDto(String returnCode, String message) {
        this.returnCode = returnCode;
        this.message = message;
    }

    public CommonResponseDto(T result) {
        this.result = result;
    }

    public CommonResponseDto(List<T> resultList) {
        this.resultList = resultList;
    }
}
