package com.runninggo.toy.service;

import com.runninggo.toy.domain.MemberDto;
import com.runninggo.toy.domain.MemberRequestDto;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public interface MemberService {
    void sendJoinCertificationMail(MemberRequestDto.JoinReqDto param) throws MessagingException, UnsupportedEncodingException;
    int insertMember(MemberRequestDto.JoinReqDto param) throws Exception;
    int idCheck(String id);
    int login(MemberDto memberDto) throws Exception;
    int updateMailAuth(MemberRequestDto.UpdateMailAuthReqDto param) throws Exception;
    int emailAuthFail(String id) throws Exception;
    List<MemberDto> findId(MemberDto memberDto) throws Exception;
    void findPass(MemberDto memberDto) throws Exception;
    int getFindUserResult(MemberDto memberDto) throws Exception;
}
