package de.htwg.seapal.events.trip;

import java.util.UUID;

/**
 * Created by jakub on 2/21/14.
 */
public class OnUpdateTripListEvent {

    private int position;

    private UUID boatUUID;

    public OnUpdateTripListEvent(int position, UUID boatUUID) {
        this.position = position;
        this.boatUUID = boatUUID;
    }

    public int getPosition() {
        return position;
    }

    public UUID getBoatUUID() {
        return boatUUID;
    }
}
