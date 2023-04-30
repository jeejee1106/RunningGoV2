package com.runninggo.toy.dao.place;

import com.runninggo.toy.domain.place.PlaceRequestDto;

public interface PlaceDao {
    int postsInsert(PlaceRequestDto placeDto) throws Exception;
}
