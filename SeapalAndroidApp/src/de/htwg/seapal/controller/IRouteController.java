package de.htwg.seapal.controller;

import java.util.List;
import java.util.UUID;

import de.htwg.seapal.model.IRoute;
import de.htwg.seapal.utils.observer.IObservable;

public interface IRouteController extends IObservable {

	/**
	 * Gets the name of the given route ID.
	 * 
	 * @param id
	 *            The route ID.
	 * @return The name.
	 */
	String getName(UUID id);

	/**
	 * Sets the name of the given route ID.
	 * 
	 * @param id
	 *            The route ID.
	 * @param name
	 *            The name.
	 */
	void setName(UUID id, String name);

	/**
	 * Gets the date of the given route ID.
	 * 
	 * @param id
	 *            The route ID.
	 * @return The date.
	 */
	long getDate(UUID id);

	/**
	 * Sets the date of the given route ID.
	 * 
	 * @param id
	 *            The route ID.
	 * @param date
	 *            The date.
	 */
	void setDate(UUID id, long date);

	/**
	 * Gets a list of all mark IDs of the given route ID.
	 * 
	 * @param id
	 *            The route ID.
	 * @return All mark IDs of the given route.
	 */
	List<UUID> getMarks(UUID id);

	/**
	 * Adds a given mark to the route.
	 * 
	 * @param id
	 *            The route ID.
	 * @param markId
	 *            The mark ID.
	 */
	void addMark(UUID id, UUID markId);

	/**
	 * Deletes a given mark of the route.
	 * 
	 * @param id
	 *            The route ID.
	 * @param markId
	 *            The mark ID.
	 */
	void deleteMark(UUID id, UUID markId);

	/**
	 * Gets a route entry point of the given route ID.
	 * 
	 * @param id
	 *            The route ID.
	 * @return The route entry point.
	 */
	UUID getRouteEntryPoint(UUID id);

	/**
	 * Sets a route entry point of the given route ID.
	 * 
	 * @param id
	 *            The route ID.
	 * @param markId
	 *            The route entry point.
	 */
	void setRouteEntryPoint(UUID id, UUID markId);

	/**
	 * Gets the distance of the given route ID.
	 * 
	 * @param id
	 *            The route ID.
	 * @return The distance.
	 */
	double getDistance(UUID id);

	/**
	 * Sets the distance of the given route ID.
	 * 
	 * @param id
	 *            The route ID.
	 * @param distance
	 *            The distance.
	 */
	void setDistance(UUID id, double distance);

	/**
	 * Deletes a route.
	 * 
	 * @param id
	 *            The route ID.
	 */
	void deleteRoute(UUID id);

	/**
	 * Closes the database connection.
	 */
	void closeDB();

	/**
	 * Gets a list of all route IDs.
	 * 
	 * @return All route IDs.
	 */
	List<UUID> getRoutes();

	/**
	 * Creates a new route.
	 * 
	 * @return The route ID.
	 */
	UUID newRoute();

	/**
	 * Gets the output string of the given route ID.
	 * 
	 * @param id
	 *            The route ID.
	 * @return The output string.
	 */
	String getString(UUID id);

	/**
	 * Gets a route by the given route ID.
	 * 
	 * @param routeId
	 *            The route ID.
	 * @return The route or NULL, if no route was found.
	 */
	IRoute getRoute(UUID routeId);

	/**
	 * Gets all routes.
	 * 
	 * @return All routes.
	 */
	List<IRoute> getAllRoutes();

	/**
	 * Saves the route.
	 * 
	 * @param route
	 *            The route to save.
	 * @return Returns TRUE, if the route was newly created and FALSE when the
	 *         route was updated.
	 */
	boolean saveRoute(IRoute route);
}
