package com.runninggo.toy.controller;

import com.runninggo.toy.domain.CommonResponseDto;
import com.runninggo.toy.service.login.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static com.runninggo.toy.domain.login.LoginRequestDto.*;

@Slf4j
@RestController
@RequestMapping("/login")
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping()
    public CommonResponseDto login(@Valid @RequestBody LoginReqDto param, boolean saveId,
                                   HttpServletResponse httpServletResponse, HttpSession session) throws Exception {
        return loginService.login(param, saveId, httpServletResponse, session);
    }

    @GetMapping("/logout")
    public CommonResponseDto logout(HttpSession session) throws Exception {
        return loginService.logout(session);
    }

    @PostMapping("/findId")
    public CommonResponseDto findId(@Valid @RequestBody FindIdReqDto param) throws Exception{
        return loginService.findId(param);
    }

    @PostMapping("/findPass")
    public CommonResponseDto findPass(@Valid @RequestBody FindPassReqDto param) throws Exception {
        return loginService.findPass(param);
    }
}
