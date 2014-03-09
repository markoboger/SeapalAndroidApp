package de.htwg.seapal.events.boat;

/**
 * Created by jakub on 2/24/14.
 */
public class RequestSelectedPackedPosition {
    private Long packedPosition;

    public Long getPackedPosition() {
        return packedPosition;
    }

    public void setPackedPosition(Long boat) {
        this.packedPosition = boat;
    }
}
