package com.runninggo.toy.service.login;

import com.runninggo.toy.dao.login.LoginDao;
import com.runninggo.toy.domain.CommonResponseDto;
import com.runninggo.toy.mail.MailHandler;
import com.runninggo.toy.mail.TempKey;
import com.runninggo.toy.myinfo.MyInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.List;

import static com.runninggo.toy.constant.MessageConstant.*;
import static com.runninggo.toy.domain.login.LoginRequestDto.*;
import static com.runninggo.toy.domain.login.LoginResponseDto.*;

@Slf4j
@Service
public class LoginServiceImpl implements LoginService {

    private final LoginDao loginDao;
    private final JavaMailSender javaMailSender;
    private final BCryptPasswordEncoder passwordEncoder;
    private final MyInfo myInfo;
    private final MessageSourceAccessor messageSource;

    public LoginServiceImpl(LoginDao loginDao, JavaMailSender javaMailSender,
                             BCryptPasswordEncoder passwordEncoder, MyInfo myInfo,
                             MessageSourceAccessor messageSource) {
        this.loginDao = loginDao;
        this.javaMailSender = javaMailSender;
        this.passwordEncoder = passwordEncoder;
        this.myInfo = myInfo;
        this.messageSource = messageSource;
    }

    public String messageSource(String messageCode) {
        return messageSource.getMessage(messageCode);
    }

    @Override
    public CommonResponseDto login(LoginReqDto param, boolean saveId,
                                   HttpServletResponse httpServletResponse, HttpSession session) throws Exception{
        CommonResponseDto response = new CommonResponseDto<>(messageSource(FAIL_CODE), "");

        //pass 일치하는 회원 있는지 확인
        String rawPassword = param.getPass();
        String encodedPassword = loginDao.getEncPass(param.getId());

        //입력받은 비밀번호와 암호화된 비밀번호를 비교(matches)
        if(!passwordEncoder.matches(rawPassword, encodedPassword)) {
            response.setMessage(messageSource(LOGIN_MISMATCH));
            return response;
        }

        //memberDto.setPass()에 암호화된 비밀번호 넣어주어 입력한 비밀번호가 암호화된 비번과 같게 처리.
        param.setEncodedPass(encodedPassword);
        loginDao.login(param);

        //이메일 인증 했는지 확인
        if (!emailAuthFail(param.getId())) {
            response.setMessage(messageSource(LOGIN_EMAIL_AUTH_FAIL));
            return response;
        }

        //id, pass가 일치하고, 이메일 인증 했으면 세션을 생성하고, saveId 값을 체크해서 쿠키를 만들거나 삭제한다.
        session.setAttribute("id", param.getId());
        session.setAttribute("loginOK", "yes");

        //이게 rest-api에서 로그인을 할 때 과연 필요할까??
//        Cookie cookie = new Cookie("id", param.getId()); //1. 쿠키생성
//        if (saveId){
//            httpServletResponse.addCookie(cookie); //2. 응답에 저장
//        } else {
//            cookie.setMaxAge(0);
//            httpServletResponse.addCookie(cookie);
//        }

        response.setReturnCode(messageSource(SUCCESS_CODE));
        response.setMessage(messageSource(SUCCESS));

        return response;
    }

    @Override
    public boolean emailAuthFail(String id) throws Exception {
        return loginDao.emailAuthFail(id);
    }

    @Override
    public CommonResponseDto logout(HttpSession session) throws Exception{
        session.invalidate(); //세션 종료
        return new CommonResponseDto<>(messageSource(SUCCESS_CODE), messageSource(SUCCESS));
    }

    @Override
    public CommonResponseDto findId(FindIdReqDto param) throws Exception {
        CommonResponseDto<FindIdResDto> response = new CommonResponseDto<>(messageSource(SUCCESS_CODE), messageSource(SUCCESS));
        List<FindIdResDto> list = loginDao.findId(param);
        response.setResultList(list);
        return response;
    }

    @Transactional
    @Override
    public CommonResponseDto findPass(FindPassReqDto param) throws Exception {
        log.info("MemberServiceImpl.findPass >>>>>>>>>>");
        CommonResponseDto response = new CommonResponseDto<>(messageSource(SUCCESS_CODE), messageSource(SUCCESS));

        if (hasMember(param)) {
            //랜덤문자열 생성
            String randomPass = new TempKey().getKey(15, false);

            //랜덤으로 생성된 문자열(비밀번호)을 암호화해서 넣어주기
            String encRandomPass = passwordEncoder.encode(randomPass);
            param.setEncodedRandomPass(encRandomPass);
            loginDao.updateRandomPass(param);

            //임시 비밀번호 메일 발송
            sendTemporaryPasswordMail(param, randomPass);
            
        } else {
            response.setReturnCode(messageSource(FAIL_CODE));
            response.setMessage(messageSource(FIND_PASS_MISMATCH));
        }
        return response;
    }

    @Override
    public boolean hasMember(FindPassReqDto param) throws Exception {
        return loginDao.hasMember(param);
    }

    @Async
    public void sendTemporaryPasswordMail(FindPassReqDto param, String randomPass) throws Exception {
        try {
            MailHandler sendMail = new MailHandler(javaMailSender);
            sendMail.setSubject("[RunninGo 임시비밀번호 입니다.]"); //메일제목
            sendMail.setText(
                            "<h1>RunninGo 임시비밀번호</h1>" +
                            "<br>회원님의 임시비밀번호입니다." +
                            "<br><b>" + randomPass + "</b>" +
                            "<br>로그인 후 반드시 비밀번호를 변경해주세요!!");
            sendMail.setFrom(myInfo.runningGoId, "러닝고");
            sendMail.setTo(param.getEmail());
            sendMail.send();
            log.info("비밀번호 찾기 메일 발송 성공");
        } catch (Exception e) {
            log.error("error >>>> sendTemporaryPasswordMail : {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

}
