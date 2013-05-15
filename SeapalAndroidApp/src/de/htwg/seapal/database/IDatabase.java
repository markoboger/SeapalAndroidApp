package de.htwg.seapal.database;

import java.util.List;
import java.util.UUID;

/**
 * The generic database interface.
 * @param <T> The model interface to manage.
 */
public interface IDatabase<T> {
	/**
	 * Opens the database connection.
	 * @return TRUE, if successful.
	 */
	boolean open();
	
	/**
	 * Creates a new data entry.
	 * @return The UUID of the created data entry.
	 */
	UUID create();
	
	/**
	 * Saves a data entry.
	 * @param data The data entry to save.
	 * @return Returns TRUE, if the data entry was newly created
	 * 	       and FALSE when the entry was updated.
	 */
	boolean save(T data);

	/**
	 * Gets a data entry with a given UUID.
	 * @param id The UUID of the data entry.
	 * @return The data entry with the given UUID or NULL,
	 *         if no data was found.
	 */
	T get(UUID id);

	/**
	 * Gets a list of all data entries.
	 * @return All boats.
	 */
	List<T> loadAll();

	/**
	 * Deletes the data entry with the given UUID.
	 * @param id The UUID of the data entry to delete.
	 */
	void delete(UUID id);
	
	/**
	 * Closes the database connection.
	 * @return TRUE, if successful.
	 */
	boolean close();
}
