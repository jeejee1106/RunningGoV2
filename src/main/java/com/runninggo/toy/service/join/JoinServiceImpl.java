package com.runninggo.toy.service.join;

import com.runninggo.toy.dao.join.JoinDao;
import com.runninggo.toy.domain.CommonResponseDto;
import com.runninggo.toy.mail.MailHandler;
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

import static com.runninggo.toy.constant.MessageConstant.*;
import static com.runninggo.toy.domain.JoinRequestDto.*;
import static com.runninggo.toy.domain.JoinResponseDto.IdCheckResDto;

@Slf4j
@Service
public class JoinServiceImpl implements JoinService {

    private final JoinDao joinDao;
    private final JavaMailSender javaMailSender;
    private final BCryptPasswordEncoder passwordEncoder;
    private final MyInfo myInfo;
    private final MessageSourceAccessor messageSource;

    public JoinServiceImpl(JoinDao joinDao, JavaMailSender javaMailSender,
                           BCryptPasswordEncoder passwordEncoder, MyInfo myInfo,
                           MessageSourceAccessor messageSource) {
        this.joinDao = joinDao;
        this.javaMailSender = javaMailSender;
        this.passwordEncoder = passwordEncoder;
        this.myInfo = myInfo;
        this.messageSource = messageSource;
    }

    public String messageSource(String messageCode) {
        return messageSource.getMessage(messageCode);
    }

    public boolean isDuplicateId(String id) {
        return joinDao.isDuplicateId(id);
    }

    @Override
    public CommonResponseDto<IdCheckResDto> idCheck(idCheckReqDto param) {
        CommonResponseDto<IdCheckResDto> response = new CommonResponseDto<>(messageSource(SUCCESS_CODE), messageSource(ID_SUCCESS));
        boolean isDuplicateId = isDuplicateId(param.getId());

        if (isDuplicateId) {
            response.setReturnCode(messageSource(FAIL_CODE));
            response.setMessage(messageSource(ID_DUPLICATE));
        }

        response.setResult(new IdCheckResDto(isDuplicateId));
        return response;
    }

    @Transactional
    @Override
    public CommonResponseDto insertMember(JoinReqDto param) throws Exception {

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
        joinDao.insertMember(param);

        CommonResponseDto response = new CommonResponseDto();
        response.setReturnCode(messageSource(SUCCESS_CODE));
        response.setMessage(messageSource(SUCCESS));

        return response;
    }

    @Async
    @Override
    public void sendJoinCertificationMail(JoinReqDto param) throws MessagingException, UnsupportedEncodingException {
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
    @Transactional
    public CommonResponseDto updateMailAuthZeroToOne(UpdateMailAuthReqDto param) throws Exception {
        joinDao.updateMailAuthZeroToOne(param);

        CommonResponseDto response = new CommonResponseDto(messageSource(SUCCESS_CODE), messageSource(SUCCESS));

        return response;
    }
}
