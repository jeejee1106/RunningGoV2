package com.runninggo.toy.controller;

import com.runninggo.toy.domain.MemberDto;
import com.runninggo.toy.service.login.LoginService;
import com.runninggo.toy.validator.LoginCheckValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/login")
public class LoginController {

    LoginService loginService;
    LoginCheckValidator loginCheckValidator;

    public LoginController(LoginService loginService, LoginCheckValidator loginCheckValidator) {
        this.loginService = loginService;
        this.loginCheckValidator = loginCheckValidator;
    }

    @InitBinder
    public void validator(WebDataBinder binder) {
        binder.setValidator(loginCheckValidator);
    }

    @PostMapping("/login")
    public String login(@Valid MemberDto memberDto, Errors errors, boolean saveId,
                        HttpServletResponse response, HttpSession session) throws Exception {

        if (errors.hasErrors()) {
            return "/member/loginForm";
        }

        //이메일 인증 했는지 확인
        if (loginService.emailAuthFail(memberDto.getId()) != 1) {
            return "/member/emailAuthFail";
        }

        //id, pass가 일치하고, 이메일 인증 했으면 세션을 생성하고, saveId 값을 체크해서 쿠키를 만들거나 삭제한다.
        session.setAttribute("id", memberDto.getId());
        session.setAttribute("loginOK", "yes");

        Cookie cookie = new Cookie("id", memberDto.getId()); //1. 쿠키생성
        if (saveId){
            response.addCookie(cookie); //2. 응답에 저장
        } else {
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }
        return "redirect:/"; //3. 홈으로 이동
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); //세션 종료
        return "redirect:/";
    }

    @PostMapping("/findId")
    public String findId(MemberDto memberDto, Model model) throws Exception{
        List<MemberDto> idList = loginService.findId(memberDto);
        model.addAttribute("idList", idList);
        return "/member/findIdResult";
    }

    @PostMapping("/findPass")
    public String findPass(MemberDto memberDto, Model model) throws Exception {
        int count = loginService.getFindUserResult(memberDto);
        log.info("LoginController.findPass : count={}", count);
        model.addAttribute("count", count);

        loginService.findPass(memberDto);
        return "/member/findPassResult";
    }
}
