package de.htwg.seapal.model.impl;

import java.util.Calendar;
import java.util.UUID;

import de.htwg.seapal.model.IWaypoint;

public class Waypoint implements IWaypoint {

	private UUID id;
	private String name;
	private double latitude;
	private double longitude;
	private Calendar date;
	private String note;
	private int btm;
	private int dtm;
	private int cog;
	private int sog;
	private UUID headedFor; // mark
	private Maneuver maneuver;
	private ForeSail foreSail;
	private MainSail mainSail;
	private UUID trip;

	public Waypoint() {
		id = UUID.randomUUID();
		maneuver = Maneuver.NONE;
		foreSail = ForeSail.NONE;
		mainSail = MainSail.NONE;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getNote() {
		return note;
	}

	@Override
	public int getBTM() {
		return btm;
	}

	@Override
	public int getDTM() {
		return dtm;
	}

	@Override
	public int getCOG() {
		return cog;
	}

	@Override
	public int getSOG() {
		return sog;
	}

	@Override
	public UUID getHeadedFor() {
		return headedFor;
	}

	@Override
	public Maneuver getManeuver() {
		return maneuver;
	}

	@Override
	public ForeSail getForesail() {
		return foreSail;
	}

	@Override
	public MainSail getMainsail() {
		return mainSail;
	}

	@Override
	public void setForesail(final ForeSail foreSail) {
		this.foreSail = foreSail;
	}

	@Override
	public void setName(final String name) {
		this.name = name;
	}

	@Override
	public void setNote(final String note) {
		this.note = note;
	}

	@Override
	public void setBTM(final int btm) {
		this.btm = btm;
	}

	@Override
	public void setDTM(final int dtm) {
		this.dtm = dtm;
	}

	@Override
	public void setCOG(final int cog) {
		this.cog = cog;
	}

	@Override
	public void setSOG(final int sog) {
		this.sog = sog;
	}

	@Override
	public void setHeadedFor(final UUID headedFor) {
		this.headedFor = headedFor;
	}

	@Override
	public void setManeuver(final Maneuver maneuver) {
		this.maneuver = maneuver;
	}

	@Override
	public void setMainsail(final MainSail mainSail) {
		this.mainSail = mainSail;
	}

	@Override
	public String toString() {
		return "Waypoint [id=" + id + ", name=" + name + ", latitude="
				+ latitude + ", longitude=" + longitude + ", date=" + date
				+ ", note=" + note + ", btm=" + btm + ", dtm=" + dtm + ", cog="
				+ cog + ", sog=" + sog + ", headedFor=" + headedFor
				+ ", maneuver=" + maneuver + ", foreSail=" + foreSail
				+ ", mainSail=" + mainSail + ", trip=" + trip + "]";
	}

	@Override
	public UUID getId() {
		return id;
	}

	@Override
	public UUID getTrip() {
		return trip;
	}

	@Override
	public void setTrip(UUID trip) {
		this.trip = trip;
	}

	@Override
	public Calendar getDate() {
		return date;
	}

	@Override
	public void setDate(Calendar date) {
		this.date = date;
	}

	@Override
	public double getLatitude() {
		return latitude;
	}

	@Override
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	@Override
	public double getLongitude() {
		return longitude;
	}

	@Override
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

}