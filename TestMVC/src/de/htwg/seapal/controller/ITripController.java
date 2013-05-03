package de.htwg.seapal.controller;

import java.util.List;
import java.util.UUID;

import de.htwg.seapal.observer.IObservable;

public interface ITripController extends IObservable {

	String getName(UUID id);

	void setName(UUID id, String name);

	String getStartLocation(UUID id);

	void setStartLocation(UUID id, String location);

	String getEndLocation(UUID id);

	void setEndLocation(UUID id, String location);

	UUID getSkipper(UUID id);

	void setSkipper(UUID id, UUID skipper);

	List<String> getCrewMembers(UUID id);

	void addCrewMember(UUID id, String crewMember);

	long getStartTime(UUID id);

	void setStartTime(UUID id, long start);

	long getEndTime(UUID id);

	void setEndTime(UUID id, long end);

	long getDuration(UUID id);

	void setDuration(UUID id, long timeInSeconds);

	int getMotor(UUID id);

	void setMotor(UUID id, int motor);

	double getFuel(UUID id);

	void setFuel(UUID id, double percent);

	String getNotes(UUID id);

	void setNotes(UUID id, String text);

	String getString(UUID id);

	void deleteTrip(UUID id);

	void closeDB();

	List<UUID> getTrips();

	UUID newTrip(UUID boat);

	List<UUID> getTrips(UUID boat);

	UUID getBoat(UUID id);
}