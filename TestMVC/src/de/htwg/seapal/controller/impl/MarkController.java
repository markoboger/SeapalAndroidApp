package de.htwg.seapal.controller.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import de.htwg.seapal.controller.IMarkController;
import de.htwg.seapal.database.IMarkDatabase;
import de.htwg.seapal.model.IMark;
import de.htwg.seapal.observer.Observable;

public class MarkController extends Observable implements IMarkController {

	private IMarkDatabase db;

	public MarkController(IMarkDatabase db) {
		this.db = db;
	}

	@Override
	public String getName(UUID id) {
		IMark mark = db.getMark(id);
		if (mark == null)
			return null;
		return mark.getName();
	}

	@Override
	public void setName(UUID id, String name) {
		IMark mark = db.getMark(id);
		if (mark == null)
			return;
		mark.setName(name);
		db.saveMark(mark);
		notifyObservers();
	}

	@Override
	public double getLatitude(UUID id) {
		IMark mark = db.getMark(id);
		if (mark == null)
			return -1;
		return mark.getLatitude();
	}

	@Override
	public void setLatitude(UUID id, double latitute) {
		IMark mark = db.getMark(id);
		if (mark == null)
			return;
		mark.setLatitude(latitute);
		db.saveMark(mark);
		notifyObservers();
	}

	@Override
	public double getLongitude(UUID id) {
		IMark mark = db.getMark(id);
		if (mark == null)
			return -1;
		return mark.getLongitude();
	}

	@Override
	public void setLongitude(UUID id, double longitude) {
		IMark mark = db.getMark(id);
		if (mark == null)
			return;
		mark.setLongitude(longitude);
		db.saveMark(mark);
		notifyObservers();
	}

	@Override
	public String getNote(UUID id) {
		IMark mark = db.getMark(id);
		if (mark == null)
			return null;
		return mark.getNote();
	}

	@Override
	public void setNote(UUID id, String note) {
		IMark mark = db.getMark(id);
		if (mark == null)
			return;
		mark.setNote(note);
		db.saveMark(mark);
		notifyObservers();
	}

	@Override
	public int getBTM(UUID id) {
		IMark mark = db.getMark(id);
		if (mark == null)
			return -1;
		return mark.getBTM();
	}

	@Override
	public void setBTM(UUID id, int btm) {
		IMark mark = db.getMark(id);
		if (mark == null)
			return;
		mark.setBTM(btm);
		db.saveMark(mark);
		notifyObservers();
	}

	@Override
	public int getDTM(UUID id) {
		IMark mark = db.getMark(id);
		if (mark == null)
			return -1;
		return mark.getDTM();
	}

	@Override
	public void setDTM(UUID id, int dtm) {
		IMark mark = db.getMark(id);
		if (mark == null)
			return;
		mark.setDTM(dtm);
		db.saveMark(mark);
		notifyObservers();
	}

	@Override
	public int getCOG(UUID id) {
		IMark mark = db.getMark(id);
		if (mark == null)
			return -1;
		return mark.getCOG();
	}

	@Override
	public void setCOG(UUID id, int cog) {
		IMark mark = db.getMark(id);
		if (mark == null)
			return;
		mark.setCOG(cog);
		db.saveMark(mark);
		notifyObservers();
	}

	@Override
	public int getSOG(UUID id) {
		IMark mark = db.getMark(id);
		if (mark == null)
			return -1;
		return mark.getSOG();
	}

	@Override
	public void setSOG(UUID id, int sog) {
		IMark mark = db.getMark(id);
		if (mark == null)
			return;
		mark.setSOG(sog);
		db.saveMark(mark);
		notifyObservers();
	}

	@Override
	public long getDate(UUID id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setDate(UUID id, long date) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isRouteMark(UUID id) {
		IMark mark = db.getMark(id);
		if (mark == null)
			return false;
		return mark.isRouteMark();
	}

	@Override
	public void setIsRouteMark(UUID id, boolean isRouteMark) {
		IMark mark = db.getMark(id);
		if (mark == null)
			return;
		mark.setIsRouteMark(isRouteMark);
		db.saveMark(mark);
		notifyObservers();
	}

	@Override
	public void deleteMark(UUID id) {
		db.deleteMark(id);
		notifyObservers();
	}

	@Override
	public void closeDB() {
		db.closeDB();
	}

	@Override
	public List<UUID> getMarks() {
		List<UUID> list = new LinkedList<UUID>();
		List<IMark> marks = db.getMarks();
		for (IMark mark : marks) {
			if (!mark.isRouteMark())
				list.add(mark.getId());
		}
		return list;
	}

	@Override
	public UUID getMark(UUID id) {
		IMark mark = db.getMark(id);
		if (mark == null)
			return null;
		return mark.getId();
	}

	@Override
	public UUID newMark() {
		UUID newMark = db.newMark();
		notifyObservers();
		return newMark;
	}

	@Override
	public String getString(UUID id) {
		return "ID = \t" + id + "\nName = \t" + getName(id)
				+ "\nLatitude = \t" + getLatitude(id) + "\nLongitude = \t"
				+ getLongitude(id) + "\nCOG = \t" + getCOG(id) + "\nSOG = \t"
				+ getSOG(id) + "\nBTM = \t" + getBTM(id) + "\nDTM = \t"
				+ getDTM(id) + "\nDate = \t" + getDate(id) + "\nNotes = \t"
				+ getNote(id);
	}
}
