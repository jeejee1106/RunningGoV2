package com.runninggo.toy.dao.login;

import com.runninggo.toy.domain.LoginRequestDto;
import com.runninggo.toy.domain.LoginResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
public class LoginDaoImpl implements LoginDao {

    private SqlSession session;
    private static String namespace = "com.runninggo.toy.dao.MemberMapper.";

    public LoginDaoImpl(SqlSession session) {
        this.session = session;
    }


    @Override
    public int login(LoginRequestDto.LoginReqDto param) throws Exception{
        return session.selectOne(namespace + "login", param);
    }

    @Override
    public boolean emailAuthFail(String id) throws Exception {
        boolean emailAuthCheck = true;
        int count = session.selectOne(namespace + "emailAuthFail", id);

        if (count == 0) {
            emailAuthCheck = false;
        }

        return emailAuthCheck;
    }

    @Override
    public List<LoginResponseDto.FindIdResDto> findId(LoginRequestDto.FindIdReqDto param) throws Exception {
        return session.selectList(namespace + "findId", param);
    }

    @Override
    public boolean hasMember(LoginRequestDto.FindPassReqDto param) throws Exception {
        boolean hasMember = true;
        int count = session.selectOne(namespace + "hasMember", param);

        if (count == 0) {
            hasMember = false;
        }
        return hasMember;
    }

    @Override
    public int updateRandomPass(LoginRequestDto.FindPassReqDto param) throws Exception {
        return session.update(namespace + "updateRandomPass", param);
    }

    @Override
    public String getEncPass(String id) throws Exception {
        return session.selectOne(namespace + "getEncPass", id);
    }

}