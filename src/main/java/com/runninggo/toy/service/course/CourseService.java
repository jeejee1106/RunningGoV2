package com.runninggo.toy.service.course;

import com.runninggo.toy.domain.CommonResponseDto;

import java.util.Map;

import static com.runninggo.toy.domain.course.CourseRequestDto.*;

public interface CourseService {
    CommonResponseDto getSubwayInfo(String subwayName) throws Exception;
    CommonResponseDto insertCourse(InsertCourseReqDto param) throws Exception;
    CommonResponseDto getAllCourse(GetAllCourseReqDto param) throws Exception;
    CommonResponseDto getOneCourse(String courseIdx) throws Exception;
    CommonResponseDto patchCourse(PatchCourseReqDto param) throws Exception;
    CommonResponseDto deleteCourse(Map<String, Object> map) throws Exception;
}
