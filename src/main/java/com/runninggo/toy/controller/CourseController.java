package com.runninggo.toy.controller;

import com.runninggo.toy.auth.UserDetailsImpl;
import com.runninggo.toy.domain.CommonResponseDto;
import com.runninggo.toy.service.course.CourseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.HashMap;
import java.util.Map;

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
    public CommonResponseDto insertCourse(@Valid @RequestBody InsertCourseReqDto param,
                                          @AuthenticationPrincipal UserDetailsImpl userDetails) throws Exception{
        String userId = userDetails.getUsername();
        param.setIdForJwtInfo(userId);
        return courseService.insertCourse(param);
    }

    @GetMapping
    public CommonResponseDto getAllCourse(@Valid GetAllCourseReqDto param) throws Exception {
        return courseService.getAllCourse(param);
    }

    @GetMapping("/{courseIdx}")
    public CommonResponseDto getOneCourse(@PathVariable String courseIdx) throws Exception {
        return courseService.getOneCourse(courseIdx);
    }

    @PatchMapping("/{courseIdx}")
    public CommonResponseDto patchCourse(@PathVariable String courseIdx,
                                         @Valid @RequestBody PatchCourseReqDto param,
                                         @AuthenticationPrincipal UserDetailsImpl userDetails) throws Exception{
        String userId = userDetails.getUsername();
        param.setIdForJwtInfo(userId);
        param.setIdxByPathVariable(courseIdx);
        return courseService.patchCourse(param);
    }

    /**
     * 코스 삭제 (논리)
     */
    @DeleteMapping("/{courseIdx}")
    public CommonResponseDto deleteCourse(@PathVariable String courseIdx,
                                          @AuthenticationPrincipal UserDetailsImpl userDetails) throws Exception {
        String userId = userDetails.getUsername();

        Map<String, Object> map = new HashMap<>();
        map.put("id", userId);
        map.put("idx", courseIdx);

        return courseService.deleteCourse(map);
    }



    //[참고]헤더의 토근정보 가져오는 방법
//    @GetMapping("/header")
//    public String getBearerTokenForHeader(@RequestHeader HttpHeaders headers) throws Exception {
//        log.info("headers.getFirst = {}", headers.getFirst("Authorization"));
//        return headers.getFirst("Authorization");
//    }

    //[참고]jwt에서(?) 토근정보 가져오는 방법
//    @GetMapping("/header2")
//    public String getUserNameForJwt(@AuthenticationPrincipal UserDetailsImpl userDetails) throws Exception {
//        String username = userDetails.getUsername();
//        log.info("username = {}", username);
//        return username;
//    }
}
