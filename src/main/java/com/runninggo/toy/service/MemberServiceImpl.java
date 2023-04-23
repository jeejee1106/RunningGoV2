package com.runninggo.toy.service;

import com.runninggo.toy.dao.MemberDao;
import com.runninggo.toy.domain.CommonResponseDto;
import com.runninggo.toy.domain.JoinRequestDto;
import com.runninggo.toy.domain.JoinResponseDto;
import com.runninggo.toy.domain.MemberDto;
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

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.runninggo.toy.domain.JoinResponseDto.*;

@Slf4j
@Service
public class MemberServiceImpl implements MemberService{

    MemberDao memberDao;
    JavaMailSender javaMailSender;
    BCryptPasswordEncoder passwordEncoder;
    MyInfo myInfo;
    MessageSourceAccessor messageSource;

    public MemberServiceImpl(MemberDao memberDao, JavaMailSender javaMailSender,
                             BCryptPasswordEncoder passwordEncoder, MyInfo myInfo,
                             MessageSourceAccessor messageSource) {
        this.memberDao = memberDao;
        this.javaMailSender = javaMailSender;
        this.passwordEncoder = passwordEncoder;
        this.myInfo = myInfo;
        this.messageSource = messageSource;
    }

    @Transactional
    @Override
    public CommonResponseDto insertMember(JoinRequestDto.JoinReqDto param) throws Exception {

        //아이디 중복체크
        CommonResponseDto<IdCheckResDto> idCheck = idCheck(param.getId());
        if ("9999".equals(idCheck.getReturnCode())) {
            throw new IllegalArgumentException(idCheck.getMessage());
//            CommonResponseDto response = new CommonResponseDto();
//            response.setReturnCode(idCheck.getReturnCode());
//            response.setMessage(idCheck.getMessage());
//            return response;
        }

        //비밀번호를 암호화해서 넣어주기
        String encPassword = passwordEncoder.encode(param.getPass());
        param.setEncodePass(encPassword);

        //회원가입 시 필요한 메일키 계산해서 param에 넣어주기
        param.InsertMailKey();
        //회원가입
        memberDao.insertMember(param);
        //회원가입 시 이메일 인증을 위한 메일키 db에 저장 - 위에서 param에 저장 후 insert하기 때문에 필요 없을 것 같음
//        memberDao.updateMailKey(param);

        CommonResponseDto response = new CommonResponseDto();
        response.setReturnCode(messageSource.getMessage("success.code"));
        response.setMessage(messageSource.getMessage("success"));

        return response;
    }

    @Async
    @Override
    public void sendJoinCertificationMail(JoinRequestDto.JoinReqDto param) throws MessagingException, UnsupportedEncodingException {
            //회원가입 완료하면 인증을 위한 이메일 발송
            MailHandler sendMail = new MailHandler(javaMailSender);
            sendMail.setSubject("[RunninGo 이메일 인증메일 입니다.]"); //메일제목
            sendMail.setText(
                    "<h1>RunninGo 메일인증</h1>" +
                    "<br>RunninGo에 오신것을 환영합니다!" +
                    "<br>아래 [이메일 인증 확인]을 눌러주세요." +
                    "<br><a href='http://localhost:8080/join/registerEmail?email=" + param.getEmail() +
                    "&mailKey=" + param.getMailKey() +
                    "' target='_blank'>이메일 인증 확인</a>");
            sendMail.setFrom(myInfo.runningGoId, "러닝고");
            sendMail.setTo(param.getEmail());
            sendMail.send();

            log.info("회원가입 인증 메일 발송 성공");
    }

    @Override
    public CommonResponseDto<IdCheckResDto> idCheck(String id) {
        CommonResponseDto response = new CommonResponseDto();
        int count = memberDao.idCheck(id);

        if (count != 0) {
            response.setReturnCode(messageSource.getMessage("fail.code"));
            response.setMessage(messageSource.getMessage("id.duplicate"));
        } else {
            response.setReturnCode(messageSource.getMessage("success.code"));
            response.setMessage(messageSource.getMessage("id.success"));
        }
        response.setResult(new IdCheckResDto(count));
        return response;
    }

    @Override
    public int login(MemberDto memberDto) throws Exception{

        String rawPassword = memberDto.getPass();
        String encodedPassword = memberDao.getEncPass(memberDto.getId());

        //입력받은 비밀번호와 암호화된 비밀번호를 비교(matches)해서 같지 않으면 0반환
        if(!passwordEncoder.matches(rawPassword, encodedPassword)) {
            return 0;
        } else{
            //같으면 memberDto.setPass()에 암호화된 비밀번호 넣어주어 입력한 비밀번호가 암호화된 비번과 같게 처리.
            memberDto.setPass(encodedPassword);
            return memberDao.login(memberDto);
        }
    }

    @Override
    public int updateMailAuth(JoinRequestDto.UpdateMailAuthReqDto param) throws Exception {
        return memberDao.updateMailAuth(param);
    }

    @Override
    public int emailAuthFail(String id) throws Exception {
        return memberDao.emailAuthFail(id);
    }

    @Override
    public List<MemberDto> findId(MemberDto memberDto) throws Exception {
        return memberDao.findId(memberDto);
    }

    @Async
    @Transactional
    @Override
    public void findPass(MemberDto memberDto) throws Exception {
        log.info("MemberServiceImpl.findPass >>>>>>>>>>");

        if (getFindUserResult(memberDto) == 1) {
            //랜덤문자열 생성
            String pass = new TempKey().getKey(15,false);

            //위 문자열(비밀번호)을 암호화해서 넣어주기
            String encPassword = passwordEncoder.encode(pass);

            memberDto.setPass(encPassword);
            memberDao.updateRandomPass(memberDto);

            MailHandler sendMail = new MailHandler(javaMailSender);
            sendMail.setSubject("[RunninGo 임시비밀번호 입니다.]"); //메일제목
            sendMail.setText(
                    "<h1>RunninGo 임시비밀번호</h1>" +
                            "<br>회원님의 임시비밀번호입니다."+
                            "<br><b>" + pass + "</b>"+
                            "<br>로그인 후 반드시 비밀번호를 변경해주세요!!");
            sendMail.setFrom(myInfo.runningGoId, "러닝고");
            sendMail.setTo(memberDto.getEmail());
            sendMail.send();
            log.info("비밀번호 찾기 메일 발송 성공");
        }
    }

    @Override
    public int getFindUserResult(MemberDto memberDto) throws Exception {
        return memberDao.getFindUserResult(memberDto);
    }

}
