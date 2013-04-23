package de.chritte.testmvc.model.impl;

import de.chritte.testmvc.model.IBoat;

public class Boat implements IBoat {

	private String id;
	private String boatName;
	
	@Override
	public IBoat getInstance() {
		return new Boat();
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getBoatName() {
		return boatName;
	}

	@Override
	public void setBoatName(String boatName) {
		this.boatName = boatName;
	}

}
