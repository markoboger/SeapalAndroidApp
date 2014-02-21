package de.htwg.seapal.events.boat;

import java.util.UUID;

/**
 * Created by jakub on 2/21/14.
 */
public class BoatFavoredEvent {
    private final UUID uuid;

    public BoatFavoredEvent(UUID uuid) {
       this.uuid = uuid;
    }

    public UUID getUuid() {
        return uuid;
    }
}
