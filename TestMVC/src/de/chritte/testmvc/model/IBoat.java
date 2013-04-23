package de.chritte.testmvc.model;

public interface IBoat {
	
	public IBoat getInstance();
	
	public String getId();
	public void setId(String id);
	
	public String getBoatName();
	public void setBoatName(String boatName);
}
