package de.htwg.seapal.database.impl.views;

import com.couchbase.lite.Emitter;
import com.couchbase.lite.Mapper;

import java.util.Map;

/**
 * Created by jakub on 2/18/14.
 */
public class OwnView implements Mapper {

    @Override
    public void map(Map<String, Object> stringObjectMap, Emitter emitter) {
        Object owner = stringObjectMap.get("owner");

    }
}
