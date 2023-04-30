package com.runninggo.toy.controller;

import com.runninggo.toy.domain.place.PlaceRequestDto;
import com.runninggo.toy.service.place.PlaceService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/place")
public class PlaceController {

    private final PlaceService placeService;

    public PlaceController(PlaceService placeService) {
        this.placeService = placeService;
    }

    @PostMapping()
    public String InsertRecommendPlacePosts(@Valid PlaceRequestDto param) throws Exception{

        if(param.getDistance() == null){
            param.setDistance(0.0);
        }
        System.out.println("placeDto = " + param.getDistance());
        placeService.postsInsert(param);
        return "/place/recmndForm";
    }

    @ResponseBody
    @GetMapping(value = "/subway_search", produces = "application/json; charset=utf8")
    public List<String> getSubwayInfo(String subwayName) throws Exception{
        return placeService.getSubwayInfo(subwayName);
    }
}
