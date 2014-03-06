package de.htwg.seapal.database.impl.views.account;

import com.couchbase.lite.Emitter;
import com.couchbase.lite.Mapper;

import java.util.Map;

/**
 * Created by jakub on 2/18/14.
 */
public class ByEmailView implements Mapper {
    @Override
    public void map(Map<String, Object> stringObjectMap, Emitter emitter) {
        Object email = stringObjectMap.get("email");
        if(email != null) {
            emitter.emit(email.toString(), stringObjectMap);
        }
    }
}
