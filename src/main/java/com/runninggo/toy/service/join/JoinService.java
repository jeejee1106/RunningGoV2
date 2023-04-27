package com.runninggo.toy.service.join;

import com.runninggo.toy.domain.CommonResponseDto;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

import static com.runninggo.toy.domain.JoinRequestDto.*;
import static com.runninggo.toy.domain.JoinResponseDto.IdCheckResDto;

public interface JoinService {
    CommonResponseDto<IdCheckResDto> idCheck(idCheckReqDto param);
    CommonResponseDto insertMember(JoinReqDto param) throws Exception;
    void sendJoinCertificationMail(JoinReqDto param) throws MessagingException, UnsupportedEncodingException;
    CommonResponseDto updateMailAuthZeroToOne(UpdateMailAuthReqDto param) throws Exception;
}
