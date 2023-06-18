package com.runninggo.toy.dao.course;

import java.util.List;

import static com.runninggo.toy.domain.course.CourseRequestDto.*;
import static com.runninggo.toy.domain.course.CourseResponseDto.*;

public interface CourseDao {
    int insertCourse(InsertCourseReqDto param) throws Exception;
    List<GetCourseResDto> getCourse(GetCourseReqDto param) throws Exception;
}
