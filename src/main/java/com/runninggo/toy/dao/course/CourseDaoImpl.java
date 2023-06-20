package com.runninggo.toy.dao.course;

import com.runninggo.toy.auth.UserDetailsImpl;
import com.runninggo.toy.domain.course.CourseResponseDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

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
    public boolean insertCourse(InsertCourseReqDto param) throws Exception{
        int count = session.insert(namespace + "insertCourse", param);
        boolean isInserted = true;

        if (count == 0) {
            isInserted = false;
        }

        return isInserted;
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

    @Override
    public boolean hasCourseByIdx(String courseIdx) throws Exception {
        int count = session.selectOne(namespace + "hasCourseByIdx", courseIdx);
        boolean hasCourse = true;

        if (count == 0) {
            hasCourse = false;
        }

        return hasCourse;
    }

    @Override
    public boolean patchCourse(PatchCourseReqDto param) throws Exception {
        int count = session.update(namespace + "patchCourse", param);
        boolean hasUpdate = true;

        if (count == 0) {
            hasUpdate = false;
        }

        return hasUpdate;
    }
}
