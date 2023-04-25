package com.runninggo.toy.dao.place;

import com.runninggo.toy.dao.place.PlaceDao;
import com.runninggo.toy.domain.PlaceDto;
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
    public int postsInsert(PlaceDto placeDto) throws Exception{
        return session.insert(namespace + "postsInsert", placeDto);
    }
}
