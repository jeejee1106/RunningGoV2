package com.runninggo.toy.dao.login;

import com.runninggo.toy.domain.LoginRequestDto;
import com.runninggo.toy.domain.LoginResponseDto;

import java.util.List;

public interface LoginDao {
    int login(LoginRequestDto.LoginReqDto param) throws Exception;
    boolean emailAuthFail(String id) throws Exception;
    List<LoginResponseDto.FindIdResDto> findId(LoginRequestDto.FindIdReqDto param) throws Exception;
//    int getFindUserResult(MemberDto memberDto) throws Exception;
//    int updateRandomPass(MemberDto memberDto) throws Exception;
    String getEncPass(String id) throws Exception;
}
