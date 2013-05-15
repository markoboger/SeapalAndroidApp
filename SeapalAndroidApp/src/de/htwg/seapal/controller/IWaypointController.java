package de.htwg.seapal.controller;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import de.htwg.seapal.model.IWaypoint;
import de.htwg.seapal.model.IWaypoint.ForeSail;
import de.htwg.seapal.model.IWaypoint.MainSail;
import de.htwg.seapal.model.IWaypoint.Maneuver;
import de.htwg.seapal.utils.observer.IObservable;

public interface IWaypointController extends IObservable {

	/**
	 * Returns the name of the waypoint.
	 * 
	 * @param id
	 *            The waypoint ID.
	 * @return The name.
	 */
	String getName(UUID id);

	/**
	 * Returns the Note of the waypoint.
	 * 
	 * @param id
	 *            The waypoint ID.
	 * @return The note or an empty String.
	 */
	String getNote(UUID id);

	/**
	 * Returns the Baring To Mark value.
	 * 
	 * @param id
	 *            The waypoint ID.
	 * @return Baring to mark in degrees.
	 */
	int getBTM(UUID id);

	/**
	 * Returns the Distance To Mark value.
	 * 
	 * @param id
	 *            The waypoint ID.
	 * @return Distance to mark.
	 */
	int getDTM(UUID id);

	/**
	 * Returns the Course Over Ground value.
	 * 
	 * @param id
	 *            The waypoint ID.
	 * @return course over ground in degrees.
	 */
	int getCOG(UUID id);

	/**
	 * Returns the Speed Over Ground value.
	 * 
	 * @param id
	 *            The waypoint ID.
	 * @return Speed over ground.
	 */
	int getSOG(UUID id);

	/**
	 * Returns the mark.
	 * 
	 * @param id
	 *            The waypoint ID.
	 * @return The mark
	 */
	UUID getHeadedFor(UUID id);

	/**
	 * Returns the maneuver done at the waypoint.
	 * 
	 * @param id
	 *            The waypoint ID.
	 * @return The maneuver
	 */
	Maneuver getManeuver(UUID id);

	/**
	 * Returns the foresail set at the waypoint.
	 * 
	 * @param id
	 *            The waypoint ID.
	 * @return The foresail
	 */
	ForeSail getForesail(UUID id);

	/**
	 * Returns the main sail set at the waypoint.
	 * 
	 * @param id
	 *            The waypoint ID.
	 * @return The main sail
	 */
	MainSail getMainsail(UUID id);

	/**
	 * Sets the name.
	 * 
	 * @param id
	 *            The waypoint ID.
	 * @param name
	 *            The name.
	 */
	void setName(UUID id, String name);

	/**
	 * Sets the note.
	 * 
	 * @param id
	 *            The waypoint ID.
	 * @param note
	 *            The note.
	 */
	void setNote(UUID id, String note);

	/**
	 * Sets the Baring To Mark in degrees.
	 * 
	 * @param id
	 *            The waypoint ID.
	 * @param btm
	 *            Baring To Mark.
	 */
	void setBTM(UUID id, int btm);

	/**
	 * Sets the Distance To Mark.
	 * 
	 * @param id
	 *            The waypoint ID.
	 * @param dtm
	 *            Distance To Mark.
	 */
	void setDTM(UUID id, int dtm);

	/**
	 * Sets the Course Over Ground in degrees.
	 * 
	 * @param id
	 *            The waypoint ID.
	 * @param cog
	 *            Course Over Ground.
	 */
	void setCOG(UUID id, int cog);

	/**
	 * Sets the Speed Over Ground.
	 * 
	 * @param id
	 *            The waypoint ID.
	 * @param sog
	 *            Speed Over Ground.
	 */
	void setSOG(UUID id, int sog);

	/**
	 * Sets the mark representing through its id.
	 * 
	 * @param id
	 *            The waypoint ID.
	 * @param markId
	 *            The ID of mark.
	 * @throws NoSuchElementException
	 *             If there is no Mark to the id
	 */
	void setHeadedFor(UUID id, UUID markId);

