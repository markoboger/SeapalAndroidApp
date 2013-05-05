package de.htwg.seapal.model.impl;

import java.util.UUID;

import de.htwg.seapal.model.IMark;

public class Mark implements IMark {

	private String id;
	private String name;
	private double latitude;
	private double longitude;
	private int btm;
	private int dtm;
	private int cog;
	private int sog;
	private String note;
	private long date;
	private boolean isRouteMark;

	// private String markType;
	// private Boolean isDay;
	// private Boolean isNight;
	// private String function;

	public Mark() {
		id = UUID.randomUUID().toString();
	}

	@Override
	public UUID getId() {
		return UUID.fromString(id);
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
	public double getLatitude() {
		return this.latitude;
	}

	@Override
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	@Override
	public double getLongitude() {
		return this.longitude;
	}

	@Override
	public void setLongitude(double longitude) {
		this.longitude = longitude;
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
	public int getBTM() {
		return btm;
	}

	@Override
	public void setBTM(int btm) {
		this.btm = btm;
	}

	@Override
	public int getDTM() {
		return dtm;
	}

	@Override
	public void setDTM(int dtm) {
		this.dtm = dtm;
	}

	@Override
	public int getCOG() {
		return cog;
	}

	@Override
	public void setCOG(int cog) {
		this.cog = cog;
	}

	@Override
	public int getSOG() {
		return sog;
	}

	@Override
	public void setSOG(int sog) {
		this.sog = sog;
	}

	@Override
	public long getDate() {
		return date;
	}

	@Override
	public void setDate(long date) {
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

	// @Override
	// public String getMarkType() {
	// return this.markType;
	// }
	//
	// @Override
	// public void setMarkType(String type) {
	// this.markType = type;
	// }
	//
	// @Override
	// public Boolean getIsDay() {
	// return this.isDay;
	// }
	//
	// @Override
	// public void setIsDay(Boolean val) {
	// this.isDay = val;
	// }
	//
	// @Override
	// public Boolean getIsNight() {
	// return this.isNight;
	// }
	//
	// @Override
	// public void setIsNight(Boolean val) {
	// this.isNight = val;
	// }
	//
	// @Override
	// public Color getColor() {
	// return this.color;
	// }
	// @Override
	// public void setColor(Color color) {
	// this.color = color;
	// }
	//
	// @Override
	// public String getFunktion() {
	// return this.function;
	// }
	//
	// @Override
	// public void setFunction(String function) {
	// this.function = function;
	// }

}
