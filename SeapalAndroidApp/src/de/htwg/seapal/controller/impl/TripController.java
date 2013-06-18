package de.htwg.seapal.controller.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import com.google.inject.Inject;

import de.htwg.seapal.controller.ITripController;
import de.htwg.seapal.database.ITripDatabase;
import de.htwg.seapal.model.ITrip;
import de.htwg.seapal.utils.logging.ILogger;
import de.htwg.seapal.utils.observer.Observable;

public class TripController extends Observable implements ITripController {

	private ITripDatabase db;
	private final ILogger logger;

	@Inject
	public TripController(ITripDatabase db, ILogger logger) {
		this.db = db;
		this.logger = logger;
	}

	@Override
	public String getName(UUID id) {
		ITrip trip = db.get(id);
		if (trip == null)
			return null;
		return trip.getName();

	}

	@Override
	public void setName(UUID id, String name) {
		ITrip trip = db.get(id);
		if (trip == null)
			return;
		trip.setName(name);
		db.save(trip);
		notifyObservers();
	}

	@Override
	public void setStartLocation(UUID id, String location) {
		ITrip trip = db.get(id);
		if (trip == null)
			return;
		trip.setStartLocation(location);
		db.save(trip);
		notifyObservers();
	}

	@Override
	public String getStartLocation(UUID id) {
		ITrip trip = db.get(id);
		if (trip == null)
			return null;
		return trip.getStartLocation();

	}

	@Override
	public void setEndLocation(UUID id, String location) {
		ITrip trip = db.get(id);
		if (trip == null)
			return;
		trip.setEndLocation(location);
		db.save(trip);
		notifyObservers();

	}

	@Override
	public String getEndLocation(UUID id) {
		ITrip trip = db.get(id);
		if (trip == null)
			return null;
		return trip.getEndLocation();

	}

	@Override
	public void setSkipper(UUID id, UUID skipper) {
		ITrip trip = db.get(id);
		if (trip == null)
			return;
		trip.setSkipper(skipper.toString());
		db.save(trip);
		notifyObservers();

	}

	@Override
	public UUID getSkipper(UUID id) {
		ITrip trip = db.get(id);
		if (trip == null)
			return null;
		if(trip.getSkipper().equals(""))
			return null;
		else
			return UUID.fromString(trip.getSkipper());

	}

	@Override
	public void setCrewMember(UUID id, String crewMember) {
		ITrip trip = db.get(id);
		if (trip == null)
			return;
		trip.setCrewMember(crewMember);
		db.save(trip);
		notifyObservers();
	}

	@Override
	public String getCrewMembers(UUID id) {
		ITrip trip = db.get(id);
		if (trip == null)
			return null;
		return trip.getCrewMembers();
	}

	@Override
	public void setStartTime(UUID id, long start) {
		ITrip trip = db.get(id);
		if (trip == null)
			return;
		trip.setStartTime(start);
		db.save(trip);
		notifyObservers();
	}

	@Override
	public long getStartTime(UUID id) {
		ITrip trip = db.get(id);
		if (trip == null)
			return -1;
		return trip.getStartTime();
	}

	@Override
	public void setEndTime(UUID id, long end) {
		ITrip trip = db.get(id);
		if (trip == null)
			return;
		trip.setEndTime(end);
		db.save(trip);
		notifyObservers();
	}

	@Override
	public long getEndTime(UUID id) {
		ITrip trip = db.get(id);
		if (trip == null)
			return -1;
		return trip.getEndTime();
	}

	@Override
	public void setMotor(UUID id, int motor) {
		ITrip trip = db.get(id);
		if (trip == null)
			return;
		trip.setMotor(motor);
		db.save(trip);
		notifyObservers();
	}

	@Override
	public int getMotor(UUID id) {
		ITrip trip = db.get(id);
		if (trip == null)
			return -1;
		return trip.getMotor();
	}

	@Override
	public void setFuel(UUID id, double percent) {
		ITrip trip = db.get(id);
		if (trip == null)
			return;
		trip.setFuel(percent);
		db.save(trip);
		notifyObservers();
	}

	@Override
	public double getFuel(UUID id) {
		ITrip trip = db.get(id);
		if (trip == null)
			return -1;
		return trip.getFuel();
	}

	@Override
	public void setNotes(UUID id, String text) {
		ITrip trip = db.get(id);
		if (trip == null)
			return;
		trip.setNotes(text);
		db.save(trip);
		notifyObservers();
	}

	@Override
	public String getNotes(UUID id) {
		ITrip trip = db.get(id);
		if (trip == null)
			return null;
		return trip.getNotes();
	}

	@Override
	public String getString(UUID id) {
		return " ID = \t" + id + "\n Name = \t" + getName(id)
				+ "\n startLocation = \t" + getStartLocation(id)
				+ "\n endLocation = \t" + getEndLocation(id)
				+ "\n skipper = \t" + getSkipper(id) + "\n crew = \t"
				+ getCrewMembers(id) + "\n startTime = \t" + getStartTime(id)
				+ "\n endTime = \t" + getEndTime(id) + "\n motor = \t"
				+ getMotor(id) + "\n fuel = \t" + getFuel(id) + "\n notes = \t"
				+ getNotes(id) + "\n boat = \t" + getBoat(id) + "\n";
	}

	@Override
	public UUID newTrip(UUID boat) {
		UUID newTrip = db.create();
		ITrip trip = db.get(newTrip);
		trip.setBoat(boat.toString());
		db.save(trip);
		notifyObservers(); // ??
		return newTrip;
	}

	@Override
	public void deleteTrip(UUID id) {
		db.delete(id);
		notifyObservers();
	}

	@Override
	public final void closeDB() {
		db.close();
		logger.info("TripController", "Database closed");
	}

	@Override
	public List<UUID> getTrips() {
		List<ITrip> trips = db.loadAll();
		List<UUID> list = new ArrayList<UUID>();
		for (ITrip trip : trips) {
			list.add(UUID.fromString(trip.getId()));
		}
		return list;
	}

	@Override
	public List<UUID> getTrips(UUID boatId) {
		List<UUID> query = db.loadAllById(boatId);
		logger.info("TripController", "All trips: " + query.toString());
		
		return query;
	}

	@Override
	public ITrip getTrip(UUID tripId) {
		return db.get(tripId);
	}

	@Override
	public List<ITrip> getAllTrips() {
		return db.loadAll();
	}

	@Override
	public List<ITrip> getAllTrips(UUID boatId) {
		List<ITrip> trips = new LinkedList<ITrip>();
		List<UUID> ids = db.loadAllById(boatId);
		logger.info("TripController", "All trips: " + trips.toString());
		// TODO: filtering should be moved to database layer.
		for(UUID id : ids) {
			trips.add(getTrip(id));
		}
		return trips;
	}

	@Override
	public boolean saveTrip(ITrip trip) {
		return db.save(trip);
	}

	@Override
	public String getBoat(UUID id) {
		return db.get(id).getBoat();
	}
}