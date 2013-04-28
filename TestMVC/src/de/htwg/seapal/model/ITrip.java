package de.htwg.seapal.model;

import java.util.Date;
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

	List<UUID> getCrewMembers(); // Person

	void addCrewMember(UUID crewMember); // Person

	Date getStartTime();

	void setStartTime(Date start);

	Date getEndTime();

	void setEndTime(Date end);

	long getDuration();

	void setDuration(long timeInSeconds);

	int getMotor();

	void setMotor(int motor);

	double getFuel();

	void setFuel(double percent);

	String getNotes();

	void setNotes(String text);

	UUID getId();

	UUID getBoat();

	void setBoat(UUID boat);
}