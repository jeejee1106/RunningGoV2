package com.runninggo.toy.service;

import com.runninggo.toy.dao.login.MemoryLoginDao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static com.runninggo.toy.domain.login.LoginRequestDto.*;

@SpringBootTest
public class LoginServiceTest {

    private final MemoryLoginDao loginDao;

    private MockMvc mockMvc;

    @Autowired
    public LoginServiceTest(MemoryLoginDao loginDao) {
        this.loginDao = loginDao;
    }

    @Test
    @DisplayName("로그인성공")
    public void loginSuccessTest() throws Exception{
        //given - 이런 상황에서
        LoginReqDto test = new LoginReqDto("11111", "test123");

        //when - 이 액션을 취하면
        int result = loginDao.login(test);

        //then - 이런 결과가 나와야해
        Assertions.assertEquals(1, result);
    }

    @Test
    @DisplayName("로그인실패_id_pass불일치")
    public void loginFailTest_Id() throws Exception{
        //given - 이런 상황에서
        LoginReqDto test = new LoginReqDto("11111", "test1234567890");

        //when - 이 액션을 취하면
        int result = loginDao.login(test);

        //then - 이런 결과가 나와야해
        Assertions.assertEquals(0, result);
    }


//    @Test
//    @DisplayName("아이디찾기성공")
//    void findIdSuccessTest() throws Exception {
//        MemberDto memberDto = findIdMemberDto("지지", "kimmj1106@naver.com", "010-1111-1111");
//        List<MemberDto> list = loginService.findId(memberDto);
//
//        System.out.println("list = " + list.size());
//        assertTrue(list.size()!=1);
//    }
//
//    @Test
//    @DisplayName("아이디찾기실패")
//    void findIdFailTest() throws Exception{
//        MemberDto memberDto = findIdMemberDto("test", "kimmj1106@naver.com", "010-1111-1111");
//        List<MemberDto> list = loginService.findId(memberDto);
//
//        System.out.println("list = " + list.size());
//        assertTrue(list.size()==0);
//    }
}