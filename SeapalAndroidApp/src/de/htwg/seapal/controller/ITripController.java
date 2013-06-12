package de.htwg.seapal.controller;

import java.util.List;
import java.util.UUID;

import de.htwg.seapal.model.ITrip;
import de.htwg.seapal.utils.observer.IObservable;

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

	int getMotor(UUID id);

	void setMotor(UUID id, int motor);

	double getFuel(UUID id);

	void setFuel(UUID id, double percent);

	String getNotes(UUID id);

	void setNotes(UUID id, String text);

	String getString(UUID id);

	void deleteTrip(UUID id);

	void closeDB();

	String getBoat(UUID id);

	List<UUID> getTrips();

	UUID newTrip(UUID boat);

	List<UUID> getTrips(UUID boatId);

	/**
	 * Gets a trip by the given trip ID.
	 * 
	 * @param tripId
	 *            The trip ID.
	 * @return The trip or NULL, if no trip was found.
	 */
	ITrip getTrip(UUID tripId);

	/**
	 * Gets all trips.
	 * 
	 * @return All trips.
	 */
	List<ITrip> getAllTrips();

	/**
	 * Gets all trips of the given boat ID.
	 * 
	 * @param boatId
	 *            The boat ID.
	 * @return All Trips of the boat ID.
	 */
	List<ITrip> getAllTrips(UUID boatId);

	/**
	 * Saves the trip.
	 * 
	 * @param trip
	 *            The trip to save.
	 * @return Returns TRUE, if the trip was newly created and FALSE when the
	 *         trip was updated.
	 */
	boolean saveTrip(ITrip trip);
}