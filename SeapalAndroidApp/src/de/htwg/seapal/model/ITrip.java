package de.htwg.seapal.model;


public interface ITrip extends IModel {

	String getName();

	void setName(String name);

	String getStartLocation();

	void setStartLocation(String location);

	String getEndLocation();

	void setEndLocation(String location);

	String getSkipper(); // Person

	void setSkipper(String skipper); // Person

	String getCrewMembers(); 

	void setCrewMember(String crewMember); 

	Integer getMotor();

	void setMotor(Integer motor);

	Double getFuel();

	void setFuel(Double percent);

	String getNotes();

	void setNotes(String text);

	String getBoat();

	void setBoat(String boat);

	void setStartTime(Long start);

	Long getStartTime();

	void setEndTime(Long end);

	Long getEndTime();

	void setDuration(Long duration);

	Long getDuration();
}