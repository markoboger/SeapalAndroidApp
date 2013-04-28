package de.htwg.seapal.model.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import de.htwg.seapal.model.ITrip;

public class Trip implements ITrip {

	private UUID id;

	private String name;
	private String startLocation;
	private String endLocation;
	private UUID skipper; // Person
	private List<UUID> crew; // Person
	private Date startTime;
	private Date endTime;
	private long duration;
	private int motor;
	private double fuel;
	private String notes;
	private UUID boat;

	public Trip() {
		this.id = UUID.randomUUID();
		this.crew = new ArrayList<UUID>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void setStartLocation(String location) {
		this.startLocation = location;
	}

	@Override
	public String getStartLocation() {
		return startLocation;
	}

	@Override
	public void setEndLocation(String location) {
		this.endLocation = location;
	}

	@Override
	public String getEndLocation() {
		return endLocation;
	}

	@Override
	public void setSkipper(UUID skipper) {
		this.skipper = skipper;
	}

	@Override
	public UUID getSkipper() {
		return skipper;
	}

	@Override
	public void addCrewMember(UUID crewMember) {
		this.crew.add(crewMember);
	}

	@Override
	public List<UUID> getCrewMembers() {
		return crew;
	}

	@Override
	public void setStartTime(Date start) {
		this.startTime = start;
	}

	@Override
	public Date getStartTime() {
		return startTime;
	}

	@Override
	public void setEndTime(Date end) {
		this.endTime = end;
	}

	@Override
	public Date getEndTime() {
		return endTime;
	}

	@Override
	public void setDuration(long timeInSeconds) {
		this.duration = timeInSeconds;
	}

	@Override
	public long getDuration() {
		return duration;
	}

	@Override
	public void setMotor(int motor) {
		this.motor = motor;
	}

	@Override
	public int getMotor() {
		return motor;
	}

	@Override
	public void setFuel(double percent) {
		this.fuel = percent;
	}

	@Override
	public double getFuel() {
		return fuel;
	}

	@Override
	public void setNotes(String text) {
		this.notes = text;
	}

	@Override
	public String getNotes() {
		return notes;
	}

	@Override
	public UUID getId() {
		return id;
	}

	@Override
	public UUID getBoat() {
		return boat;
	}

	@Override
	public void setBoat(UUID boat) {
		this.boat = boat;
	}

	@Override
	public String toString() {
		return "Trip [id=" + id + ", name=" + name + ", startLocation="
				+ startLocation + ", endLocation=" + endLocation + ", skipper="
				+ skipper + ", crew=" + crew + ", startTime=" + startTime
				+ ", endTime=" + endTime + ", duration=" + duration
				+ ", motor=" + motor + ", fuel=" + fuel + ", notes=" + notes
				+ ", boat=" + boat + "]";
	}
}