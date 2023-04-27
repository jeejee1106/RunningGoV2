package com.runninggo.toy.controller;

import com.runninggo.toy.domain.CommonResponseDto;
import com.runninggo.toy.domain.LoginRequestDto;
import com.runninggo.toy.service.login.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

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
    @PostMapping("/login")
    public CommonResponseDto login(@Valid LoginRequestDto.LoginReqDto param, boolean saveId,
                                   HttpServletResponse httpServletResponse, HttpSession session) throws Exception {
        return loginService.login(param, saveId, httpServletResponse, session);
    }

//    @GetMapping("/logout")
//    public String logout(HttpSession session) {
//        session.invalidate(); //세션 종료
//        return "redirect:/";
//    }
//
//    @PostMapping("/findId")
//    public String findId(MemberDto memberDto, Model model) throws Exception{
//        List<MemberDto> idList = loginService.findId(memberDto);
//        model.addAttribute("idList", idList);
//        return "/member/findIdResult";
//    }
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
