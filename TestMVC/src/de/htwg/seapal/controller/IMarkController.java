package de.htwg.seapal.controller;

import java.util.List;
import java.util.UUID;

import de.htwg.seapal.observer.IObservable;

public interface IMarkController extends IObservable {

	String getName(UUID id);

	void setName(UUID id, String name);

	double getLatitude(UUID id);

	void setLatitude(UUID id, double latitute);

	double getLongitude(UUID id);

	void setLongitude(UUID id, double longitude);

	String getNote(UUID id);

	void setNote(UUID id, String note);

	int getBTM(UUID id);

	void setBTM(UUID id, int btm);

	int getDTM(UUID id);

	void setDTM(UUID id, int dtm);

	int getCOG(UUID id);

	void setCOG(UUID id, int cog);

	int getSOG(UUID id);

	void setSOG(UUID id, int sog);

	long getDate(UUID id);

	void setDate(UUID id, long date);

	boolean isRouteMark(UUID id);

	void setIsRouteMark(UUID id, boolean isRouteMark);

	void deleteMark(UUID id);

	void closeDB();

	List<UUID> getMarks();

	UUID getMark(UUID id);

	UUID newMark();

	String getString(UUID id);

}
