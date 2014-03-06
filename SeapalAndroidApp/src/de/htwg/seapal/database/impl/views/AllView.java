package de.htwg.seapal.database.impl.views;

import com.couchbase.lite.Emitter;
import com.couchbase.lite.Mapper;

import java.util.Map;

/**
 * Created by jakub on 2/23/14.
 */
public class AllView implements Mapper {
    @Override
    public void map(Map<String, Object> stringObjectMap, Emitter emitter) {
        emitter.emit(stringObjectMap.get("_id"), stringObjectMap);


    }
}
