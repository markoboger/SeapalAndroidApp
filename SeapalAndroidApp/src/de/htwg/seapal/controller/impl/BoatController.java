package de.htwg.seapal.controller.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.google.inject.Inject;

import de.htwg.seapal.controller.IBoatController;
import de.htwg.seapal.database.IBoatDatabase;
import de.htwg.seapal.model.IBoat;
import de.htwg.seapal.utils.logging.ILogger;
import de.htwg.seapal.utils.observer.Observable;

public class BoatController extends Observable implements IBoatController {

	protected IBoatDatabase db;
	private final ILogger logger;

	@Inject
	public BoatController(IBoatDatabase db, ILogger logger) {
		this.db = db;
		this.logger = logger;
	}

	@Override
	public String getBoatName(final UUID id) {
		IBoat boat = db.get(id);
		if (boat == null)
			return null;
		return db.get(id).getBoatName();
	}

	@Override
	public void setBoatName(UUID id, String BoatName) {
		IBoat boat = db.get(id);
		if (boat == null)
			return;
		boat.setBoatName(BoatName);
		db.save(boat);
		notifyObservers();
	}

	@Override
	public String getRegisterNr(UUID id) {
		IBoat boat = db.get(id);
		if (boat == null)
			return null;
		return boat.getRegisterNr();
	}

	@Override
	public void setRegisterNr(UUID id, String registerNr) {
		IBoat boat = db.get(id);
		if (boat == null)
			return;
		boat.setRegisterNr(registerNr);
		db.save(boat);
		notifyObservers();
	}

	@Override
	public String getSailSign(UUID id) {
		IBoat boat = db.get(id);
		if (boat == null)
			return null;
		return boat.getSailSign();
	}

	@Override
	public void setSailSign(UUID id, String SailSign) {
		IBoat boat = db.get(id);
		if (boat == null)
			return;
		boat.setSailSign(SailSign);
		db.save(boat);
		notifyObservers();
	}

	@Override
	public String getHomePort(UUID id) {
		IBoat boat = db.get(id);
		if (boat == null)
			return null;
		return boat.getHomePort();
	}

	@Override
	public void setHomePort(UUID id, String HomePort) {
		IBoat boat = db.get(id);
		if (boat == null)
			return;
		boat.setHomePort(HomePort);
		db.save(boat);
		notifyObservers();
	}

	@Override
	public String getYachtclub(UUID id) {
		IBoat boat = db.get(id);
		if (boat == null)
			return null;
		return boat.getYachtclub();
	}

	@Override
	public void setYachtclub(UUID id, String yachtclub) {
		IBoat boat = db.get(id);
		if (boat == null)
			return;
		boat.setYachtclub(yachtclub);
		db.save(boat);
		notifyObservers();
	}

	@Override
	public UUID getOwner(UUID id) {
		IBoat boat = db.get(id);
		if (boat == null)
			return null;
		if(boat.getOwner().equals(""))
			return null;
		else
			return UUID.fromString(boat.getOwner());
	}

	@Override
	public void setOwner(UUID id, UUID owner) {
		IBoat boat = db.get(id);
		if (boat == null)
			return;
		boat.setOwner(owner.toString());
		db.save(boat);
		notifyObservers();
	}

	@Override
	public String getInsurance(UUID id) {
		IBoat boat = db.get(id);
		if (boat == null)
			return null;
		return boat.getInsurance();
	}

	@Override
	public void setInsurance(UUID id, String Insurance) {
		IBoat boat = db.get(id);
		if (boat == null)
			return;
		boat.setInsurance(Insurance);
		db.save(boat);
		notifyObservers();
	}

	@Override
	public String getCallSign(UUID id) {
		IBoat boat = db.get(id);
		if (boat == null)
			return null;
		return boat.getCallSign();
	}

	@Override
	public void setCallSign(UUID id, String CallSign) {
		IBoat boat = db.get(id);
		if (boat == null)
			return;
		boat.setCallSign(CallSign);
		db.save(boat);
		notifyObservers();
	}

	@Override
	public String getType(UUID id) {
		IBoat boat = db.get(id);
		if (boat == null)
			return null;
		return boat.getType();
	}

	@Override
	public void setType(UUID id, String Type) {
		IBoat boat = db.get(id);
		if (boat == null)
			return;
		boat.setType(Type);
		db.save(boat);
		notifyObservers();
	}

