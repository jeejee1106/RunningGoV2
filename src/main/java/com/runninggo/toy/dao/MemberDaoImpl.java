package com.runninggo.toy.dao;

import com.runninggo.toy.domain.JoinResponseDto;
import com.runninggo.toy.domain.MemberDto;
import com.runninggo.toy.domain.JoinRequestDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MemberDaoImpl implements MemberDao{

    private SqlSession session;
    private static String namespace = "com.runninggo.toy.dao.MemberMapper.";

    public MemberDaoImpl(SqlSession session) {
        this.session = session;
    }

    @Override
    public int insertMember(JoinRequestDto.JoinReqDto param) {
        return session.insert(namespace + "insertMember", param);
    }

    @Override
    public boolean isDuplicateId(String id) {
        int count = session.selectOne(namespace + "idCheck", id);
        boolean isDuplicateId = true;

        if (count == 0) {
            isDuplicateId = false;
        }

        return isDuplicateId;
    }

    @Override
    public int login(MemberDto memberDto) throws Exception{
        return session.selectOne(namespace + "login", memberDto);
    }

    @Override
    public int updateMailKey(JoinRequestDto.JoinReqDto param) throws Exception {
        return session.update(namespace + "updateMailKey", param);
    }

    @Override
    public int updateMailAuth(JoinRequestDto.UpdateMailAuthReqDto param) throws Exception {
        return session.update(namespace + "updateMailAuth", param);
    }

    @Override
    public int emailAuthFail(String id) throws Exception {
        return session.selectOne(namespace + "emailAuthFail", id);
    }

    @Override
    public List<MemberDto> findId(MemberDto memberDto) throws Exception {
        return session.selectList(namespace + "findId", memberDto);
    }

    @Override
    public int getFindUserResult(MemberDto memberDto) throws Exception {
        return session.selectOne(namespace + "getFindUserResult", memberDto);
    }

    @Override
    public int updateRandomPass(MemberDto memberDto) throws Exception {
        return session.update(namespace + "updateRandomPass", memberDto);
    }

    @Override
    public String getEncPass(String id) throws Exception {
        return session.selectOne(namespace + "getEncPass", id);
    }

}
