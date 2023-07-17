package com.runninggo.toy.service;

import com.runninggo.toy.dao.login.MemoryLoginDao;
import com.runninggo.toy.domain.CommonResponseDto;
import com.runninggo.toy.service.login.LoginService;
import com.runninggo.toy.service.login.LoginServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.test.web.servlet.MockMvc;

import static com.runninggo.toy.domain.login.LoginRequestDto.*;
import static org.assertj.core.api.Assertions.assertThat;

//@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class LoginServiceTest {

    @Mock
    private MessageSourceAccessor messageSourceAccessor; //오호.. 얘에서 NPE떠서 추가했더니 다른 필드에서 NPE뜸.. 의존관계를 다 이렇게 Mock으로 만들어줘야해?? 더 알아보기..
    @Mock //객체를 만들어 반환
    private MemoryLoginDao loginDao;
    @InjectMocks //@Mock이나 @Spy 객체가 자신의 필드에 있는 클래스와 일치하면 주입
    private LoginServiceImpl loginService;

    /*
       예를 들면 MemberService 안에 MemberRepository 가 있을 때
       MemberRepository가 주입받고 난 후에 MemberService 가 생성되어야 하는데 이때 사용에 용이
     */

//    @Autowired
//    public LoginServiceTest(LoginService loginService, MemoryLoginDao loginDao) {
//        this.loginService = loginService;
//        this.loginDao = loginDao;
//    }

    LoginReqDto test = new LoginReqDto("11111", "test123");

//    @BeforeEach
//    void setup() {
//        MockitoAnnotations.initMocks(this);
//        loginService = new LoginServiceImpl();
//    }

    @Test
    @DisplayName("로그인성공")
    public void loginSuccessTest() throws Exception{
        //given - 이런 상황에서

        //when - 이 액션을 취하면
//        CommonResponseDto result = loginService.login(test);
//        BDDMockito
//                .given(loginService.login(test))
//                .willReturn(result);


        //then - 이런 결과가 나와야해
//        Assertions.assertEquals("0000", result.getReturnCode());


        //given - 이런 상황에서
        LoginReqDto test = new LoginReqDto("11111", "PnlrDhdjxiHiJxj");

        //when - 이 액션을 취하면
        CommonResponseDto login = loginService.login(test);

        //then - 이런 결과가 나와야해
        assertThat(login.getReturnCode()).isEqualTo("0000");
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