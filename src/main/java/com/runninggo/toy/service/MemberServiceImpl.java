package com.runninggo.toy.service;

import com.runninggo.toy.constant.MessageConstant;
import com.runninggo.toy.dao.MemberDao;
import com.runninggo.toy.domain.CommonResponseDto;
import com.runninggo.toy.domain.JoinRequestDto;
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
import java.util.List;

import static com.runninggo.toy.constant.MessageConstant.*;
import static com.runninggo.toy.domain.JoinResponseDto.*;

@Slf4j
@Service
public class MemberServiceImpl implements MemberService{

    private final MemberDao memberDao;
    private final JavaMailSender javaMailSender;
    private final BCryptPasswordEncoder passwordEncoder;
    private final MyInfo myInfo;
    private final MessageSourceAccessor messageSource;

    public MemberServiceImpl(MemberDao memberDao, JavaMailSender javaMailSender,
                             BCryptPasswordEncoder passwordEncoder, MyInfo myInfo,
                             MessageSourceAccessor messageSource) {
        this.memberDao = memberDao;
        this.javaMailSender = javaMailSender;
        this.passwordEncoder = passwordEncoder;
        this.myInfo = myInfo;
        this.messageSource = messageSource;
    }

    public String messageSource(String messageCode) {
        return messageSource.getMessage(messageCode);
    }

    public boolean isDuplicateId(String id) {
        return memberDao.isDuplicateId(id);
    }

    @Override
    public CommonResponseDto<IdCheckResDto> idCheck(String id) {
        CommonResponseDto<IdCheckResDto> response = new CommonResponseDto<>(messageSource(SUCCESS_CODE), messageSource(ID_SUCCESS));
        boolean isDuplicateId = isDuplicateId(id);

        if (isDuplicateId) {
            response.setReturnCode(messageSource(FAIL_CODE));
            response.setMessage(messageSource(ID_DUPLICATE));
        }

        response.setResult(new IdCheckResDto(isDuplicateId));
        return response;
    }

    @Transactional
    @Override
    public CommonResponseDto insertMember(JoinRequestDto.JoinReqDto param) throws Exception {

        //아이디 중복체크
        if (isDuplicateId(param.getId())) {
            throw new IllegalArgumentException(messageSource(ID_DUPLICATE));
        }

        //비밀번호를 암호화해서 넣어주기
        String encPassword = passwordEncoder.encode(param.getPass());
        param.setEncodePass(encPassword);

        //회원가입 시 필요한 메일키 계산해서 param에 넣어주기
        param.InsertMailKey();

        //회원가입
        memberDao.insertMember(param);

        CommonResponseDto response = new CommonResponseDto();
        response.setReturnCode(messageSource(SUCCESS_CODE));
        response.setMessage(messageSource(SUCCESS));

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
