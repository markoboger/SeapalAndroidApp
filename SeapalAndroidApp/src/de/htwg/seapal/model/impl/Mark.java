package de.htwg.seapal.model.impl;

import java.util.UUID;

import de.htwg.seapal.model.IMark;
import de.htwg.seapal.model.ModelDocument;

public class Mark extends ModelDocument implements IMark {

	/**
	 * Serial version UID for serialization.
	 */
	private static final long serialVersionUID = 1L;

	private String user; // UUID user

	private String name;
	private Double latitude;
	private Double Longitude;
	private Integer btm;
	private Integer dtm;
	private Integer cog;
	private Integer sog;
	private String note;
	private Long date;
	private boolean isRouteMark;

	public Mark() {
		setId(UUID.randomUUID().toString());
		this.user = "";
		this.latitude = 0D;
		this.Longitude = 0D;
		this.btm = 0;
		this.dtm = 0;
		this.cog = 0;
		this.sog = 0;
		this.note = "";
		this.date = 0L;
		this.isRouteMark = false;
	}

	public Mark(IMark m) {
		setId(m.getId());
		this.user = m.getUser();

		this.name = m.getName();
		this.latitude = m.getLatitude();
		this.Longitude = m.getLongitude();
		this.btm = m.getBTM();
		this.dtm = m.getDTM();
		this.cog = m.getCOG();
		this.sog = m.getSOG();
		this.note = m.getNote();
		this.date = m.getDate();
		this.isRouteMark = m.isRouteMark();
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public Double getLatitude() {
		return this.latitude;
	}

	@Override
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	@Override
	public Double getLongitude() {
		return this.Longitude;
	}

	@Override
	public void setLongitude(Double Longitude) {
		this.Longitude = Longitude;
	}

	@Override
	public String getNote() {
		return note;
	}

	@Override
	public void setNote(String note) {
		this.note = note;
	}

	@Override
	public Integer getBTM() {
		return btm;
	}

	@Override
	public void setBTM(Integer btm) {
		this.btm = btm;
	}

	@Override
	public Integer getDTM() {
		return dtm;
	}

	@Override
	public void setDTM(Integer dtm) {
		this.dtm = dtm;
	}

	@Override
	public Integer getCOG() {
		return cog;
	}

	@Override
	public void setCOG(Integer cog) {
		this.cog = cog;
	}

	@Override
	public Integer getSOG() {
		return sog;
	}

	@Override
	public void setSOG(Integer sog) {
		this.sog = sog;
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
	public boolean isRouteMark() {
		return isRouteMark;
	}

	@Override
	public void setIsRouteMark(boolean isRouteMark) {
		this.isRouteMark = isRouteMark;
	}

	@Override
	public String getUser() {
		return user;
	}

	@Override
	public void setUser(String user) {
		this.user = user;
	}

}
