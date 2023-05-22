package com.runninggo.toy.auth;

import com.runninggo.toy.dao.login.LoginDao;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 스프링 시큐리티의 구조에서 인증을 담당하는 AuthenticationManager는 내부적으로 UserDetailsService를 호출해서 사용자의 정보를 가져온다.
 *
 * 스프링 시큐리티는 UserDetailsService를 이용해서 회원의 존재만을 우선적으로 가져오고, 이후에 password가 틀리면 'Bad Cridential(잘못된 자격증명)'이라는 결과를 만들어낸다.(인증)
 * 사용자의 username과 password로 인증 과정이 끝나면 원하는 자원(URL)에 접근할 수 있는 적절한 권한이 있는지를 확인하고 인가 과정을 실행한다.
 * 이 과정에서는 'Access Denied'와 같은 결과가 만들어진다.
 */

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    /**
     * loadUserByUsername()은 말 그대로 username이라는 회원 아이디와 같은 식별 값으로 회원 정보를 가져온다.
     * 메서드의 리턴 타입은 UserDetils라는 타입인데 이를 통해서 다음과 같은 정보를 알아낼 수 있도록 구성되어 있다.
     * - getAuthorities() : 사용자가 가지는 권한에 대한 정보
     * - getPassword() : 인증을 마무리하기 위한 패스워드 정보
     * - getUsername() : 인증에 필요한 아이디와 같은 정보
     * - 계정 만료 여부 : 더이상 사용이 불가능한 계정인지 알 수 있는 정보
     * - 계정 잠김 여부 : 현재 계정의 잠김 여부
     */

    private final LoginDao loginDao;

    public UserDetailsServiceImpl(LoginDao loginDao) {
        this.loginDao = loginDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //이 부분 이제 구현해야함!!!!!
        try {
            boolean hasMemberByUsername = loginDao.hasMemberByUsername(username);
            if(!hasMemberByUsername){
                throw new UsernameNotFoundException("Check Email or Social ");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }





        return null;
    }
}
