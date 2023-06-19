package com.runninggo.toy.service.course;

import com.runninggo.toy.domain.CommonResponseDto;

import static com.runninggo.toy.domain.course.CourseRequestDto.*;

public interface CourseService {
    CommonResponseDto getSubwayInfo(String subwayName) throws Exception;
    CommonResponseDto insertCourse(InsertCourseReqDto param) throws Exception;
    CommonResponseDto getAllCourse(GetAllCourseReqDto param) throws Exception;
}
