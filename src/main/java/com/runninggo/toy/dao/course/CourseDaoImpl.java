package com.runninggo.toy.dao.course;

import com.runninggo.toy.domain.course.CourseRequestDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

@Repository
public class CourseDaoImpl implements CourseDao {

    private SqlSession session;
    private static String namespace = "com.runninggo.toy.dao.CourseMapper.";

    public CourseDaoImpl(SqlSession session) {
        this.session = session;
    }

    @Override
    public int postsInsert(CourseRequestDto param) throws Exception{
        return session.insert(namespace + "postsInsert", param);
    }
}
