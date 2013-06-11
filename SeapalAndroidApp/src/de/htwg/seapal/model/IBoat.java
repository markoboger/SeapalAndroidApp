package de.htwg.seapal.model;


public interface IBoat extends IModel {

	String getBoatName();

	void setBoatName(String boatName);

	String getRegisterNr();

	void setRegisterNr(String registerNr);

	String getSailSign();

	void setSailSign(String sailSign);

	String getHomePort();

	void setHomePort(String homePort);

	String getYachtclub();

	void setYachtclub(String yachtclub);

	String getOwner(); // Person

	void setOwner(String owner); // Person

	String getInsurance();

	void setInsurance(String insurance);

	String getCallSign();

	void setCallSign(String callSign);

	String getType();

	void setType(String type);

	String getConstructor();

	void setConstructor(String constructor);

	Double getLength();

	void setLength(Double length);

	Double getWidth();

	void setWidth(Double width);

	Double getDraft();

	void setDraft(Double draft);

	Double getMastHeight();

	void setMastHeight(Double mastHeight);

	Double getDisplacement();

	void setDisplacement(Double displacement);

	String getRigging();

	void setRigging(String rigging);

	Integer getYearOfConstruction();

	void setYearOfConstruction(Integer yearOfConstruction);

	String getMotor();

	void setMotor(String motor);

	Double getTankSize();

	void setTankSize(Double tankSize);

	Double getWasteWaterTankSize();

	void setWasteWaterTankSize(Double wasteWaterTankSize);

	Double getFreshWaterTankSize();

	void setFreshWaterTankSize(Double freshWaterTankSize);

	Double getMainSailSize();

	void setMainSailSize(Double mainSailSize);

	Double getGenuaSize();

	void setGenuaSize(Double genuaSize);

	Double getSpiSize();

	void setSpiSize(Double spiSize);

	void setUser(String userId);

	String getUser();
}
