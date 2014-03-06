package de.htwg.seapal.events.boat;

import de.htwg.seapal.model.IBoat;

/**
 * Created by jakub on 2/21/14.
 */
public class RequestBoatViewInformation {

    private IBoat boat;

    public RequestBoatViewInformation(){

    }

    public void setBoat(IBoat boat) {
        this.boat = boat;
    }

    public IBoat getBoat() {
        return boat;
    }
}
