package com.runninggo.toy.controller;

import com.runninggo.toy.domain.CommonResponseDto;
import com.runninggo.toy.service.course.CourseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.runninggo.toy.domain.course.CourseRequestDto.*;

@Slf4j
@RestController
@RequestMapping("/course")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping(value = "/subway", produces = "application/json; charset=utf8")
    public CommonResponseDto getSubwayInfo(String subwayName) throws Exception{
        return courseService.getSubwayInfo(subwayName);
    }

    @PostMapping
    public CommonResponseDto insertCourse(@Valid InsertCourseReqDto param) throws Exception{
        return courseService.insertCourse(param);
    }

    @GetMapping
    public CommonResponseDto getCourse(@Valid GetCourseReqDto param) throws Exception {
        return courseService.getCourse(param);
    }
}
