package de.htwg.seapal.model.impl;

import java.util.UUID;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

import de.htwg.seapal.model.IWaypoint;
import de.htwg.seapal.model.ModelDocument;

public class Waypoint extends ModelDocument implements IWaypoint {

	/**
	 * Serial version UID for serialization.
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private Double latitude;
	private Double Longitude;
	private Long date; // unix timestamp
	private String note;
	private Integer btm;
	private Integer dtm;
	private Integer cog;
	private Integer sog;
	private String headedFor; // UUID Mark
	private Maneuver maneuver;
	private ForeSail foreSail;
	private MainSail mainSail;
	private String trip; // UUID Trip

	public Waypoint() {
		setId(UUID.randomUUID().toString());
		maneuver = Maneuver.NONE;
		foreSail = ForeSail.NONE;
		mainSail = MainSail.NONE;
	}
	
	@JsonCreator
	public Waypoint(@JsonProperty("maneuver") Maneuver m, 
			@JsonProperty("foresail") ForeSail f,
			@JsonProperty("mainsail") MainSail msail) 
	{
		setId(UUID.randomUUID().toString());
		maneuver = m;
		foreSail = f;
		mainSail = msail;
	}
	
	public Waypoint(IWaypoint w) 
	{
		setId(w.getId());
		this.name = w.getName();
		this.latitude = w.getLatitude();
		this.Longitude = w.getLongitude();
		this.date = w.getDate();
		this.note = w.getNote();
		this.btm = w.getBTM();
		this.dtm = w.getDTM();
		this.cog = w.getCOG();
		this.sog = w.getSOG();
		this.headedFor = w.getHeadedFor();
		this.maneuver = w.getManeuver();
		this.foreSail = w.getForesail();
		this.mainSail = w.getMainsail();
		this.trip = w.getTrip();
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
	public Integer getBTM() {
		return btm;
	}

	@Override
	public Integer getDTM() {
		return dtm;
	}

	@Override
	public Integer getCOG() {
		return cog;
	}

	@Override
	public Integer getSOG() {
		return sog;
	}

	@Override
	public String getHeadedFor() {
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
	public void setBTM(final Integer btm) {
		this.btm = btm;
	}

	@Override
	public void setDTM(final Integer dtm) {
		this.dtm = dtm;
	}

	@Override
	public void setCOG(final Integer cog) {
		this.cog = cog;
	}

	@Override
	public void setSOG(final Integer sog) {
		this.sog = sog;
	}

	@Override
	public void setHeadedFor(final UUID headedFor) {
		this.headedFor = headedFor.toString();
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
	public String getTrip() {
		return trip;
	}

	@Override
	public void setTrip(String trip) {
		this.trip = trip.toString();
	}

	@Override
	public Long getDate() {
		return date;
	}

	@Override
	public void setDate(Long date) {
		this.date = date;
	}

	@Override
	public Double getLatitude() {
		return latitude;
	}

	@Override
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	@Override
	public Double getLongitude() {
		return Longitude;
	}

	@Override
	public void setLongitude(Double Longitude) {
		this.Longitude = Longitude;
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