package com.runninggo.toy.validator;

import com.runninggo.toy.domain.join.JoinRequestDto;
import com.runninggo.toy.service.join.JoinService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class JoinCkValidator extends AbstractValidator<JoinRequestDto.JoinReqDto>{

    private JoinService joinService;

    public JoinCkValidator(JoinService joinService) {
        this.joinService = joinService;
    }

    @Override
    protected void doValidate(JoinRequestDto.JoinReqDto param, Errors errors) {
        //아이디 중복 체크
//        if (memberService.idCheck(param.getId()) == 1) {
//            errors.rejectValue("id", "id.duplicate"); //필드, 에러코드
//        }

        //비밀번호 일치하는지 체크
        if (!param.getPass().equals(param.getPassCheck())) {
            errors.rejectValue("passCheck", "pass.bad");
        }
    }
}
