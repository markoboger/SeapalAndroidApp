package de.chritte.testmvc.model;

import java.util.UUID;

public interface IBoat {
	
	public IBoat getInstance();
	
	public UUID getId();
	public void setId(UUID id);
	
	public String getBoatName();
	public void setBoatName(String boatName);
}
