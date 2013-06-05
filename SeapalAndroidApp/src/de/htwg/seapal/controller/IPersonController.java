package de.htwg.seapal.controller;

import java.util.List;
import java.util.UUID;

import de.htwg.seapal.model.IPerson;
import de.htwg.seapal.utils.observer.IObservable;

public interface IPersonController extends IObservable {

	String getPersonFirstname(UUID personId);

	void setPersonFirstname(UUID personId, String firstname);

	String getPersonLastname(UUID personId);

	void setPersonLastname(UUID personId, String lastname);

	long getPersonBirth(UUID personId);

	void setPersonBirth(UUID personId, long birth);

	long getPersonRegistration(UUID personId);

	void setPersonRegistration(UUID personId, long registration);

	int getPersonAge(UUID personId);

	void setPersonAge(UUID personId, int age);

	String getPersonNationality(UUID personId);

	void setPersonNationality(UUID personId, String nationality);

	String getPersonEmail(UUID personId);

	void setPersonEmail(UUID personId, String email);

	String getPersonTelephone(UUID personId);

	void setPersonTelephone(UUID personId, String telephone);

	String getPersonMobile(UUID personId);

	void setPersonMobile(UUID personId, String mobile);

	String getPersonStreet(UUID personId);

	void setPersonStreet(UUID personId, String street);

	int getPersonPostcode(UUID personId);

	void setPersonPostcode(UUID personId, int postcode);

	String getPersonCity(UUID personId);

	void setPersonCity(UUID personId, String city);

	String getPersonCountry(UUID personId);

	void setPersonCountry(UUID personId, String country);

	String getPersonString(UUID personId);

	void closeDB();

	void deletePerson(UUID personId);

	List<UUID> getPersons();

	UUID newPerson();

	/**
	 * Gets a person by the given person ID.
	 * 
	 * @param personId
	 *            The person ID.
	 * @return The person or NULL, if no person was found.
	 */
	IPerson getPerson(UUID personId);

	/**
	 * Gets all persons.
	 * 
	 * @return All persons.
	 */
	List<IPerson> getAllPersons();

	/**
	 * Saves the person.
	 * 
	 * @param person
	 *            The person to save.
	 * @return Returns TRUE, if the person was newly created and FALSE when the
	 *         person was updated.
	 */
	boolean savePerson(IPerson person);
}
