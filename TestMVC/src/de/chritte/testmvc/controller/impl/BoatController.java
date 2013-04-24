package de.chritte.testmvc.controller.impl;

import java.util.UUID;

import de.chritte.testmvc.controller.IBoatController;
import de.chritte.testmvc.model.IBoat;
import de.chritte.testmvc.model.impl.Boat;
import de.chritte.testmvc.observer.Event;
import de.chritte.testmvc.observer.IObserver;
import de.chritte.testmvc.observer.Observable;

public class BoatController extends Observable implements IBoatController {

	private IBoat boat;
	
	public BoatController() {
		boat = new Boat();
		boat.setId(UUID.randomUUID());
		boat.setBoatName("Black Pearl");
	}
	
	@Override
	public String getBoatName(UUID id) {
		return boat.getBoatName();
	}

	@Override
	public void setBoatName(UUID id, String bootsname) {
		boat.setBoatName(bootsname);
		notifyObservers();
	}

}
