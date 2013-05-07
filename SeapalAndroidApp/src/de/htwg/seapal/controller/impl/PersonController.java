package de.htwg.seapal.controller.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import de.htwg.seapal.controller.IPersonController;
import de.htwg.seapal.database.IPersonDatabase;
import de.htwg.seapal.model.IPerson;
import de.htwg.seapal.observer.Observable;

public class PersonController extends Observable implements IPersonController {

	protected IPersonDatabase db;

	public PersonController(IPersonDatabase db) {
		this.db = db;
	}

	@Override
	public UUID newPerson() {
		return db.newPerson();
	}

	@Override
	public void deletePerson(UUID personId) {
		db.deletePerson(personId);
	}

	@Override
	public String getPersonFirstname(UUID personId) {
		IPerson person = db.getPerson(personId);
		if (person == null)
			return null;
		return person.getFirstname();
	}

	@Override
	public void setPersonFirstname(UUID personId, String firstname) {
		IPerson person = db.getPerson(personId);
		if (person == null)
			return;
		person.setFirstname(firstname);
		db.savePerson(person);
		notifyObservers();
	}

	@Override
	public String getPersonLastname(UUID personId) {
		IPerson person = db.getPerson(personId);
		if (person == null)
			return null;
		return person.getLastname();
	}

	@Override
	public void setPersonLastname(UUID personId, String lastname) {
		IPerson person = db.getPerson(personId);
		if (person == null)
			return;
		person.setLastname(lastname);
		db.savePerson(person);
		notifyObservers();
	}

	@Override
	public long getPersonBirth(UUID personId) {
		IPerson person = db.getPerson(personId);
		if (person == null)
			return 0;
		return person.getBirth();
	}

	@Override
	public void setPersonBirth(UUID personId, long birth) {
		IPerson person = db.getPerson(personId);
		if (person == null)
			return;
		person.setBirth(birth);
		db.savePerson(person);
		notifyObservers();
	}

	@Override
	public long getPersonRegistration(UUID personId) {
		IPerson person = db.getPerson(personId);
		if (person == null)
			return 0;
		return person.getRegistration();
	}

	@Override
	public void setPersonRegistration(UUID personId, long registration) {
		IPerson person = db.getPerson(personId);
		if (person == null)
			return;
		person.setRegistration(registration);
		db.savePerson(person);
		notifyObservers();
	}

	@Override
	public int getPersonAge(UUID personId) {
		IPerson person = db.getPerson(personId);
		if (person == null)
			return -1;
		return person.getAge();
	}

	@Override
	public void setPersonAge(UUID personId, int age) {
		IPerson person = db.getPerson(personId);
		if (person == null)
			return;
		person.setAge(age);
		db.savePerson(person);
		notifyObservers();
	}

	@Override
	public String getPersonNationality(UUID personId) {
		IPerson person = db.getPerson(personId);
		if (person == null)
			return null;
		return person.getNationality();
	}

	@Override
	public void setPersonNationality(UUID personId, String nationality) {
		IPerson person = db.getPerson(personId);
		if (person == null)
			return;
		person.setNationality(nationality);
		db.savePerson(person);
		notifyObservers();
	}

	@Override
	public String getPersonEmail(UUID personId) {
		IPerson person = db.getPerson(personId);
		if (person == null)
			return null;
		return person.getEmail();
	}

	@Override
	public void setPersonEmail(UUID personId, String email) {
		IPerson person = db.getPerson(personId);
		if (person == null)
			return;
		person.setEmail(email);
		db.savePerson(person);
		notifyObservers();
	}

	@Override
	public String getPersonTelephone(UUID personId) {
		IPerson person = db.getPerson(personId);
		if (person == null)
			return null;
		return person.getTelephone();
	}

	@Override
	public void setPersonTelephone(UUID personId, String telephone) {
		IPerson person = db.getPerson(personId);
		if (person == null)
			return;
		person.setTelephone(telephone);
		db.savePerson(person);
		notifyObservers();
	}

	@Override
	public String getPersonMobile(UUID personId) {
		IPerson person = db.getPerson(personId);
		if (person == null)
			return null;
		return person.getMobile();
	}

	@Override
	public void setPersonMobile(UUID personId, String mobile) {
		IPerson person = db.getPerson(personId);
		if (person == null)
			return;
		person.setMobile(mobile);
		db.savePerson(person);
		notifyObservers();
	}

	@Override
	public String getPersonStreet(UUID personId) {
		IPerson person = db.getPerson(personId);
		if (person == null)
			return null;
		return person.getStreet();
	}

	@Override
	public void setPersonStreet(UUID personId, String street) {
		IPerson person = db.getPerson(personId);
		if (person == null)
			return;
		person.setStreet(street);
		db.savePerson(person);
		notifyObservers();
	}

	@Override
	public int getPersonPostcode(UUID personId) {
		IPerson person = db.getPerson(personId);
		if (person == null)
			return -1;
		return person.getPostcode();
	}

	@Override
	public void setPersonPostcode(UUID personId, int postcode) {
		IPerson person = db.getPerson(personId);
		if (person == null)
			return;
		person.setPostcode(postcode);
		db.savePerson(person);
		notifyObservers();
	}

	@Override
	public String getPersonCity(UUID personId) {
		IPerson person = db.getPerson(personId);
		if (person == null)
			return null;
		return person.getCity();
	}

	@Override
	public void setPersonCity(UUID personId, String city) {
		IPerson person = db.getPerson(personId);
		if (person == null)
			return;
		person.setCity(city);
		db.savePerson(person);
		notifyObservers();
	}

	@Override
	public String getPersonCountry(UUID personId) {
		IPerson person = db.getPerson(personId);
		if (person == null)
			return null;
		return person.getCountry();
	}

	@Override
	public void setPersonCountry(UUID personId, String country) {
		IPerson person = db.getPerson(personId);
		if (person == null)
			return;
		person.setCountry(country);
		db.savePerson(person);
		notifyObservers();
	}

	@Override
	public String getPersonString(UUID personId) {
		IPerson person = db.getPerson(personId);
		if (person == null)
			return null;
		return person.getString();
	}

	@Override
	public List<UUID> getPersons() {
		List<IPerson> persons = db.getPersons();
		List<UUID> list = new ArrayList<UUID>();
		for (IPerson person : persons) {
			list.add(person.getUUId());
		}
		return list;
	}
}