package de.htwg.seapal.controller.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import com.google.inject.Inject;

import de.htwg.seapal.controller.IMarkController;
import de.htwg.seapal.database.IMarkDatabase;
import de.htwg.seapal.model.IMark;
import de.htwg.seapal.utils.logging.ILogger;
import de.htwg.seapal.utils.observer.Observable;

public class MarkController extends Observable implements IMarkController {

	private IMarkDatabase db;
	private final ILogger logger;

	@Inject
	public MarkController(IMarkDatabase db, ILogger logger) {
		this.db = db;
		this.logger = logger;
	}

	@Override
	public String getName(UUID id) {
		IMark mark = db.get(id);
		if (mark == null)
			return null;
		return mark.getName();
	}

	@Override
	public void setName(UUID id, String name) {
		IMark mark = db.get(id);
		if (mark == null)
			return;
		mark.setName(name);
		db.save(mark);
		notifyObservers();
	}

	@Override
	public double getLatitude(UUID id) {
		IMark mark = db.get(id);
		if (mark == null)
			return -1;
		return mark.getLatitude();
	}

	@Override
	public void setLatitude(UUID id, double latitute) {
		IMark mark = db.get(id);
		if (mark == null)
			return;
		mark.setLatitude(latitute);
		db.save(mark);
		notifyObservers();
	}

	@Override
	public double getLongitude(UUID id) {
		IMark mark = db.get(id);
		if (mark == null)
			return -1;
		return mark.getLongitude();
	}

	@Override
	public void setLongitude(UUID id, double longitude) {
		IMark mark = db.get(id);
		if (mark == null)
			return;
		mark.setLongitude(longitude);
		db.save(mark);
		notifyObservers();
	}

	@Override
	public String getNote(UUID id) {
		IMark mark = db.get(id);
		if (mark == null)
			return null;
		return mark.getNote();
	}

	@Override
	public void setNote(UUID id, String note) {
		IMark mark = db.get(id);
		if (mark == null)
			return;
		mark.setNote(note);
		db.save(mark);
		notifyObservers();
	}

	@Override
	public int getBTM(UUID id) {
		IMark mark = db.get(id);
		if (mark == null)
			return -1;
		return mark.getBTM();
	}

	@Override
	public void setBTM(UUID id, int btm) {
		IMark mark = db.get(id);
		if (mark == null)
			return;
		mark.setBTM(btm);
		db.save(mark);
		notifyObservers();
	}

	@Override
	public int getDTM(UUID id) {
		IMark mark = db.get(id);
		if (mark == null)
			return -1;
		return mark.getDTM();
	}

	@Override
	public void setDTM(UUID id, int dtm) {
		IMark mark = db.get(id);
		if (mark == null)
			return;
		mark.setDTM(dtm);
		db.save(mark);
		notifyObservers();
	}

	@Override
	public int getCOG(UUID id) {
		IMark mark = db.get(id);
		if (mark == null)
			return -1;
		return mark.getCOG();
	}

	@Override
	public void setCOG(UUID id, int cog) {
		IMark mark = db.get(id);
		if (mark == null)
			return;
		mark.setCOG(cog);
		db.save(mark);
		notifyObservers();
	}

	@Override
	public int getSOG(UUID id) {
		IMark mark = db.get(id);
		if (mark == null)
			return -1;
		return mark.getSOG();
	}

	@Override
	public void setSOG(UUID id, int sog) {
		IMark mark = db.get(id);
		if (mark == null)
			return;
		mark.setSOG(sog);
		db.save(mark);
		notifyObservers();
	}

	@Override
	public long getDate(UUID id) {
		IMark mark = db.get(id);
		if (mark == null)
			return -1;
		return mark.getDate();
	}

	@Override
	public void setDate(UUID id, long date) {
		IMark mark = db.get(id);
		if (mark == null)
			return;
		mark.setDate(date);
		db.save(mark);
		notifyObservers();
	}

	@Override
	public boolean isRouteMark(UUID id) {
		IMark mark = db.get(id);
		if (mark == null)
			return false;
		return mark.isRouteMark();
	}

	@Override
	public void setIsRouteMark(UUID id, boolean isRouteMark) {
		IMark mark = db.get(id);
		if (mark == null)
			return;
		mark.setIsRouteMark(isRouteMark);
		db.save(mark);
		notifyObservers();
	}

	@Override
	public void deleteMark(UUID id) {
		db.delete(id);
		notifyObservers();
	}

	@Override
	public final void closeDB() {
		db.close();
		logger.info("MarkController", "Database closed");
	}

	@Override
	public List<UUID> getMarks() {
		List<UUID> list = new LinkedList<UUID>();
		List<IMark> marks = db.loadAll();
		for (IMark mark : marks) {
			if (!mark.isRouteMark()) // only normal Marks
				list.add(mark.getUUID());
		}
		return list;
	}

	@Override
	public UUID newMark(double longitude, double latitude) {
		UUID newMark = db.create();
		setDate(newMark, System.currentTimeMillis());
		setIsRouteMark(newMark, false);
		setLatitude(newMark, latitude);
		setLongitude(newMark, longitude);
		notifyObservers();
		return newMark;
	}

	@Override
	public UUID newRouteMark(double longitude, double latitude) {
		UUID newMark = db.create();
		setDate(newMark, System.currentTimeMillis());
		setIsRouteMark(newMark, true);
		setLatitude(newMark, latitude);
		setLongitude(newMark, longitude);
		notifyObservers();
		return newMark;
	}

	@Override
	public String getString(UUID id) {
		return "ID = \t" + id + "\nName = \t" + getName(id) + "\nLatitude = \t"
				+ getLatitude(id) + "\nLongitude = \t" + getLongitude(id)
				+ "\nCOG = \t" + getCOG(id) + "\nSOG = \t" + getSOG(id)
				+ "\nBTM = \t" + getBTM(id) + "\nDTM = \t" + getDTM(id)
				+ "\nDate = \t" + getDate(id) + "\nNotes = \t" + getNote(id);
	}

	@Override
	public IMark getMark(UUID markId) {
		return db.get(markId);
	}

	@Override
	public List<IMark> getAllMarks() {
		return db.loadAll();
	}

	@Override
	public boolean saveMark(IMark mark) {
		return db.save(mark);
	}
}
