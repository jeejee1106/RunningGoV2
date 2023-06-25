package com.runninggo.toy.service.login;

import com.runninggo.toy.domain.CommonResponseDto;
import com.runninggo.toy.domain.login.LoginRequestDto;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public interface LoginService {
    CommonResponseDto login(LoginRequestDto.LoginReqDto param) throws Exception;
    boolean emailAuthFail(String id) throws Exception;
    CommonResponseDto logout(HttpSession session) throws Exception;
    CommonResponseDto findId(LoginRequestDto.FindIdReqDto param) throws Exception;
    CommonResponseDto findPass(LoginRequestDto.FindPassReqDto param) throws Exception;
    boolean hasMember(LoginRequestDto.FindPassReqDto param) throws Exception;
}
