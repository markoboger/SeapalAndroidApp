package de.htwg.seapal.controller;

import java.util.List;
import java.util.UUID;

import de.htwg.seapal.observer.IObservable;

public interface IPersonController extends IObservable {

	List<UUID> getPersons();

	UUID newPerson();

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

	void deletePerson(UUID personId);

}