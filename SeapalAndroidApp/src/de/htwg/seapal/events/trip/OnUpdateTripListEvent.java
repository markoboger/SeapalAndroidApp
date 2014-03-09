package de.htwg.seapal.events.trip;

import de.htwg.seapal.model.impl.Boat;

/**
 * Created by jakub on 2/21/14.
 */
public class OnUpdateTripListEvent {

    private Boat boat;

    public OnUpdateTripListEvent(Boat boat) {
        this.boat = boat;
    }

    public Boat getBoat() {
        return boat;
    }
}
