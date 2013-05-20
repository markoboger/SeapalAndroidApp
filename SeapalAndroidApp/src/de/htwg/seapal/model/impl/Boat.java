package de.htwg.seapal.model.impl;

import java.util.UUID;

import de.htwg.seapal.model.IBoat;
import de.htwg.seapal.model.ModelDocument;

public class Boat extends ModelDocument implements IBoat {

	/**
	 * Serial version UID for serialization.
	 */
	private static final long serialVersionUID = 1L;

	private String user; // person UUID

	private String boatName;
	private String registerNr;
	private String sailSign;
	private String homePort;
	private String yachtclub;
	private String owner;
	private String insurance;
	private String callSign;
	private String type;
	private String constructor;
	private Double length;
	private Double width;
	private Double draft;
	private Double mastHeight;
	private Double displacement;
	private String rigging;
	private Integer yearOfConstruction;
	private String motor;
	private Double tankSize;
	private Double wasteWaterTankSize;
	private Double freshWaterTankSize;
	private Double mainSailSize;
	private Double genuaSize;
	private Double spiSize;

	public Boat() {
		setId(UUID.randomUUID().toString());
		this.user = "";
		this.boatName = "";
		this.registerNr = "";
		this.sailSign = "";
		this.homePort = "";
		this.yachtclub = "";
		this.owner = null;
		this.insurance = "";
		this.callSign = "";
		this.type = "";
		this.constructor = "";
		this.length = 0D;
		this.width = 0D;
		this.draft = 0D;
		this.mastHeight = 0D;
		this.displacement = 0D;
		this.rigging = "";
		this.yearOfConstruction = 0;
		this.motor = "";
		this.tankSize = 0D;
		this.wasteWaterTankSize = 0D;
		this.freshWaterTankSize = 0D;
		this.mainSailSize = 0D;
		this.genuaSize = 0D;
		this.spiSize = 0D;
	}

	public Boat(IBoat boat) {
		setId(boat.getId());
		this.user = boat.getUser();
		this.boatName = boat.getBoatName();
		this.registerNr = boat.getRegisterNr();
		this.sailSign = boat.getSailSign();
		this.homePort = boat.getHomePort();
		this.yachtclub = boat.getYachtclub();
		this.owner = boat.getOwner().toString();
		this.insurance = boat.getInsurance();
		this.callSign = boat.getCallSign();
		this.type = boat.getType();
		this.constructor = boat.getConstructor();
		this.length = boat.getLength();
		this.width = boat.getLength();
		this.draft = boat.getDraft();
		this.mastHeight = boat.getMastHeight();
		this.displacement = boat.getDisplacement();
		this.rigging = boat.getRigging();
		this.yearOfConstruction = boat.getYearOfConstruction();
		this.motor = boat.getMotor();
		this.tankSize = boat.getFreshWaterTankSize();
		this.wasteWaterTankSize = boat.getFreshWaterTankSize();
		this.freshWaterTankSize = boat.getFreshWaterTankSize();
		this.mainSailSize = boat.getMainSailSize();
		this.genuaSize = boat.getGenuaSize();
		this.spiSize = boat.getGenuaSize();
	}

	@Override
	public String getBoatName() {
		return boatName;
	}

	@Override
	public void setBoatName(String boatName) {
		this.boatName = boatName;
	}

	@Override
	public String getRegisterNr() {
		return registerNr;
	}

	@Override
	public void setRegisterNr(String registerNr) {
		this.registerNr = registerNr;
	}

	@Override
	public String getSailSign() {
		return sailSign;
	}

	@Override
	public void setSailSign(String sailSign) {
		this.sailSign = sailSign;
	}

	@Override
	public String getHomePort() {
		return homePort;
	}

	@Override
	public void setHomePort(String homePort) {
		this.homePort = homePort;
	}

	@Override
	public String getYachtclub() {
		return yachtclub;
	}

	@Override
	public void setYachtclub(String yachtclub) {
		this.yachtclub = yachtclub;
	}

	@Override
	public UUID getOwner() { // Person
		if(owner == null)
			return null;
		return UUID.fromString(owner);
	}

	@Override
	public void setOwner(UUID owner) { // Person
		this.owner = owner.toString();
	}

	@Override
	public String getInsurance() {
		return insurance;
	}

	@Override
	public void setInsurance(String insurance) {
		this.insurance = insurance;
	}

	@Override
	public String getCallSign() {
		return callSign;
	}

	@Override
	public void setCallSign(String callSign) {
		this.callSign = callSign;
	}

	@Override
	public String getType() {
		return type;
	}

	@Override
	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String getConstructor() {
		return constructor;
	}

	@Override
	public void setConstructor(String constructor) {
		this.constructor = constructor;
	}

	@Override
	public Double getLength() {
		return length;
	}

	@Override
	public void setLength(Double length) {
		this.length = length;
	}

	@Override
	public Double getWidth() {
		return width;
	}

	@Override
	public void setWidth(Double width) {
		this.width = width;
	}

	@Override
	public Double getDraft() {
		return draft;
	}

	@Override
	public void setDraft(Double draft) {
		this.draft = draft;
	}

	@Override
	public Double getMastHeight() {
		return mastHeight;
	}

	@Override
	public void setMastHeight(Double mastHeight) {
		this.mastHeight = mastHeight;
	}

	@Override
	public Double getDisplacement() {
		return displacement;
	}

	@Override
	public void setDisplacement(Double displacement) {
		this.displacement = displacement;
	}

	@Override
	public String getRigging() {
		return rigging;
	}

	@Override
	public void setRigging(String rigging) {
		this.rigging = rigging;
	}

	@Override
	public Integer getYearOfConstruction() {
		return yearOfConstruction;
	}

	@Override
	public void setYearOfConstruction(Integer yearOfConstruction) {
		this.yearOfConstruction = yearOfConstruction;
	}

	@Override
	public String getMotor() {
		return motor;
	}

	@Override
	public void setMotor(String motor) {
		this.motor = motor;
	}

	@Override
	public Double getTankSize() {
		return tankSize;
	}

	@Override
	public void setTankSize(Double tankSize) {
		this.tankSize = tankSize;
	}

	@Override
	public Double getWasteWaterTankSize() {
		return wasteWaterTankSize;
	}

	@Override
	public void setWasteWaterTankSize(Double wasteWaterTankSize) {
		this.wasteWaterTankSize = wasteWaterTankSize;
	}

	@Override
	public Double getFreshWaterTankSize() {
		return freshWaterTankSize;
	}

	@Override
	public void setFreshWaterTankSize(Double freshWaterTankSize) {
		this.freshWaterTankSize = freshWaterTankSize;
	}

	@Override
	public Double getMainSailSize() {
		return mainSailSize;
	}

	@Override
	public void setMainSailSize(Double mainSailSize) {
		this.mainSailSize = mainSailSize;
	}

	@Override
	public Double getGenuaSize() {
		return genuaSize;
	}

	@Override
	public void setGenuaSize(Double genuaSize) {
		this.genuaSize = genuaSize;
	}

	@Override
	public Double getSpiSize() {
		return spiSize;
	}

	@Override
	public void setSpiSize(Double spiSize) {
		this.spiSize = spiSize;
	}

	@Override
	public String getUser() {
		return user;
	}

	@Override
	public void setUser(String userId) {
		this.user = userId;
	}
}
