package de.htwg.seapal.events.boat;

import de.htwg.seapal.model.impl.Boat;

/**
 * Created by jakub on 2/21/14.
 */
public class OnUpdateBoatView {
    private Boat boat;

    public OnUpdateBoatView(Boat boat) {
        this.boat = boat;
    }

    public Boat getBoat() {
        return boat;
    }
}
