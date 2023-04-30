package com.runninggo.toy.dao.join;

import static com.runninggo.toy.domain.join.JoinRequestDto.JoinReqDto;
import static com.runninggo.toy.domain.join.JoinRequestDto.UpdateMailAuthReqDto;

public interface JoinDao {
    int insertMember(JoinReqDto param);
    boolean isDuplicateId(String id);
    int updateMailAuthZeroToOne(UpdateMailAuthReqDto param) throws Exception;
}
