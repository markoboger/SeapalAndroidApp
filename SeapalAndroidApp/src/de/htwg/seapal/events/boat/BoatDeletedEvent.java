package de.htwg.seapal.events.boat;

/**
 * Created by jakub on 2/21/14.
 */
public class BoatDeletedEvent {
    private long  currentPosition;
    public BoatDeletedEvent(long mCurrentPosition) {
        this.currentPosition = mCurrentPosition;

    }

    public long getCurrentPosition() {
        return currentPosition;
    }
}
