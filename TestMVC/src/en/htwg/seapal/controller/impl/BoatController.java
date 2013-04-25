package en.htwg.seapal.controller.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import en.htwg.seapal.controller.IBoatController;
import en.htwg.seapal.database.IBoatDatabase;
import en.htwg.seapal.model.IBoat;
import en.htwg.seapal.observer.Observable;

public class BoatController extends Observable implements IBoatController {

	protected IBoatDatabase db;

	public BoatController(IBoatDatabase db) {
		this.db = db;
	}

	@Override
	public List<UUID> getBoats() {
		List<IBoat> query = db.getBoats();
		List<UUID> list = new ArrayList<UUID>();
		for (IBoat boat : query) {
			list.add(boat.getId());
		}
		return list;
	}

	@Override
	public String getBoatName(final UUID id) {
		return db.getBoat(id).getBoatName();
	}

	@Override
	public void setBoatName(UUID id, String BoatName) {
		IBoat boat = db.getBoat(id);
		boat.setBoatName(BoatName);
		db.saveBoat(boat);
		notifyObservers();
	}

	@Override
	public String getRegisterNr(UUID id) {
		return db.getBoat(id).getRegisterNr();
	}

	@Override
	public void setRegisterNr(UUID id, String registerNr) {
		IBoat boat = db.getBoat(id);
		boat.setRegisterNr(registerNr);
		db.saveBoat(boat);
		notifyObservers();
	}

	@Override
	public String getSailSign(UUID id) {
		return db.getBoat(id).getSailSign();
	}

	@Override
	public void setSailSign(UUID id, String SailSign) {
		IBoat boat = db.getBoat(id);
		boat.setSailSign(SailSign);
		db.saveBoat(boat);
		notifyObservers();
	}

	@Override
	public String getHomePort(UUID id) {
		return db.getBoat(id).getHomePort();
	}

	@Override
	public void setHomePort(UUID id, String HomePort) {
		IBoat boat = db.getBoat(id);
		boat.setHomePort(HomePort);
		db.saveBoat(boat);
		notifyObservers();
	}

	@Override
	public String getYachtclub(UUID id) {
		return db.getBoat(id).getYachtclub();
	}

	@Override
	public void setYachtclub(UUID id, String yachtclub) {
		IBoat boat = db.getBoat(id);
		boat.setYachtclub(yachtclub);
		db.saveBoat(boat);
		notifyObservers();
	}

	@Override
	public String getOwner(UUID id) {
		return db.getBoat(id).getOwner();
	}

	@Override
	public void setOwner(UUID id, String Owner) {
		IBoat boat = db.getBoat(id);
		boat.setOwner(Owner);
		db.saveBoat(boat);
		notifyObservers();
	}

	@Override
	public String getInsurance(UUID id) {
		return db.getBoat(id).getInsurance();
	}

	@Override
	public void setInsurance(UUID id, String Insurance) {
		IBoat boat = db.getBoat(id);
		boat.setInsurance(Insurance);
		db.saveBoat(boat);
		notifyObservers();
	}

	@Override
	public String getCallSign(UUID id) {
		return db.getBoat(id).getCallSign();
	}

	@Override
	public void setCallSign(UUID id, String CallSign) {
		IBoat boat = db.getBoat(id);
		boat.setCallSign(CallSign);
		db.saveBoat(boat);
		notifyObservers();
	}

	@Override
	public String getType(UUID id) {
		return db.getBoat(id).getType();
	}

	@Override
	public void setType(UUID id, String Type) {
		IBoat boat = db.getBoat(id);
		boat.setType(Type);
		db.saveBoat(boat);
		notifyObservers();
	}

	@Override
	public String getConstructor(UUID id) {
		return db.getBoat(id).getConstructor();
	}

	@Override
	public void setConstructor(UUID id, String Constructor) {
		IBoat boat = db.getBoat(id);
		boat.setConstructor(Constructor);
		db.saveBoat(boat);
		notifyObservers();
	}

	@Override
	public double getLength(UUID id) {
		return db.getBoat(id).getLength();
	}

	@Override
	public void setLength(UUID id, double Length) {
		IBoat boat = db.getBoat(id);
		boat.setLength(Length);
		db.saveBoat(boat);
		notifyObservers();
	}

	@Override
	public double getWidth(UUID id) {
		return db.getBoat(id).getWidth();
	}

	@Override
	public void setWidth(UUID id, double width) {
		IBoat boat = db.getBoat(id);
		boat.setWidth(width);
		db.saveBoat(boat);
		notifyObservers();
	}

	@Override
	public double getDraft(UUID id) {
		return db.getBoat(id).getDraft();
	}

	@Override
	public void setDraft(UUID id, double draft) {
		IBoat boat = db.getBoat(id);
		boat.setDraft(draft);
		db.saveBoat(boat);
		notifyObservers();
	}

	@Override
	public double getMastHeight(UUID id) {
		return db.getBoat(id).getMastHeight();
	}

	@Override
	public void setMastHeight(UUID id, double mastHeight) {
		IBoat boat = db.getBoat(id);
		boat.setMastHeight(mastHeight);
		db.saveBoat(boat);
		notifyObservers();
	}

