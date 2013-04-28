package de.htwg.seapal.controller;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import de.htwg.seapal.model.IWaypoint.ForeSail;
import de.htwg.seapal.model.IWaypoint.MainSail;
import de.htwg.seapal.model.IWaypoint.Maneuver;
import de.htwg.seapal.observer.IObservable;


public interface IWaypointController extends IObservable {

	/**
	 * Returns the name of the waypoint.
	 * 
	 * @return the name
	 */
	String getName(UUID id);

	/**
	 * Returns the position as string of the waypoint.
	 * 
	 * @return the position.
	 */
	String getPosition(UUID id);

	/**
	 * Returns the Note of the waypoint.
	 * 
	 * @return the note or an empty String.
	 */
	String getNote(UUID id);

	/**
	 * Returns the Baring To Mark value.
	 * 
	 * @return baring to mark in degrees.
	 */
	int getBTM(UUID id);

	/**
	 * Returns the Distance To Mark value.
	 * 
	 * @return distance to mark.
	 */
	int getDTM(UUID id);

	/**
	 * Returns the Course Over Ground value.
	 * 
	 * @return course over ground in degrees.
	 */
	int getCOG(UUID id);

	/**
	 * Returns the Speed Over Ground value.
	 * 
	 * @return speed over ground.
	 */
	int getSOG(UUID id);

	/**
	 * Returns the mark.
	 * 
	 * @return the mark
	 */
	UUID getHeadedFor(UUID id);

	/**
	 * Returns the maneuver done at the waypoint.
	 * 
	 * @return the maneuver
	 */
	Maneuver getManeuver(UUID id);

	/**
	 * Returns the foresail set at the waypoint.
	 * 
	 * @return the foresail
	 */
	ForeSail getForesail(UUID id);

	/**
	 * Returns the main sail set at the waypoint.
	 * 
	 * @return the main sail
	 */
	MainSail getMainsail(UUID id);

	/**
	 * Sets the name.
	 * 
	 * @param name
	 *            the name
	 */
	void setName(UUID id, String name);

	/**
	 * Sets the position.
	 * 
	 * @param position
	 *            the position
	 */
	void setPosition(UUID id, String position);

	/**
	 * Sets the note.
	 * 
	 * @param note
	 *            the note
	 */
	void setNote(UUID id, String note);

	/**
	 * Sets the Baring To Mark in degrees.
	 * 
	 * @param btm
	 *            Baring To Mark
	 */
	void setBTM(UUID id, int btm);

	/**
	 * Sets the Distance To Mark.
	 * 
	 * @param dtm
	 *            Distance To Mark
	 */
	void setDTM(UUID id, int dtm);

	/**
	 * Sets the Course Over Ground in degrees.
	 * 
	 * @param cog
	 *            Course Over Ground
	 */
	void setCOG(UUID id, int cog);

	/**
	 * Sets the Speed Over Ground.
	 * 
	 * @param sog
	 *            Speed Over Ground
	 */
	void setSOG(UUID id, int sog);

	/**
	 * Sets the mark representing through its id.
	 * 
	 * @param markId
	 *            id of mark
	 * @throws NoSuchElementException
	 *             if there is no Mark to the id
	 */
	void setHeadedFor(UUID id, UUID markId);

	/**
	 * Sets the maneuver of the waypoint. Don't use <tt>null</tt> here. Use
	 * <tt>Maneuver.NONE</tt> instead.
	 * 
	 * @param maneuver
	 *            the maneuver
	 * @throws IllegalArgumentException
	 *             if mainSail is null
	 */
	void setManeuver(UUID id, Maneuver maneuver);

	/**
	 * Sets the main sail.<br/>
	 * Don't use <tt>null</tt> here. Use <tt>Mainsail.NONE</tt> instead.
	 * 
	 * @param mainSail
	 *            the mainsail
	 * @throws IllegalArgumentException
	 *             if mainSail is null
	 */
	void setMainsail(UUID id, MainSail mainSail);

	/**
	 * Sets the fore sail.<br/>
	 * Don't use <tt>null</tt> here. Use <tt>Foresail.NONE</tt> instead.
	 * 
	 * @param foreSail
	 *            the foresail
	 * @throws IllegalArgumentException
	 *             if foreSail is null
	 */
	void setForesail(UUID id, ForeSail foreSail);

	/**
	 * Returns the string representing the current modifying waypoint.
	 * 
	 * @return string representing the current modifying waypoint
	 */
	String getString(UUID id);

	/**
	 * Creates a new WaypointElement.
	 */
	UUID newWaypoint(UUID trip);

	/**
	 * Delete the currently selected waypoint.
	 */
	void deleteWaypoint(UUID id);

	/**
	 * Gets a list of all waypoints.
	 */
	List<UUID> getWaypoints();

	List<UUID> getWaypoints(UUID trip);

	/**
	 * Closes database connection and other open resources.
	 */
	void closeDB();


}