package com.runninggo.toy.dao.course;

import static com.runninggo.toy.domain.course.CourseRequestDto.*;

public interface CourseDao {
    int insertCourse(InsertCourseReqDto param) throws Exception;
}