	@Override
	public String getConstructor(UUID id) {
		IBoat boat = db.get(id);
		if (boat == null)
			return null;
		return boat.getConstructor();
	}

	@Override
	public void setConstructor(UUID id, String Constructor) {
		IBoat boat = db.get(id);
		if (boat == null)
			return;
		boat.setConstructor(Constructor);
		db.save(boat);
		notifyObservers();
	}

	@Override
	public double getLength(UUID id) {
		IBoat boat = db.get(id);
		if (boat == null)
			return -1;
		return boat.getLength();
	}

	@Override
	public void setLength(UUID id, double Length) {
		IBoat boat = db.get(id);
		if (boat == null)
			return;
		boat.setLength(Length);
		db.save(boat);
		notifyObservers();
	}

	@Override
	public double getWidth(UUID id) {
		IBoat boat = db.get(id);
		if (boat == null)
			return -1;
		return boat.getWidth();
	}

	@Override
	public void setWidth(UUID id, double width) {
		IBoat boat = db.get(id);
		if (boat == null)
			return;
		boat.setWidth(width);
		db.save(boat);
		notifyObservers();
	}

	@Override
	public double getDraft(UUID id) {
		IBoat boat = db.get(id);
		if (boat == null)
			return -1;
		return boat.getDraft();
	}

	@Override
	public void setDraft(UUID id, double draft) {
		IBoat boat = db.get(id);
		if (boat == null)
			return;
		boat.setDraft(draft);
		db.save(boat);
		notifyObservers();
	}

	@Override
	public double getMastHeight(UUID id) {
		IBoat boat = db.get(id);
		if (boat == null)
			return -1;
		return boat.getMastHeight();
	}

	@Override
	public void setMastHeight(UUID id, double mastHeight) {
		IBoat boat = db.get(id);
		if (boat == null)
			return;
		boat.setMastHeight(mastHeight);
		db.save(boat);
		notifyObservers();
	}

	@Override
	public double getDisplacement(UUID id) {
		IBoat boat = db.get(id);
		if (boat == null)
			return -1;
		return boat.getDisplacement();
	}

	@Override
	public void setDisplacement(UUID id, double displacement) {
		IBoat boat = db.get(id);
		if (boat == null)
			return;
		boat.setDisplacement(displacement);
		db.save(boat);
		notifyObservers();
	}

	@Override
	public String getRigging(UUID id) {
		IBoat boat = db.get(id);
		if (boat == null)
			return null;
		return boat.getRigging();
	}

	@Override
	public void setRigging(UUID id, String rigging) {
		IBoat boat = db.get(id);
		if (boat == null)
			return;
		boat.setRigging(rigging);
		db.save(boat);
		notifyObservers();
	}

	@Override
	public int getYearOfConstruction(UUID id) {
		IBoat boat = db.get(id);
		if (boat == null)
			return -1;
		return boat.getYearOfConstruction();
	}

	@Override
	public void setYearOfConstruction(UUID id, int yearOfConstruction) {
		IBoat boat = db.get(id);
		if (boat == null)
			return;
		boat.setYearOfConstruction(yearOfConstruction);
		db.save(boat);
		notifyObservers();
	}

	@Override
	public String getMotor(UUID id) {
		IBoat boat = db.get(id);
		if (boat == null)
			return null;
		return boat.getMotor();
	}

	@Override
	public void setMotor(UUID id, String motor) {
		IBoat boat = db.get(id);
		if (boat == null)
			return;
		boat.setMotor(motor);
		db.save(boat);
		notifyObservers();
	}

	@Override
	public double getTankSize(UUID id) {
		IBoat boat = db.get(id);
		if (boat == null)
			return -1;
		return boat.getTankSize();
	}

	@Override
	public void setTankSize(UUID id, double tankSize) {
		IBoat boat = db.get(id);
		if (boat == null)
			return;
		boat.setTankSize(tankSize);
		db.save(boat);
		notifyObservers();
	}

	@Override
	public double getWasteWaterTankSize(UUID id) {
		IBoat boat = db.get(id);
		if (boat == null)
			return -1;
		return boat.getWasteWaterTankSize();
	}

