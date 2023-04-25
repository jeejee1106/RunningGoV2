package com.runninggo.toy.service.login;

import com.runninggo.toy.domain.MemberDto;

import java.util.List;

public interface LoginService {
    int login(MemberDto memberDto) throws Exception;
    int emailAuthFail(String id) throws Exception;
    List<MemberDto> findId(MemberDto memberDto) throws Exception;
    void findPass(MemberDto memberDto) throws Exception;
    int getFindUserResult(MemberDto memberDto) throws Exception;
}
