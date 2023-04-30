package com.runninggo.toy.controller;

import com.runninggo.toy.domain.CommonResponseDto;
import com.runninggo.toy.service.join.JoinService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.runninggo.toy.domain.join.JoinRequestDto.*;
import static com.runninggo.toy.domain.join.JoinResponseDto.*;

@Slf4j
@RestController
@RequestMapping("/join")
public class JoinController {

    private final JoinService joinService;

    public JoinController(JoinService joinService) {
        this.joinService = joinService;
    }

    //id 중복 체크
    @PostMapping("/id-check")
    public CommonResponseDto<IdCheckResDto> idCheck(@Valid idCheckReqDto param) {
        return joinService.idCheck(param);
    }

    @PostMapping()
    public CommonResponseDto joinCheck(@Valid JoinReqDto param) throws Exception{
        CommonResponseDto response = joinService.insertMember(param);
        return response;
    }

    @PutMapping("/mail-auth")
    public CommonResponseDto updateMailAuthZeroToOne(@Valid UpdateMailAuthReqDto param)throws Exception{
        return joinService.updateMailAuthZeroToOne(param);
    }

}