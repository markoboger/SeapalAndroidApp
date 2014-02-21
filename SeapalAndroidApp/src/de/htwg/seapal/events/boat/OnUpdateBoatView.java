package de.htwg.seapal.events.boat;

import java.util.UUID;

/**
 * Created by jakub on 2/21/14.
 */
public class OnUpdateBoatView {
    private int mPosition;
    private UUID mBoatUUid;

    public OnUpdateBoatView(int position, UUID boatUuid) {
        this.mPosition = position;
        this.mBoatUUid = boatUuid;
    }

    public int getPosition() {
        return mPosition;
    }

    public UUID getBoatUUID() {
        return mBoatUUid;
    }
}
