package de.chritte.testmvc.model.impl;

import java.util.UUID;

import de.chritte.testmvc.model.IBoat;

public class Boat implements IBoat {

	private UUID id;
	private String boatName;
	
	@Override
	public IBoat getInstance() {
		return new Boat();
	}

	@Override
	public UUID getId() {
		return id;
	}

	@Override
	public void setId(UUID id) {
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
