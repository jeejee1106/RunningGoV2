package com.runninggo.toy.controller;

import com.runninggo.toy.domain.CommonResponseDto;
import com.runninggo.toy.domain.JoinRequestDto;
import com.runninggo.toy.domain.JoinResponseDto;
import com.runninggo.toy.service.MemberService;
import com.runninggo.toy.validator.JoinCkValidator;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import static com.runninggo.toy.domain.JoinResponseDto.*;

@Slf4j
@Controller
@RequestMapping("/join")
public class JoinController {

    MemberService memberService;
//    JoinCkValidator joinCkValidator;

    public JoinController(MemberService memberService) {
        this.memberService = memberService;
//        this.joinCkValidator = joinCkValidator;
    }

    //추후 기능 정리 끝나면 지우기
//    @InitBinder
//    public void validator(WebDataBinder binder) {
//        binder.setValidator(joinCkValidator);
//    }

    @ResponseBody
    @PostMapping("/join")
    public CommonResponseDto joinCheck(@Valid JoinRequestDto.JoinReqDto param) throws Exception{

        CommonResponseDto response = memberService.insertMember(param);

        //인증메일 보내기
        memberService.sendJoinCertificationMail(param);

        return response;
    }

    @ResponseBody
    @PutMapping("/mail-auth")
    public CommonResponseDto updateMailAuthZeroToOne(JoinRequestDto.UpdateMailAuthReqDto param)throws Exception{
        CommonResponseDto response = memberService.updateMailAuthZeroToOne(param);
        return response;
    }

    //id 중복 체크
    @ResponseBody
    @PostMapping("/id-check")
    public CommonResponseDto<IdCheckResDto> idCheck(@Valid JoinRequestDto.idCheckReqDto param) {
        return memberService.idCheck(param);
    }

}
