package com.runninggo.toy.dao.course;

import com.runninggo.toy.domain.CommonResponseDto;
import com.runninggo.toy.domain.course.CourseResponseDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.runninggo.toy.domain.course.CourseRequestDto.*;
import static com.runninggo.toy.domain.course.CourseResponseDto.*;

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

    @Override
    public List<GetCourseResDto> getAllCourse(GetAllCourseReqDto param) throws Exception {
        return session.selectList(namespace + "getAllCourse", param);
    }

    @Override
    public int getCourseTotalCount(GetAllCourseReqDto param) throws Exception {
        return session.selectOne(namespace + "getCourseTotalCount", param);
    }

    @Override
    public CourseResponseDto.GetCourseResDto getOneCourse(String courseIdx) throws Exception {
        return session.selectOne(namespace + "getOneCourse", courseIdx);
    }
}
