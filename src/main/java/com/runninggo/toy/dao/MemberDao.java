package com.runninggo.toy.dao;

import com.runninggo.toy.domain.MemberDto;
import com.runninggo.toy.domain.MemberRequestDto;

import java.util.List;

public interface MemberDao {
    int insertMember(MemberRequestDto.JoinReqDto param);
    int idCheck(String id);
    int login(MemberDto memberDto) throws Exception;
    int updateMailKey(MemberRequestDto.JoinReqDto param) throws Exception;
    int updateMailAuth(MemberRequestDto.UpdateMailAuthReqDto param) throws Exception;
    int emailAuthFail(String id) throws Exception;
    List<MemberDto> findId(MemberDto memberDto) throws Exception;
    int getFindUserResult(MemberDto memberDto) throws Exception;
    int updateRandomPass(MemberDto memberDto) throws Exception;
    String getEncPass(String id) throws Exception;
}
