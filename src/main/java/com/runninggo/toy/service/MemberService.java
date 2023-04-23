package com.runninggo.toy.service;

import com.runninggo.toy.domain.CommonResponseDto;
import com.runninggo.toy.domain.JoinRequestDto;
import com.runninggo.toy.domain.JoinResponseDto;
import com.runninggo.toy.domain.MemberDto;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public interface MemberService {
    void sendJoinCertificationMail(JoinRequestDto.JoinReqDto param) throws MessagingException, UnsupportedEncodingException;
    int insertMember(JoinRequestDto.JoinReqDto param) throws Exception;
    CommonResponseDto<JoinResponseDto.IdCheckResDto> idCheck(String id);
    int login(MemberDto memberDto) throws Exception;
    int updateMailAuth(JoinRequestDto.UpdateMailAuthReqDto param) throws Exception;
    int emailAuthFail(String id) throws Exception;
    List<MemberDto> findId(MemberDto memberDto) throws Exception;
    void findPass(MemberDto memberDto) throws Exception;
    int getFindUserResult(MemberDto memberDto) throws Exception;
}
