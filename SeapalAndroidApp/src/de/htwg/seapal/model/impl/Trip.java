package de.htwg.seapal.model.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import de.htwg.seapal.model.ITrip;
import de.htwg.seapal.model.ModelDocument;

public class Trip extends ModelDocument implements ITrip {

	/**
	 * Serial version UID for serialization.
	 */
	private static final long serialVersionUID = 1L;

	private String name;
	private String startLocation;
	private String endLocation;
	private String skipper; // UUID Person
	private List<String> crew; // UUID Person ?/ or just Name TODO
	private Long startTime; // unix timestamp
	private Long endTime; // unix timestamp
	private Long duration; // TODO do we need this ? could be calculated
	private Integer motor;
	private Double fuel;
	private String notes;
	private String boat; // UUID Boat

	public Trip() {
		setId(UUID.randomUUID().toString());
		this.crew = new ArrayList<String>();
		this.skipper = UUID.randomUUID().toString();
	}

	public Trip(ITrip t) {
		setId(t.getId());
		this.crew = new ArrayList<String>();

		this.name = t.getName();
		this.startLocation = t.getStartLocation();
		this.endLocation = t.getEndLocation();
		this.skipper = t.getSkipper();
		this.crew = t.getCrewMembers();
		this.startTime = t.getStartTime();
		this.endTime = t.getEndTime();
		this.duration = t.getDuration();
		this.motor = t.getMotor();
		this.fuel = t.getFuel();
		this.notes = t.getNotes();
		this.boat = t.getBoat();
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
	public void setSkipper(String skipper) {
		this.skipper = skipper;
	}

	@Override
	public String getSkipper() {
		return skipper;
	}

	@Override
	public void addCrewMember(String crewMember) {
		this.crew.add(crewMember);
	}

	@Override
	public List<String> getCrewMembers() {
		return crew;
	}

	@Override
	public void setStartTime(Long start) {
		this.startTime = start;
	}

	@Override
	public Long getStartTime() {
		return startTime;
	}

	@Override
	public void setEndTime(Long end) {
		this.endTime = end;
	}

	@Override
	public Long getEndTime() {
		return endTime;
	}

	@Override
	public void setDuration(Long timeInSeconds) {
		this.duration = timeInSeconds;
	}

	@Override
	public Long getDuration() {
		return duration;
	}

	@Override
	public void setMotor(Integer motor) {
		this.motor = motor;
	}

	@Override
	public Integer getMotor() {
		return motor;
	}

	@Override
	public void setFuel(Double percent) {
		this.fuel = percent;
	}

	@Override
	public Double getFuel() {
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
	public String getBoat() {
		return boat;
	}

	@Override
	public void setBoat(String boat) {
		this.boat = boat;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}
	
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}
}