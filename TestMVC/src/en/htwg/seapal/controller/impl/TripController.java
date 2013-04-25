package en.htwg.seapal.controller.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import en.htwg.seapal.controller.ITripController;
import en.htwg.seapal.database.ITripDatabase;
import en.htwg.seapal.model.ITrip;
import en.htwg.seapal.observer.Observable;

public class TripController extends Observable implements ITripController {

	private ITripDatabase db;

	public TripController(ITripDatabase db) {
		this.db = db;
	}

	@Override
	public String getName(UUID id) {
		ITrip trip = db.getTrip(id);
		if (trip == null)
			return null;
		return trip.getName();

	}

	@Override
	public void setName(UUID id, String name) {
		ITrip trip = db.getTrip(id);
		if (trip == null)
			return;
		trip.setName(name);
		db.saveTrip(trip);
		notifyObservers();
	}

	@Override
	public void setStartLocation(UUID id, String location) {
		ITrip trip = db.getTrip(id);
		if (trip == null)
			return;
		trip.setStartLocation(location);
		db.saveTrip(trip);
		notifyObservers();
	}

	@Override
	public String getStartLocation(UUID id) {
		ITrip trip = db.getTrip(id);
		if (trip == null)
			return null;
		return trip.getStartLocation();

	}

	@Override
	public void setEndLocation(UUID id, String location) {
		ITrip trip = db.getTrip(id);
		if (trip == null)
			return;
		trip.setEndLocation(location);
		db.saveTrip(trip);
		notifyObservers();

	}

	@Override
	public String getEndLocation(UUID id) {
		ITrip trip = db.getTrip(id);
		if (trip == null)
			return null;
		return trip.getEndLocation();

	}

	@Override
	public void setSkipper(UUID id, UUID skipper) {
		ITrip trip = db.getTrip(id);
		if (trip == null)
			return;
		trip.setSkipper(skipper);
		db.saveTrip(trip);
		notifyObservers();

	}

	@Override
	public UUID getSkipper(UUID id) {
		ITrip trip = db.getTrip(id);
		if (trip == null)
			return null;
		return trip.getSkipper();

	}

	@Override
	public void addCrewMembers(UUID id, List<UUID> crewMembers) {
		ITrip trip = db.getTrip(id);
		if (trip == null)
			return;
		trip.addCrewMembers(crewMembers);
		db.saveTrip(trip);
		notifyObservers();
	}

	@Override
	public List<UUID> getCrewMembers(UUID id) {
		ITrip trip = db.getTrip(id);
		if (trip == null)
			return null;
		return trip.getCrewMembers();
	}

	@Override
	public void setStartTime(UUID id, Date start) {
		ITrip trip = db.getTrip(id);
		if (trip == null)
			return;
		trip.setStartTime(start);
		db.saveTrip(trip);
		notifyObservers();
	}

	@Override
	public Date getStartTime(UUID id) {
		ITrip trip = db.getTrip(id);
		if (trip == null)
			return null;
		return trip.getStartTime();
	}

	@Override
	public void setEndTime(UUID id, Date end) {
		ITrip trip = db.getTrip(id);
		if (trip == null)
			return;
		trip.setEndTime(end);
		db.saveTrip(trip);
		notifyObservers();
	}

	@Override
	public Date getEndTime(UUID id) {
		ITrip trip = db.getTrip(id);
		if (trip == null)
			return null;
		return trip.getEndTime();
	}

	@Override
	public void setDuration(UUID id, long timeInSeconds) {
		ITrip trip = db.getTrip(id);
		if (trip == null)
			return;
		trip.setDuration(timeInSeconds);
		db.saveTrip(trip);
		notifyObservers();
	}

	@Override
	public long getDuration(UUID id) {
		ITrip trip = db.getTrip(id);
		if (trip == null)
			return -1;
		return trip.getDuration();
	}

	@Override
	public void setMotor(UUID id, int motor) {
		ITrip trip = db.getTrip(id);
		if (trip == null)
			return;
		trip.setMotor(motor);
		db.saveTrip(trip);
		notifyObservers();
	}

	@Override
	public int getMotor(UUID id) {
		ITrip trip = db.getTrip(id);
		if (trip == null)
			return -1;
		return trip.getMotor();
	}

	@Override
	public void setFuel(UUID id, double percent) {
		ITrip trip = db.getTrip(id);
		if (trip == null)
			return;
		trip.setFuel(percent);
		db.saveTrip(trip);
		notifyObservers();
	}

	@Override
	public double getFuel(UUID id) {
		ITrip trip = db.getTrip(id);
		if (trip == null)
			return -1;
		return trip.getFuel();
	}

	@Override
	public void setNotes(UUID id, String text) {
		ITrip trip = db.getTrip(id);
		if (trip == null)
			return;
		trip.setNotes(text);
		db.saveTrip(trip);
		notifyObservers();
	}

	@Override
	public String getNotes(UUID id) {
		ITrip trip = db.getTrip(id);
		if (trip == null)
			return null;
		return trip.getNotes();
	}

	@Override
	public String getString(UUID id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UUID newTrip() {
		UUID newTrip = db.newTrip();
		notifyObservers(); // ??
		return newTrip;
	}

	@Override
	public void deleteTrip(UUID id) {
		db.deleteTrip(id);
		notifyObservers();
	}

	@Override
	public void closeDB() {
		db.closeDB();
	}

	@Override
	public List<UUID> getTrips() {
		List<ITrip> query = db.getTrips();
		List<UUID> list = new ArrayList<UUID>();
		for (ITrip boat : query) {
			list.add(boat.getId());
		}
		return list;
	}

}