	/**
	 * Sets the maneuver of the waypoint. Don't use <tt>null</tt> here. Use
	 * <tt>Maneuver.NONE</tt> instead.
	 * 
	 * @param id
	 *            The waypoint ID.
	 * @param maneuver
	 *            The maneuver.
	 * @throws IllegalArgumentException
	 *             If mainSail is null.
	 */
	void setManeuver(UUID id, Maneuver maneuver);

	/**
	 * Sets the main sail.<br/>
	 * Don't use <tt>null</tt> here. Use <tt>Mainsail.NONE</tt> instead.
	 * 
	 * @param id
	 *            The waypoint ID.
	 * @param mainSail
	 *            The mainsail
	 * @throws IllegalArgumentException
	 *             If mainSail is null
	 */
	void setMainsail(UUID id, MainSail mainSail);

	/**
	 * Sets the fore sail.<br/>
	 * Don't use <tt>null</tt> here. Use <tt>Foresail.NONE</tt> instead.
	 * 
	 * @param id
	 *            The waypoint ID.
	 * @param foreSail
	 *            The foresail.
	 * @throws IllegalArgumentException
	 *             If foreSail is null.
	 */
	void setForesail(UUID id, ForeSail foreSail);

	/**
	 * Sets the longitude of the given waypoint ID.
	 * 
	 * @param id
	 *            The waypoint ID.
	 * @param longitude
	 *            The longitude.
	 */
	void setLongitude(UUID id, double longitude);

	/**
	 * Sets the latitude of the given waypoint ID.
	 * 
	 * @param id
	 *            The waypoint ID.
	 * @param latitude
	 *            The longitude.
	 */
	void setLatitude(UUID id, double latitude);

	/**
	 * Gets the longitude of the given waypoint ID.
	 * 
	 * @param id
	 *            The waypoint ID.
	 * @return The longitude.
	 */
	double getLongitude(UUID id);

	/**
	 * Gets the latitude of the given waypoint ID.
	 * 
	 * @param id
	 *            The waypoint ID.
	 * @return The latitude.
	 */
	double getLatitude(UUID id);

	/**
	 * Returns the string representing the current modifying waypoint.
	 * 
	 * @param id
	 *            The waypoint ID.
	 * @return string Representing the current modifying waypoint.
	 */
	String getString(UUID id);

	/**
	 * Creates a new WaypointElement.
	 * 
	 * @param tripId
	 *            The trip ID.
	 * @return All waypoints of the given trip.
	 */
	UUID newWaypoint(UUID tripId);

	/**
	 * Delete the currently selected waypoint.
	 * 
	 * @param id
	 *            The waypoint ID.
	 */
	void deleteWaypoint(UUID id);

	/**
	 * Gets a list of all waypoints.
	 * 
	 * @return All waypoints.
	 */
	List<UUID> getWaypoints();

	/**
	 * Gets a list of all waypoints of the given trip ID.
	 * 
	 * @param tripId
	 *            The trip ID.
	 * @return All waypoints of the given trip ID.
	 */
	List<UUID> getWaypoints(UUID tripId);

	/**
	 * Closes database connection and other open resources.
	 */
	void closeDB();

	/**
	 * Creates a new waypoint.
	 * 
	 * @param tripId
	 *            The trip ID.
	 * @param location
	 *            The location.
	 * @param date
	 *            The date.
	 * @return The waypoint ID.
	 */
	UUID newWaypoint(UUID tripId, long date, double longitude, double latitude);

	/**
	 * Gets a waypoint by the given waypoint ID.
	 * 
	 * @param waypointId
	 *            The waypoint ID.
	 * @return The waypoint or NULL, if no waypoint was found.
	 */
	IWaypoint getWaypoint(UUID waypointId);

	/**
	 * Gets a list of all waypoints
	 * 
	 * @return All waypoints
	 */
	List<IWaypoint> getAllWaypoints();

	/**
	 * Gets a list of all waypoints of the given trip ID.
	 * 
	 * @param tripId
	 *            The trip id.
	 * @return The waypoints of the trip.
	 */
	List<IWaypoint> getAllWaypoints(UUID tripId);

	/**
	 * Saves the waypoint.
	 * 
	 * @param waypoint
	 *            The waypoint to save.
	 * @return Returns TRUE, if the waypoint was newly created and FALSE when
	 *         the waypoint was updated.
	 */
	boolean saveWaypoint(IWaypoint waypoint);
}