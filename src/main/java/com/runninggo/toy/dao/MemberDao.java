package com.runninggo.toy.dao;

import com.runninggo.toy.domain.MemberDto;

import java.util.List;

import static com.runninggo.toy.domain.JoinRequestDto.*;

public interface MemberDao {
    int insertMember(JoinReqDto param);
    boolean isDuplicateId(String id);
    int login(MemberDto memberDto) throws Exception;
    int updateMailKey(JoinReqDto param) throws Exception;
    int updateMailAuthZeroToOne(UpdateMailAuthReqDto param) throws Exception;
    int emailAuthFail(String id) throws Exception;
    List<MemberDto> findId(MemberDto memberDto) throws Exception;
    int getFindUserResult(MemberDto memberDto) throws Exception;
    int updateRandomPass(MemberDto memberDto) throws Exception;
    String getEncPass(String id) throws Exception;
}
