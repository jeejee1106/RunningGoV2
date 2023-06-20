package com.runninggo.toy.dao.course;

import com.runninggo.toy.domain.course.CourseResponseDto;

import java.util.List;
import java.util.Map;

import static com.runninggo.toy.domain.course.CourseRequestDto.*;
import static com.runninggo.toy.domain.course.CourseResponseDto.*;

public interface CourseDao {
    boolean insertCourse(InsertCourseReqDto param) throws Exception;
    List<GetCourseResDto> getAllCourse(GetAllCourseReqDto param) throws Exception;
    int getCourseTotalCount(GetAllCourseReqDto param) throws Exception;
    CourseResponseDto.GetCourseResDto getOneCourse(String courseIdx) throws Exception;
    boolean hasCourseByIdx(String courseIdx) throws Exception;
    boolean patchCourse(PatchCourseReqDto param) throws Exception;
    boolean deleteCourse(Map<String, Object> map) throws Exception;
}
