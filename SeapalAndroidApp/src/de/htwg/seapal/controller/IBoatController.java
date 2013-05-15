package de.htwg.seapal.controller;

import java.util.List;
import java.util.UUID;

import de.htwg.seapal.model.IBoat;
import de.htwg.seapal.utils.observer.IObservable;

/**
 * The boat controller interface.
 */
public interface IBoatController extends IObservable {

	/**
	 * Gets the boat name of the given ID.
	 * @param id The boat ID.
	 * @return The boat name.
	 */
	String getBoatName(UUID id);

	/**
	 * Sets the boat name of the given boat ID.
	 * @param id The boat ID.
	 * @param boatName The boat name.
	 */
	void setBoatName(UUID id, String boatName);

	/**
	 * Gets the register number of the given boat ID.
	 * @param id The boat ID.
	 * @return The register number.
	 */
	String getRegisterNr(UUID id);

	/**
	 * Sets the register number of the given boat ID.
	 * @param id
	 * @param registerNr
	 */
	void setRegisterNr(UUID id, String registerNr);

	/**
	 * Gets the sail sign of the given boat ID.
	 * @param id The boat ID
	 * @return The sail sign.
	 */
	String getSailSign(UUID id);

	/**
	 * Sets the sail sign of the given boat ID.
	 * @param id The boat ID.
	 * @param sailSign The sail sign.
	 */
	void setSailSign(UUID id, String sailSign);

	/**
	 * Gets the home port of the given boat ID.
	 * @param id The boat ID.
	 * @return The home port.
	 */
	String getHomePort(UUID id);

	
	/**
	 * Sets the home port of the given boat ID.
	 * @param id The boat ID.
	 * @param homePort The home port.
	 */
	void setHomePort(UUID id, String homePort);

	/**
	 * Gets the yacht club of the given boat ID.
	 * @param id The boat ID.
	 * @return The yacht club.
	 */
	String getYachtclub(UUID id);

	/**
	 * Sets the yacht club of the given boat ID.
	 * @param id The boat ID.
	 * @param yachtclub The yacht club.
	 */
	void setYachtclub(UUID id, String yachtclub);

	/**
	 * Gets the owner ID by the given boat ID.
	 * @param id The boat ID.
	 * @return The owner ID.
	 */
	UUID getOwner(UUID id);

	/**
	 * Sets the owner by the given boat ID.
	 * @param id The boat ID.
	 * @param owner The owner ID.
	 */
	void setOwner(UUID id, UUID owner);

	/**
	 * Gets the insurance of the given boat ID.
	 * @param id The boat ID.
	 * @return The insurance.
	 */
	String getInsurance(UUID id);

	/**
	 * Sets the insurance of the given boat ID.
	 * @param id The boat ID.
	 * @param insurance The insurance.
	 */
	void setInsurance(UUID id, String insurance);

	/**
	 * Gets the call sign of the given boat ID.
	 * @param id The boat ID.
	 * @return The call sign.
	 */
	String getCallSign(UUID id);

	/**
	 * Sets the call sign of the given boat ID.
	 * @param id The boat ID.
	 * @param callSign The call sign.
	 */
	void setCallSign(UUID id, String callSign);

	/**
	 * Gets the type of the given boat ID.
	 * @param id The boat ID.
	 * @return The type.
	 */
	String getType(UUID id);

	/**
	 * Sets the type of the given boat ID.
	 * @param id The boat ID.
	 * @param type The type.
	 */
	void setType(UUID id, String type);

	/**
	 * Gets the constructor of the given boat ID.
	 * @param id The boat ID.
	 * @return The constructor.
	 */
	String getConstructor(UUID id);

	/**
	 * Sets the constructor of the given boat ID.
	 * @param id The boat ID.
	 * @param constructor The constructor.
	 */
	void setConstructor(UUID id, String constructor);

	/**
	 * Gets the length of the given boat ID.
	 * @param id The boat ID.
	 * @return The length.
	 */
	double getLength(UUID id);

	/**
	 * Sets the length of the given boat ID.
	 * @param id The boat ID.
	 * @param length The length.
	 */
	void setLength(UUID id, double length);

	/**
	 * Gets the width of the given boat ID.
	 * @param id The boat ID.
	 * @return The width.
	 */
	double getWidth(UUID id);

	/**
	 * Sets the width of the given boat ID.
	 * @param id The boat ID.
	 * @param width The width.
	 */
	void setWidth(UUID id, double width);

	/**
	 * Gets the draft of the given boat ID.
	 * @param id The boat ID.
	 * @return The draft.
	 */
	double getDraft(UUID id);

	/**
	 * Sets the draft of the given boat ID.
	 * @param id The boat ID.
	 * @param draft The draft.
	 */
	void setDraft(UUID id, double draft);

	/**
	 * Gets the mast height of the given boat ID.
	 * @param id The boat ID.
	 * @return The mast height.
	 */
	double getMastHeight(UUID id);

