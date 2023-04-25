package com.runninggo.toy.service.place;

import com.runninggo.toy.domain.PlaceDto;

import java.util.List;

public interface PlaceService {
    List<String> getSubwayInfo(String subwayName) throws Exception;
    void postsInsert(PlaceDto placeDto) throws Exception;
}
