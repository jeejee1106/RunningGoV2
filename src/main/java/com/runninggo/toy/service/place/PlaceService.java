package com.runninggo.toy.service.place;

import com.runninggo.toy.domain.place.PlaceRequestDto;

import java.util.List;

public interface PlaceService {
    List<String> getSubwayInfo(String subwayName) throws Exception;
    void postsInsert(PlaceRequestDto placeDto) throws Exception;
}
