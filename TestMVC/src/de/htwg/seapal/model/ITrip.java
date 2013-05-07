package de.htwg.seapal.model;

import java.util.List;

public interface ITrip {

	String getName();

	void setName(String name);

	String getStartLocation();

	void setStartLocation(String location);

	String getEndLocation();

	void setEndLocation(String location);

	String getSkipper(); // Person

	void setSkipper(String skipper); // Person

	List<String> getCrewMembers(); // Person

	void addCrewMember(String crewMember); // Person

	long getDuration();

	void setDuration(long timeInSeconds);

	int getMotor();

	void setMotor(int motor);

	double getFuel();

	void setFuel(double percent);

	String getNotes();

	void setNotes(String text);

	String getId();

	String getBoat();

	void setBoat(String boat);

	void setStartTime(long start);

	long getStartTime();

	void setEndTime(long end);

	long getEndTime();
}