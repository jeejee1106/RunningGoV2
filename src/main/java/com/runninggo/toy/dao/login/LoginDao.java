package com.runninggo.toy.dao.login;

import com.runninggo.toy.domain.LoginRequestDto;

public interface LoginDao {
    int login(LoginRequestDto.LoginReqDto param) throws Exception;
    int emailAuthFail(String id) throws Exception;
//    List<MemberDto> findId(MemberDto memberDto) throws Exception;
//    int getFindUserResult(MemberDto memberDto) throws Exception;
//    int updateRandomPass(MemberDto memberDto) throws Exception;
    String getEncPass(String id) throws Exception;
}
