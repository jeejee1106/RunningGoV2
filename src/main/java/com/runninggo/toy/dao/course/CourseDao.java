package com.runninggo.toy.dao.course;

import java.util.List;

import static com.runninggo.toy.domain.course.CourseRequestDto.*;
import static com.runninggo.toy.domain.course.CourseResponseDto.*;

public interface CourseDao {
    int insertCourse(InsertCourseReqDto param) throws Exception;
    List<GetCourseResDto> getAllCourse(GetAllCourseReqDto param) throws Exception;
    int getCourseTotalCount(GetAllCourseReqDto param) throws Exception;
}
