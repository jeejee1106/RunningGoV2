package com.runninggo.toy.dao.login;

import com.runninggo.toy.domain.LoginRequestDto;
import com.runninggo.toy.domain.LoginResponseDto;

import java.util.List;

public interface LoginDao {
    int login(LoginRequestDto.LoginReqDto param) throws Exception;
    boolean emailAuthFail(String id) throws Exception;
    List<LoginResponseDto.FindIdResDto> findId(LoginRequestDto.FindIdReqDto param) throws Exception;
    boolean hasMember(LoginRequestDto.FindPassReqDto param) throws Exception;
    int updateRandomPass(LoginRequestDto.FindPassReqDto param) throws Exception;
    String getEncPass(String id) throws Exception;
}
