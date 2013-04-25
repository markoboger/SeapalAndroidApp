package en.htwg.seapal.model.impl;

import java.util.UUID;

import en.htwg.seapal.model.IWaypoint;

public class Waypoint implements IWaypoint {

	private UUID id;
	private String name;
	private String position;
	private String note;
	private int btm;
	private int dtm;
	private int cog;
	private int sog;
	private UUID headedFor;	// mark
	private Maneuver maneuver;
	private ForeSail foreSail;
	private MainSail mainSail;

	public Waypoint() {
		id = UUID.randomUUID();
		name = "";
		position = "0°E 0°N";
		note = "";
		maneuver = Maneuver.NONE;
		foreSail = ForeSail.NONE;
		mainSail = MainSail.NONE;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getPosition() {
		return position;
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
	public void setPosition(final String position) {
		this.position = position;
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
		StringBuilder sb = new StringBuilder("{");
		sb.append("id=").append(getId()).append("; name=").append(getName())
				.append("; pos=").append(getPosition()).append("; note=")
				.append(getNote()).append("; btm=").append(getBTM())
				.append("; dtm=").append(getDTM()).append("; cog=")
				.append(getCOG()).append("; sog=").append(getSOG())
				.append("; man=").append(getManeuver()).append("; fsail=")
				.append(getForesail()).append("; msail=").append(getMainsail())
				.append("; mark=").append(getHeadedFor()).append("}");
		return sb.toString();
	}

	@Override
	public UUID getId() {
		return id;
	}

}