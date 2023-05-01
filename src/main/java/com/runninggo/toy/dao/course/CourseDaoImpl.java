package com.runninggo.toy.dao.course;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import static com.runninggo.toy.domain.course.CourseRequestDto.*;

@Repository
public class CourseDaoImpl implements CourseDao {

    private SqlSession session;
    private static String namespace = "com.runninggo.toy.dao.CourseMapper.";

    public CourseDaoImpl(SqlSession session) {
        this.session = session;
    }

    @Override
    public int insertCourse(InsertCourseReqDto param) throws Exception{
        return session.insert(namespace + "insertCourse", param);
    }
}
