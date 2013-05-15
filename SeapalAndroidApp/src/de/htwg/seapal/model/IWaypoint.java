package de.htwg.seapal.model;

import java.util.UUID;

public interface IWaypoint extends IModel {

	public enum Maneuver {
		NONE, TACK, JIBE, LAYTO, SET_SAILS, CHANGE_SAILS, SAILS_DOWN, REFF, ANKER_UP, ANKER_DOWN
	}

	public enum ForeSail {
		NONE, GENUA1, GENUA2, GENUA3, FOCK, STORM_FOCK, BISTER, SPINACKER
	}

	public enum MainSail {
		NONE, FULL, REEF1, REEF2
	}

	String getName();

	String getNote();

	Integer getBTM();

	Integer getDTM();

	Integer getCOG();

	Integer getSOG();

	UUID getHeadedFor();

	Maneuver getManeuver();

	ForeSail getForesail();

	MainSail getMainsail();

	void setForesail(ForeSail foreSail);

	void setName(String name);

	void setNote(String note);

	void setBTM(Integer btm);

	void setDTM(Integer dtm);

	void setCOG(Integer cog);

	void setSOG(Integer sog);

	void setHeadedFor(UUID mark);

	void setManeuver(Maneuver maneuver);

	void setMainsail(MainSail mainSail);

	UUID getTrip();

	void setTrip(UUID trip);

	Long getDate();

	void setDate(Long date);

	Double getLatitude();

	void setLatitude(Double latitude);

	Double getLongitude();

	void setLongitude(Double Longitude);
}