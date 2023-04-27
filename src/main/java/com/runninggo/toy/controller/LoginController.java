package com.runninggo.toy.controller;

import com.runninggo.toy.domain.CommonResponseDto;
import com.runninggo.toy.domain.JoinResponseDto;
import com.runninggo.toy.domain.LoginRequestDto;
import com.runninggo.toy.service.login.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import java.util.List;

import static com.runninggo.toy.constant.MessageConstant.FAIL_CODE;

@Slf4j
@Controller
@RequestMapping("/login")
public class LoginController {

    private final LoginService loginService;
//    private final LoginCheckValidator loginCheckValidator;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
//        this.loginCheckValidator = loginCheckValidator;
    }

//    @InitBinder
//    public void validator(WebDataBinder binder) {
//        binder.setValidator(loginCheckValidator);
//    }

    @ResponseBody
    @PostMapping()
    public CommonResponseDto login(@Valid LoginRequestDto.LoginReqDto param, boolean saveId,
                                   HttpServletResponse httpServletResponse, HttpSession session) throws Exception {
        return loginService.login(param, saveId, httpServletResponse, session);
    }

    @ResponseBody
    @GetMapping("/logout")
    public CommonResponseDto logout(HttpSession session) throws Exception {
        return loginService.logout(session);
    }

    @ResponseBody
    @PostMapping("/findId")
    public CommonResponseDto findId(LoginRequestDto.FindIdReqDto param) throws Exception{
        return loginService.findId(param);
    }
//
//    @PostMapping("/findPass")
//    public String findPass(MemberDto memberDto, Model model) throws Exception {
//        int count = loginService.getFindUserResult(memberDto);
//        log.info("LoginController.findPass : count={}", count);
//        model.addAttribute("count", count);
//
//        loginService.findPass(memberDto);
//        return "/member/findPassResult";
//    }
}
