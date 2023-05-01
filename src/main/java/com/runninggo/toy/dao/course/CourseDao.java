package com.runninggo.toy.dao.course;

import com.runninggo.toy.domain.course.CourseRequestDto;

public interface CourseDao {
    int postsInsert(CourseRequestDto param) throws Exception;
}
