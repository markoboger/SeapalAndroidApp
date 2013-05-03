package de.htwg.seapal.model;

import java.util.List;
import java.util.UUID;

public interface ITrip {

	String getName();

	void setName(String name);

	String getStartLocation();

	void setStartLocation(String location);

	String getEndLocation();

	void setEndLocation(String location);

	UUID getSkipper(); // Person

	void setSkipper(UUID skipper); // Person

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

	UUID getBoat();

	void setBoat(UUID boat);

	void setSkipper(String skipper);

	String getSkipperString();

	UUID getUUId();

	void setStartTime(long start);

	long getStartTime();

	void setEndTime(long end);

	long getEndTime();
}