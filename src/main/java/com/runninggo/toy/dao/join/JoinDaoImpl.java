package com.runninggo.toy.dao.join;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import static com.runninggo.toy.domain.join.JoinRequestDto.JoinReqDto;
import static com.runninggo.toy.domain.join.JoinRequestDto.UpdateMailAuthReqDto;

@Repository
public class JoinDaoImpl implements JoinDao {

    private SqlSession session;
    private static String namespace = "com.runninggo.toy.dao.JoinMapper.";

    public JoinDaoImpl(SqlSession session) {
        this.session = session;
    }

    @Override
    public int insertMember(JoinReqDto param) {
        return session.insert(namespace + "insertMember", param);
    }

    @Override
    public boolean isDuplicateId(String id) {
        int count = session.selectOne(namespace + "isDuplicateId", id);
        boolean isDuplicateId = true;

        if (count == 0) {
            isDuplicateId = false;
        }

        return isDuplicateId;
    }

    @Override
    public int updateMailAuthZeroToOne(UpdateMailAuthReqDto param) throws Exception {
        return session.update(namespace + "updateMailAuthZeroToOne", param);
    }
}
