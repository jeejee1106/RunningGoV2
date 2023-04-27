//package com.runninggo.toy.validator;
//
//import com.runninggo.toy.domain.MemberDto;
//import com.runninggo.toy.service.login.LoginService;
//import org.springframework.stereotype.Component;
//import org.springframework.validation.Errors;
//
//@Component
//public class LoginCheckValidator extends AbstractValidator<MemberDto>{
//
//    private LoginService loginService;
//
//    public LoginCheckValidator(LoginService memberService) {
//        this.loginService = memberService;
//    }
//
//    @Override
//    protected void doValidate(MemberDto memberDto, Errors errors) throws Exception {
//        //아이디와 비밀번호가 존재하는지 체크
//        if (loginService.login(memberDto) != 1) {
//            errors.reject("login.mismatch");
//        }
//    }
//}
