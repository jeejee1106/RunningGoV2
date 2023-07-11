package com.runninggo.toy.dao.login;

import com.runninggo.toy.domain.login.LoginRequestDto;
import com.runninggo.toy.domain.login.LoginResponseDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 가짜(fake)
 * - 제품에는 적합하지 않지만, 실제 동작하는 구현을 제공한다.
 * - '실제 DB 대신에 메모리를 이용해서 구현'하는 것이 가짜 대역에 해당한다!
 *
 * 즉, 실제 DB에 접근하지 않고 LoginService를 테스트 하기 위한 가짜객체
 */

@Repository
@Primary
public class MemoryLoginDao implements LoginDao{

    private Map<String, Object> userMap = new HashMap<>();

    @Override
    public int login(LoginRequestDto.LoginReqDto param) throws Exception {
        userMap.put("id", "11111");
        userMap.put("pass", "test123");
        if (param.getId().equals(userMap.get("id")) && param.getPass().equals(userMap.get("pass"))) {
            return 1;
        }
        return 0;
    }

    @Override
    public boolean emailAuthFail(String id) throws Exception {
        return false;
    }

    @Override
    public List<LoginResponseDto.FindIdResDto> findId(LoginRequestDto.FindIdReqDto param) throws Exception {
        return null;
    }

    @Override
    public boolean hasMember(LoginRequestDto.FindPassReqDto param) throws Exception {
        return false;
    }

    @Override
    public boolean hasMemberByUsername(String username) throws Exception {
        return false;
    }

    @Override
    public int updateRandomPass(LoginRequestDto.FindPassReqDto param) throws Exception {
        return 0;
    }

    @Override
    public String getEncPass(String id) throws Exception {
        return null;
    }
}
