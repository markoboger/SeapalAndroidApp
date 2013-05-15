package de.htwg.seapal.model;

import java.util.UUID;

public interface IModel {
	/**
	 * Gets the UUID of the data.
	 * @return The UUID of the data.
	 */
	UUID getUUID();

	/**
	 * Gets the String converted UUID.
	 * @return The UUID of the data as String.
	 */
	String getId();
}
