package de.htwg.seapal.controller;

import java.util.List;
import java.util.UUID;

import de.htwg.seapal.model.IMark;
import de.htwg.seapal.utils.observer.IObservable;

public interface IMarkController extends IObservable {

	/**
	 * Gets the name of the given mark ID.
	 * 
	 * @param id
	 *            The mark ID.
	 * @return The name.
	 */
	String getName(UUID id);

	/**
	 * Sets the name of the given mark ID.
	 * 
	 * @param id
	 *            The mark ID.
	 * @param name
	 *            The name.
	 */
	void setName(UUID id, String name);

	/**
	 * Gets the latitude of the given mark ID.
	 * 
	 * @param id
	 *            The mark ID.
	 * @return The latitude.
	 */
	double getLatitude(UUID id);

	/**
	 * Sets the latitude of the given mark ID.
	 * 
	 * @param id
	 *            The mark ID.
	 * @param latitute
	 *            The latitude.
	 */
	void setLatitude(UUID id, double latitute);

	/**
	 * Gets the longitude of the give mark ID.
	 * 
	 * @param id
	 *            The mark ID.
	 * @return The longitude.
	 */
	double getLongitude(UUID id);

	/**
	 * Sets the longitude of the give mark ID.
	 * 
	 * @param id
	 *            The mark ID.
	 * @param longitude
	 *            The longitude.
	 */
	void setLongitude(UUID id, double longitude);

	/**
	 * Gets the note of the given mark ID.
	 * 
	 * @param id
	 *            The mark ID.
	 * @return The note.
	 */
	String getNote(UUID id);

	/**
	 * Sets the note of the given mark ID.
	 * 
	 * @param id
	 *            The mark ID.
	 * @param note
	 *            The note.
	 */
	void setNote(UUID id, String note);

	/**
	 * Gets the BTM of the given mark ID.
	 * 
	 * @param id
	 *            The mark ID.
	 * @return The BTM.
	 */
	int getBTM(UUID id);

	/**
	 * Sets the BTM of the given mark ID.
	 * 
	 * @param id
	 *            The mark ID.
	 * @param btm
	 *            The BTM.
	 */
	void setBTM(UUID id, int btm);

	/**
	 * Gets the DTM of the given mark ID.
	 * 
	 * @param id
	 *            The mark ID.
	 * @return The DTM.
	 */
	int getDTM(UUID id);

	/**
	 * Sets the DTM of the given mark ID.
	 * 
	 * @param id
	 *            The mark ID.
	 * @param dtm
	 *            The DTM.
	 */
	void setDTM(UUID id, int dtm);

	/**
	 * Gets the COG of the given mark ID.
	 * 
	 * @param id
	 *            The mark ID.
	 * @return The COG.
	 */
	int getCOG(UUID id);

	/**
	 * Sets the COG of the given mark ID.
	 * 
	 * @param id
	 *            The mark ID.
	 * @param cog
	 *            The COG.
	 */
	void setCOG(UUID id, int cog);

	/**
	 * Gets the SOG of the given mark ID.
	 * 
	 * @param id
	 *            The mark ID.
	 * @return The SOG.
	 */
	int getSOG(UUID id);

	/**
	 * Sets the SOG of the given mark ID.
	 * 
	 * @param id
	 *            The mark ID.
	 * @param sog
	 *            The SOG.
	 */
	void setSOG(UUID id, int sog);

	/**
	 * Gets the date of the given mark ID.
	 * 
	 * @param id
	 *            The mark ID.
	 * @return The date.
	 */
	long getDate(UUID id);

	/**
	 * Sets the date of the given mark ID.
	 * 
	 * @param id
	 *            The mark ID.
	 * @param date
	 *            The date.
	 */
	void setDate(UUID id, long date);

	/**
	 * Indicates whether the given mark ID is a route mark.
	 * 
	 * @param id
	 *            The mark ID.
	 * @return TRUE, if it is a route mark.
	 */
	boolean isRouteMark(UUID id);

	/**
	 * Sets the value whether the given mark ID is a route mark.
	 * 
	 * @param id
	 *            The mark ID.
	 * @param isRouteMark
	 *            The value indication whether it is a route mark or not.
	 */
	void setIsRouteMark(UUID id, boolean isRouteMark);

	/**
	 * Deletes a mark.
	 * 
	 * @param id
	 *            The mark ID.
	 */
	void deleteMark(UUID id);

	/**
	 * Closes the database connection.
	 */
	void closeDB();

	/**
	 * Gets a list of all mark IDs.
	 * 
	 * @return All mark IDs.
	 */
	List<UUID> getMarks();

	/**
	 * Creates a new route mark.
	 * 
	 * @return The mark ID.
	 */
	UUID newRouteMark(double longitude, double latitude);

	/**
	 * Creates a new mark.
	 * 
	 * @return The mark ID.
	 */
	UUID newMark(double longitude, double latitude);

	/**
	 * Gets the output string of the given mark ID.
	 * 
	 * @param id
	 *            The mark ID.
	 * @return The output string.
	 */
	String getString(UUID id);

	/**
	 * Gets a mark by the given mark ID.
	 * 
	 * @param markId
	 *            The mark ID.
	 * @return The mark or NULL, if no mark was found.
	 */
	IMark getMark(UUID markId);

	/**
	 * Gets all marks.
	 * 
	 * @return All marks.
	 */
	List<IMark> getAllMarks();

	/**
	 * Saves the mark.
	 * 
	 * @param mark
	 *            The mark to save.
	 * @return Returns TRUE, if the mark was newly created and FALSE when the
	 *         mark was updated.
	 */
	boolean saveMark(IMark mark);
}
