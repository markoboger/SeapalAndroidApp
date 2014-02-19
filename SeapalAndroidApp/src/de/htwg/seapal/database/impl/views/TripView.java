package de.htwg.seapal.database.impl.views;

import com.couchbase.lite.Emitter;
import com.couchbase.lite.Mapper;

import java.util.Map;

/**
 * Created by jakub on 2/19/14.
 */
public class TripView  implements Mapper{
    @Override
    public void map(Map<String, Object> stringObjectMap, Emitter emitter) {
        String trip = (String) stringObjectMap.get("trip");
        String owner = (String) stringObjectMap.get("owner");
        if (trip != null && owner != null) {
            emitter.emit(owner.concat(trip), stringObjectMap);

        }



    }
}
