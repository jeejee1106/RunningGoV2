package com.runninggo.toy.dao.login;

import java.util.List;

import static com.runninggo.toy.domain.login.LoginRequestDto.*;
import static com.runninggo.toy.domain.login.LoginResponseDto.*;

public interface LoginDao {
    int login(LoginReqDto param) throws Exception;
    boolean emailAuthFail(String id) throws Exception;
    List<FindIdResDto> findId(FindIdReqDto param) throws Exception;
    boolean hasMember(FindPassReqDto param) throws Exception;
    int updateRandomPass(FindPassReqDto param) throws Exception;
    String getEncPass(String id) throws Exception;
}
