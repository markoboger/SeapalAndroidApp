package de.htwg.seapal.model;

import java.util.List;
import java.util.UUID;

public interface IRegatta extends IModel {
	String getName();

	void setName(String name);

	UUID getHost();

	void setHost(UUID host);

	Long getEstimatedStartDate();

	void setEstimatedStartDate(Long date);

	Long getEstimatedFinishTime();

	void setEstimatedFinishTime(Long date);

	Long getRealStartTime();

	void setRealStartTime(Long date);

	Long getRealFinishTime();

	void setRealFinishTime(Long date);

	List<UUID> getBoats();

	void addBoats(UUID boat);

	List<UUID> getTrips();

	void addTrip(UUID tripId);

	String getString();
}
