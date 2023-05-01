package com.runninggo.toy.controller;

import com.runninggo.toy.domain.course.CourseRequestDto;

import com.runninggo.toy.service.course.CourseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/course")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping()
    public void InsertRecommendCoursePosts(@Valid CourseRequestDto param) throws Exception{

        if(param.getDistance() == null){
            param.setDistance(0.0);
        }
        courseService.postsInsert(param);
//        return "/place/recmndForm";
    }

    @ResponseBody
    @GetMapping(value = "/subway_search", produces = "application/json; charset=utf8")
    public List<String> getSubwayInfo(String subwayName) throws Exception{
        return courseService.getSubwayInfo(subwayName);
    }
}
