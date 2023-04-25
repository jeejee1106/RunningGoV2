package com.runninggo.toy.dao.place;

import com.runninggo.toy.domain.PlaceDto;

public interface PlaceDao {
    int postsInsert(PlaceDto placeDto) throws Exception;
}
