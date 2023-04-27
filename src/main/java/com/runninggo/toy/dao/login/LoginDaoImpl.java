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
        List<LoginResponseDto.FindIdResDto> list = session.selectList(namespace + "findId", param);
//        log.info("list.size = {}", list.size());
//        log.info("list.get = {}", list.get(0));
//        log.info("list = {}", list);
//        for (LoginResponseDto.FindIdResDto findIdResDto : list) {
//            log.info("findIdResDto = {}", findIdResDto);
//        }
        return list;
    }

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
