package com.runninggo.toy.dao.login;

import com.runninggo.toy.domain.MemberDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.runninggo.toy.domain.JoinRequestDto.*;

@Repository
public class LoginDaoImpl implements LoginDao {

    private SqlSession session;
    private static String namespace = "com.runninggo.toy.dao.MemberMapper.";

    public LoginDaoImpl(SqlSession session) {
        this.session = session;
    }


    @Override
    public int login(MemberDto memberDto) throws Exception{
        return session.selectOne(namespace + "login", memberDto);
    }

    @Override
    public int updateMailKey(JoinReqDto param) throws Exception {
        return session.update(namespace + "updateMailKey", param);
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
