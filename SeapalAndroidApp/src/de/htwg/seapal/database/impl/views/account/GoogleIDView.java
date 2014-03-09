package de.htwg.seapal.database.impl.views.account;

import com.couchbase.lite.Emitter;
import com.couchbase.lite.Mapper;

import java.util.Map;

/**
 * Created by jakub on 2/20/14.
 */
public class GoogleIDView implements Mapper {
    @Override
    public void map(Map<String, Object> stringObjectMap, Emitter emitter) {
        Object googleID = stringObjectMap.get("googleID");
        if (googleID != null) {
            emitter.emit(googleID, stringObjectMap);

        }
    }
}
