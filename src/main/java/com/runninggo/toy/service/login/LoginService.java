package com.runninggo.toy.service.login;

import com.runninggo.toy.domain.CommonResponseDto;
import com.runninggo.toy.domain.LoginRequestDto;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public interface LoginService {
    CommonResponseDto login(LoginRequestDto.LoginReqDto param, boolean saveId,
                            HttpServletResponse httpServletResponse, HttpSession session) throws Exception;
    boolean emailAuthFail(String id) throws Exception;
    CommonResponseDto logout(HttpSession session) throws Exception;
//    List<MemberDto> findId(MemberDto memberDto) throws Exception;
//    void findPass(MemberDto memberDto) throws Exception;
//    int getFindUserResult(MemberDto memberDto) throws Exception;
}
