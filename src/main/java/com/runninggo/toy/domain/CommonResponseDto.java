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
    private List<T> resultList;
    private T result;

}
