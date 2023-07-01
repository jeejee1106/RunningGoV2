package com.runninggo.toy.service;

import com.runninggo.toy.domain.CommonResponseDto;
import com.runninggo.toy.service.login.LoginService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static com.runninggo.toy.domain.login.LoginRequestDto.*;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class LoginServiceTest {

    private final LoginService loginService;

    private MockMvc mockMvc;

    @Autowired
    public LoginServiceTest(LoginService loginService) {
        this.loginService = loginService;
    }

    @Test
    @DisplayName("로그인성공")
    public void loginSuccessTest() throws Exception{
        //given - 이런 상황에서
        LoginReqDto test = new LoginReqDto("11111", "PnlrDhdjxiHiJxj");

        //when - 이 액션을 취하면
        CommonResponseDto login = loginService.login(test);

//        mockMvc.perform();
        //then - 이런 결과가 나와야해
        assertThat(login.getReturnCode()).isEqualTo("0000");
    }

    @Test
    @DisplayName("로그인실패_id_pass불일치")
    public void loginFailTest_Id() throws Exception{
        //given - 이런 상황에서
        LoginReqDto test = new LoginReqDto("22222", "PnlrDhdjxiHiJxj");

        //when - 이 액션을 취하면
        CommonResponseDto login = loginService.login(test);

        //then - 이런 결과가 나와야해
        assertThat(login.getReturnCode()).isEqualTo("9999");
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