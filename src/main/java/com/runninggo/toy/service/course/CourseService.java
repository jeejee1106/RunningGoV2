package com.runninggo.toy.service.course;

import com.runninggo.toy.domain.CommonResponseDto;

import java.util.List;

import static com.runninggo.toy.domain.course.CourseRequestDto.*;

public interface CourseService {
    List<String> getSubwayInfo(String subwayName) throws Exception;
    CommonResponseDto insertCourse(InsertCourseReqDto param) throws Exception;
}
