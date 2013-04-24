package de.chritte.testmvc.controller.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import de.chritte.testmvc.controller.IBoatController;
import de.chritte.testmvc.model.IBoat;
import de.chritte.testmvc.model.impl.Boat;
import de.chritte.testmvc.observer.Observable;

public class BoatController extends Observable implements IBoatController {

	private IBoat boat;
	private IBoat boat2;
	
	public BoatController() {
		boat = new Boat();
		boat.setBoatName("Black Pearl");
		
		boat2 = new Boat();
		boat2.setId(UUID.randomUUID());
		boat2.setBoatName("SchneidisSegelBoot");
		
	}
	
	@Override
	public String getBoatName(UUID id) {
		return boat.getBoatName() + "\n" +  boat2.getBoatName();
	}

	@Override
	public void setBoatName(UUID id, String bootsname) {
		boat.setBoatName(bootsname);
		notifyObservers();
	}

	@Override
	public List<IBoat> getBoats() {
		List<IBoat> list = new ArrayList<IBoat>();
		list.add(boat);
		list.add(boat2);
		return list;
	}

}
