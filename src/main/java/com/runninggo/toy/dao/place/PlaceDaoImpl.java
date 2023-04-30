package com.runninggo.toy.dao.place;

import com.runninggo.toy.domain.place.PlaceRequestDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

@Repository
public class PlaceDaoImpl implements PlaceDao {

    private SqlSession session;
    private static String namespace = "com.runninggo.toy.dao.PlaceMapper.";

    public PlaceDaoImpl(SqlSession session) {
        this.session = session;
    }

    @Override
    public int postsInsert(PlaceRequestDto placeDto) throws Exception{
        return session.insert(namespace + "postsInsert", placeDto);
    }
}