	/**
	 * Sets the mast height of the given boat ID.
	 * @param id The boat ID.
	 * @param mastHeight The mast height.
	 */
	void setMastHeight(UUID id, double mastHeight);

	/**
	 * Gets the displacement of the given boat ID.
	 * @param id The boat ID.
	 * @return The displacement.
	 */
	double getDisplacement(UUID id);

	/**
	 * Sets the displacement of the given boat ID.
	 * @param id The boat ID.
	 * @param displacement The displacement.
	 */
	void setDisplacement(UUID id, double displacement);

	/**
	 * Gets the rigging of the given boat ID.
	 * @param id The boat ID.
	 * @return The rigging.
	 */
	String getRigging(UUID id);

	/**
	 * Sets the rigging of the given boat ID.
	 * @param id The boat ID.
	 * @param rigging The rigging.
	 */
	void setRigging(UUID id, String rigging);

	/**
	 * Gets the year of construction of the given boat ID.
	 * @param id The boat ID.
	 * @return The year of construction.
	 */
	int getYearOfConstruction(UUID id);

	/**
	 * Sets the year of construction of the given boat ID.
	 * @param id The boat ID.
	 * @param yearOfConstruction The year of construction.
	 */
	void setYearOfConstruction(UUID id, int yearOfConstruction);

	/**
	 * Gets the motor of the given boat ID.
	 * @param id The boat ID.
	 * @return The motor.
	 */
	String getMotor(UUID id);

	/**
	 * Sets the motor of the given boat ID.
	 * @param id The boat ID.
	 * @param motor The motor.
	 */
	void setMotor(UUID id, String motor);

	/**
	 * Gets the tank size of the given boat ID.
	 * @param id The boat ID.
	 * @return The tank size.
	 */
	double getTankSize(UUID id);

	/**
	 * Sets the tank size of the given boat ID.
	 * @param id The boat ID.
	 * @param tankSize The tank size.
	 */
	void setTankSize(UUID id, double tankSize);

	/**
	 * Gets the waste water tank size of the given  boat ID.
	 * @param id The boat ID.
	 * @return The waste water tank size.
	 */
	double getWasteWaterTankSize(UUID id);

	/**
	 * Sets the waste water tank size of the given  boat ID.
	 * @param id The boat ID.
	 * @param wasteWaterTankSize The waste water tank size.
	 */
	void setWasteWaterTankSize(UUID id, double wasteWaterTankSize);

	/**
	 * Gets the fresh water tank size of the given  boat ID.
	 * @param id The boat ID.
	 * @return The fresh water tank size.
	 */
	double getFreshWaterTankSize(UUID id);

	/**
	 * Sets the fresh water tank size of the given  boat ID.
	 * @param id The boat ID.
	 * @param freshWaterTankSize The fresh water tank size.
	 */
	void setFreshWaterTankSize(UUID id, double freshWaterTankSize);

	/**
	 * Gets the main sail size of the given boat ID.
	 * @param id The boat ID.
	 * @return The main sail size.
	 */
	double getMainSailSize(UUID id);

	/**
	 * Sets the main sail size of the given boat ID.
	 * @param id The boat ID.
	 * @param mainSailSize The main sail size.
	 */
	void setMainSailSize(UUID id, double mainSailSize);

	/**
	 * Gets the genua size of the given boat ID.
	 * @param id The boat ID.
	 * @return The genua size.
	 */
	double getGenuaSize(UUID id);

	/**
	 * Sets the genua size of the given boat ID.
	 * @param id The boat ID.
	 * @param genuaSize The genua size.
	 */
	void setGenuaSize(UUID id, double genuaSize);

	/**
	 * Gets the spi size of the given boat ID.
	 * @param id The boat ID.
	 * @return The spi size.
	 */
	double getSpiSize(UUID id);

	/**
	 * Sets the spi size of the given boat ID.
	 * @param id The boat ID.
	 * @param spiSize The spi size.
	 */
	void setSpiSize(UUID id, double spiSize);

	/**
	 * Gets the output string of the given boat ID.
	 * @param id The boat ID.
	 * @return The output string.
	 */
	String getString(UUID id);

	/**
	 * Creates a new boat.
	 * @return The boat ID.
	 */
	UUID newBoat();

	/**
	 * Deletes a boat.
	 * @param id The boat ID to delete.
	 */
	void deleteBoat(UUID id);

	/**
	 * Closes the database connection.
	 */
	void closeDB();

	/**
	 * Gets a list of all boat IDs.
	 * @return All boat IDs.
	 */
	List<UUID> getBoats();
	
	/**
	 * Gets a boat by the given boat ID.
	 * @param boatId The boat ID.
	 * @return The boat or NULL, if no boat was found.
	 */
	IBoat getBoat(UUID boatId);
	
	/**
	 * Gets all boats.
	 * @return All boats.
	 */
	List<IBoat> getAllBoats();
	
	/**
	 * Saves the boat.
	 * @param boat The boat to save.
	 * @return Returns TRUE, if the boat was newly created
	 * 	       and FALSE when the boat was updated.
	 */
	boolean saveBoat(IBoat boat);
}
