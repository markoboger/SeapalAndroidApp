package de.htwg.seapal.database.impl.views;

import com.couchbase.lite.Emitter;
import com.couchbase.lite.Mapper;

import java.util.Map;

/**
 * Created by jakub on 2/19/14.
 */
public class SingleDocumentView  implements Mapper {
    @Override
    public void map(Map<String, Object> stringObjectMap, Emitter emitter) {
        String owner = (String) stringObjectMap.get("owner");
        String id = (String) stringObjectMap.get("_id");
        if (owner != null && id != null) {
            emitter.emit(owner.concat(id), stringObjectMap);

        }

    }
}
