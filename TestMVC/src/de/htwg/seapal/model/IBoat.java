package de.htwg.seapal.model;

import java.util.UUID;

public interface IBoat {

    UUID getUUId();

    String getId();
    
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

//    UUID getOwner(); // Person
//
//    void setOwner(UUID owner); // Person

    String getInsurance();

    void setInsurance(String insurance);

    String getCallSign();

    void setCallSign(String callSign);

    String getType();

    void setType(String type);

    String getConstructor();

    void setConstructor(String constructor);

    double getLength();

    void setLength(double length);

    double getWidth();

    void setWidth(double width);

    double getDraft();

    void setDraft(double draft);

    double getMastHeight();

    void setMastHeight(double mastHeight);

    double getDisplacement();

    void setDisplacement(double displacement);

    String getRigging();

    void setRigging(String rigging);

    int getYearOfConstruction();

    void setYearOfConstruction(int yearOfConstruction);

    String getMotor();

    void setMotor(String motor);

    double getTankSize();

    void setTankSize(double tankSize);

    double getWasteWaterTankSize();

    void setWasteWaterTankSize(double wasteWaterTankSize);

    double getFreshWaterTankSize();

    void setFreshWaterTankSize(double freshWaterTankSize);

    double getMainSailSize();

    void setMainSailSize(double mainSailSize);

    double getGenuaSize();

    void setGenuaSize(double genuaSize);

    double getSpiSize();

    void setSpiSize(double spiSize);
}
