package com.runninggo.toy.dao.login;

import com.runninggo.toy.domain.LoginRequestDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

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

//    @Override
//    public List<MemberDto> findId(MemberDto memberDto) throws Exception {
//        return session.selectList(namespace + "findId", memberDto);
//    }
//
//    @Override
//    public int getFindUserResult(MemberDto memberDto) throws Exception {
//        return session.selectOne(namespace + "getFindUserResult", memberDto);
//    }
//
//    @Override
//    public int updateRandomPass(MemberDto memberDto) throws Exception {
//        return session.update(namespace + "updateRandomPass", memberDto);
//    }
//
    @Override
    public String getEncPass(String id) throws Exception {
        return session.selectOne(namespace + "getEncPass", id);
    }

}
