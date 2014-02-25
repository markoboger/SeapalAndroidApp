package de.htwg.seapal.events.boat;

import de.htwg.seapal.model.IBoat;
import de.htwg.seapal.model.impl.Boat;

/**
 * Created by jakub on 2/21/14.
 */
public class BoatCreatedEvent {
    private Boat boat;
    public BoatCreatedEvent(IBoat boat) {
        this.boat = (Boat) boat;
    }

    public Boat getBoat() {
        return boat;
    }
}