	@Override
	public double getDisplacement(UUID id) {
		return db.getBoat(id).getDisplacement();
	}

	@Override
	public void setDisplacement(UUID id, double displacement) {
		IBoat boat = db.getBoat(id);
		boat.setDisplacement(displacement);
		db.saveBoat(boat);
		notifyObservers();
	}

	@Override
	public String getRigging(UUID id) {
		return db.getBoat(id).getRigging();
	}

	@Override
	public void setRigging(UUID id, String rigging) {
		IBoat boat = db.getBoat(id);
		boat.setRigging(rigging);
		db.saveBoat(boat);
		notifyObservers();
	}

	@Override
	public int getYearOfConstruction(UUID id) {
		return db.getBoat(id).getYearOfConstruction();
	}

	@Override
	public void setYearOfConstruction(UUID id, int yearOfConstruction) {
		IBoat boat = db.getBoat(id);
		boat.setYearOfConstruction(yearOfConstruction);
		db.saveBoat(boat);
		notifyObservers();
	}

	@Override
	public String getMotor(UUID id) {
		return db.getBoat(id).getMotor();
	}

	@Override
	public void setMotor(UUID id, String motor) {
		IBoat boat = db.getBoat(id);
		boat.setMotor(motor);
		db.saveBoat(boat);
		notifyObservers();
	}

	@Override
	public double getTankSize(UUID id) {
		return db.getBoat(id).getTankSize();
	}

	@Override
	public void setTankSize(UUID id, double tankSize) {
		IBoat boat = db.getBoat(id);
		boat.setTankSize(tankSize);
		db.saveBoat(boat);
		notifyObservers();
	}

	@Override
	public double getWasteWaterTankSize(UUID id) {
		return db.getBoat(id).getWasteWaterTankSize();
	}

	@Override
	public void setWasteWaterTankSize(UUID id, double wasteWaterTankSize) {
		IBoat boat = db.getBoat(id);
		boat.setWasteWaterTankSize(wasteWaterTankSize);
		db.saveBoat(boat);
		notifyObservers();
	}

	@Override
	public double getFreshWaterTankSize(UUID id) {
		return db.getBoat(id).getFreshWaterTankSize();
	}

	@Override
	public void setFreshWaterTankSize(UUID id, double freshWaterTankSize) {
		IBoat boat = db.getBoat(id);
		boat.setFreshWaterTankSize(freshWaterTankSize);
		db.saveBoat(boat);
		notifyObservers();
	}

	@Override
	public double getMainSailSize(UUID id) {
		return db.getBoat(id).getMainSailSize();
	}

	@Override
	public void setMainSailSize(UUID id, double mainSailSize) {
		IBoat boat = db.getBoat(id);
		boat.setMainSailSize(mainSailSize);
		db.saveBoat(boat);
		notifyObservers();
	}

	@Override
	public double getGenuaSize(UUID id) {
		return db.getBoat(id).getGenuaSize();
	}

	@Override
	public void setGenuaSize(UUID id, double genuaSize) {
		IBoat boat = db.getBoat(id);
		boat.setGenuaSize(genuaSize);
		db.saveBoat(boat);
		notifyObservers();
	}

	@Override
	public double getSpiSize(UUID id) {
		return db.getBoat(id).getSpiSize();
	}

	@Override
	public void setSpiSize(UUID id, double spiSize) {
		IBoat boat = db.getBoat(id);
		boat.setSpiSize(spiSize);
		db.saveBoat(boat);
		notifyObservers();
	}

	@Override
	public String getString(UUID id) {
		return "BoatName = " + getBoatName(id) + ", ID = " + id + ", RegisterNr = "
				+ getRegisterNr(id) + ", SailSign = " + getSailSign(id)
				+ ", HomePort = " + getHomePort(id) + ", Yachtclub = "
				+ getYachtclub(id) + ", Owner = " + getOwner(id)
				+ ", Insurance = " + getInsurance(id) + ", CallSign = "
				+ getCallSign(id) + ", Type = " + getType(id)
				+ ", Constructor = " + getConstructor(id) + ", Length = "
				+ getLength(id) + ", Width = " + getWidth(id)
				+ ", Draft = " + getDraft(id) + ", MastHeight = "
				+ getMastHeight(id) + ", Displacement = " + getDisplacement(id)
				+ ", Rigging = " + getRigging(id) + ", YearOfConstruction = "
				+ getYearOfConstruction(id) + ", Motor = " + getMotor(id)
				+ ", TankSize = " + getTankSize(id)
				+ ", WasteWaterTankSize = " + getWasteWaterTankSize(id)
				+ ", FreshWaterTankSize = " + getFreshWaterTankSize(id)
				+ ", MainSailSize = " + getMainSailSize(id)
				+ ", GenuaSize = " + getGenuaSize(id) + ", SpiSize = "
				+ getSpiSize(id);
	}

	@Override
	public UUID newBoat() {
		return db.newBoat();
	}

	public void closeDB() {
		db.closeDB();
	}

	@Override
	public void deleteBoat(UUID id) {
		db.deleteBoat(id);
		notifyObservers();
	}
}