	@Override
	public void setWasteWaterTankSize(UUID id, double wasteWaterTankSize) {
		IBoat boat = db.get(id);
		if (boat == null)
			return;
		boat.setWasteWaterTankSize(wasteWaterTankSize);
		db.save(boat);
		notifyObservers();
	}

	@Override
	public double getFreshWaterTankSize(UUID id) {
		IBoat boat = db.get(id);
		if (boat == null)
			return -1;
		return boat.getFreshWaterTankSize();
	}

	@Override
	public void setFreshWaterTankSize(UUID id, double freshWaterTankSize) {
		IBoat boat = db.get(id);
		if (boat == null)
			return;
		boat.setFreshWaterTankSize(freshWaterTankSize);
		db.save(boat);
		notifyObservers();
	}

	@Override
	public double getMainSailSize(UUID id) {
		IBoat boat = db.get(id);
		if (boat == null)
			return -1;
		return boat.getMainSailSize();
	}

	@Override
	public void setMainSailSize(UUID id, double mainSailSize) {
		IBoat boat = db.get(id);
		if (boat == null)
			return;
		boat.setMainSailSize(mainSailSize);
		db.save(boat);
		notifyObservers();
	}

	@Override
	public double getGenuaSize(UUID id) {
		IBoat boat = db.get(id);
		if (boat == null)
			return -1;
		return boat.getGenuaSize();
	}

	@Override
	public void setGenuaSize(UUID id, double genuaSize) {
		IBoat boat = db.get(id);
		if (boat == null)
			return;
		boat.setGenuaSize(genuaSize);
		db.save(boat);
		notifyObservers();
	}

	@Override
	public double getSpiSize(UUID id) {
		IBoat boat = db.get(id);
		if (boat == null)
			return -1;
		return boat.getSpiSize();
	}

	@Override
	public void setSpiSize(UUID id, double spiSize) {
		IBoat boat = db.get(id);
		if (boat == null)
			return;
		boat.setSpiSize(spiSize);
		db.save(boat);
		notifyObservers();
	}

	@Override
	public String getString(UUID id) {
		return "ID = " + id + " \n" + "BoatName = " + getBoatName(id) + "\n"
				+ "RegisterNr = " + getRegisterNr(id) + "\n" + "SailSign = "
				+ getSailSign(id) + "\n" + "HomePort = " + getHomePort(id)
				+ "\n" + "Yachtclub = " + getYachtclub(id) + "\n" + "\n"
				+ "Insurance = " + getInsurance(id) + "\n" + "CallSign = "
				+ getCallSign(id) + "\n" + "Type = " + getType(id) + "\n"
				+ "Constructor = " + getConstructor(id) + "\n" + "Length = "
				+ getLength(id) + "\n" + "Width = " + getWidth(id) + "\n"
				+ "Draft = " + getDraft(id) + "\n" + "MastHeight = "
				+ getMastHeight(id) + "Displacement = " + getDisplacement(id)
				+ "\n" + "Rigging = " + getRigging(id) + "\n"
				+ "YearOfConstruction = " + getYearOfConstruction(id) + "\n"
				+ "Motor = " + getMotor(id) + "\n" + "TankSize = "
				+ getTankSize(id) + "\n" + "WasteWaterTankSize = "
				+ getWasteWaterTankSize(id) + "\n" + "FreshWaterTankSize = "
				+ getFreshWaterTankSize(id) + "\n" + "MainSailSize = "
				+ getMainSailSize(id) + "\n" + "GenuaSize = "
				+ getGenuaSize(id) + "\n" + "SpiSize = " + getSpiSize(id);
	}

	@Override
	public UUID newBoat() {
		UUID newBoat = db.create();
		return newBoat;
	}

	public final void closeDB() {
		db.close();
		logger.info("BoatController", "Database closed");
	}

	@Override
	public void deleteBoat(UUID id) {
		db.delete(id);
		notifyObservers();
	}

	@Override
	public List<UUID> getBoats() {
		List<IBoat> query = db.loadAll();
		List<UUID> list = new ArrayList<UUID>();
		for (IBoat boat : query) {
			list.add(boat.getUUID());
		}
		return list;
	}

	@Override
	public IBoat getBoat(UUID boatId) {
		return db.get(boatId);
	}

	@Override
	public List<IBoat> getAllBoats() {
		return db.loadAll();
	}

	@Override
	public boolean saveBoat(IBoat boat) {
		return db.save(boat);
	}
}
