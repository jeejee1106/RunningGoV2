package com.runninggo.toy.controller;

import com.runninggo.toy.domain.CommonResponseDto;
import com.runninggo.toy.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.runninggo.toy.domain.JoinRequestDto.*;
import static com.runninggo.toy.domain.JoinResponseDto.*;

@Slf4j
@RestController
@RequestMapping("/join")
public class JoinController {

    private final MemberService memberService;

    public JoinController(MemberService memberService) {
        this.memberService = memberService;
    }

    //id 중복 체크
    @PostMapping("/id-check")
    public CommonResponseDto<IdCheckResDto> idCheck(@Valid idCheckReqDto param) {
        return memberService.idCheck(param);
    }

    @PostMapping("/join")
    public CommonResponseDto joinCheck(@Valid JoinReqDto param) throws Exception{
        CommonResponseDto response = memberService.insertMember(param);
        //인증메일 보내기
        memberService.sendJoinCertificationMail(param);
        return response;
    }

    @PutMapping("/mail-auth")
    public CommonResponseDto updateMailAuthZeroToOne(UpdateMailAuthReqDto param)throws Exception{
        return memberService.updateMailAuthZeroToOne(param);
    }

}