package com.runninggo.toy.service.course;

import com.runninggo.toy.domain.course.CourseRequestDto;

import java.util.List;

public interface CourseService {
    List<String> getSubwayInfo(String subwayName) throws Exception;
    void postsInsert(CourseRequestDto param) throws Exception;
}
