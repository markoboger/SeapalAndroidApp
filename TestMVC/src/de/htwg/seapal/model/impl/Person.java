package de.htwg.seapal.model.impl;

import java.text.SimpleDateFormat;
import java.util.UUID;

import de.htwg.seapal.model.IPerson;

public class Person implements IPerson {

	private String user; // UUID user

	private String id; // UUID

	private String firstname = null;

	private String lastname = null;

	private long birth; // unix timestamp

	private long registration; // unix timestamp

	private int age = 0;

	private String nationality = null;

	private String email = null;

	private String telephone = null;

	private String mobile = null;

	private String street = null;

	private int postcode = 0;

	private String city = null;

	private String country = null;

	public Person() {

	}

	public Person(IPerson person) {
		user = person.getUser();
		id = person.getId();

		firstname = person.getFirstname();
		lastname = person.getLastname();
		birth = person.getBirth();
		registration = person.getRegistration();
		age = person.getAge();
		nationality = person.getNationality();

		email = person.getEmail();
		telephone = person.getTelephone();
		mobile = person.getMobile();

		street = person.getStreet();
		postcode = person.getPostcode();
		city = person.getCity();
		country = person.getCountry();
	}

	public Person(int id) {
		this.id = "PERSON-" + id;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public UUID getUUId() {
		return UUID.fromString(id);
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public void setId(UUID id) {
		this.id = id.toString();
	}

	@Override
	public String getFirstname() {
		return firstname;
	}

	@Override
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	@Override
	public String getLastname() {
		return lastname;
	}

	@Override
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	@Override
	public long getBirth() {
		return birth;
	}

	@Override
	public void setBirth(long birth) {
		this.birth = birth;
	}

	@Override
	public long getRegistration() {
		return registration;
	}

	@Override
	public void setRegistration(long registration) {
		this.registration = registration;
	}

	@Override
	public int getAge() {
		return age;
	}

	@Override
	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public String getNationality() {
		return nationality;
	}

	@Override
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	@Override
	public String getEmail() {
		return email;
	}

	@Override
	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String getTelephone() {
		return telephone;
	}

	@Override
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	@Override
	public String getMobile() {
		return mobile;
	}

	@Override
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Override
	public String getStreet() {
		return street;
	}

	@Override
	public void setStreet(String street) {
		this.street = street;
	}

	@Override
	public int getPostcode() {
		return postcode;
	}

	@Override
	public void setPostcode(int postcode) {
		this.postcode = postcode;
	}

	@Override
	public String getCity() {
		return city;
	}

	@Override
	public void setCity(String city) {
		this.city = city;
	}

	@Override
	public String getCountry() {
		return country;
	}

	@Override
	public void setCountry(String country) {
		this.country = country;
	}

	@Override
	public String getString() {

		StringBuilder sb = new StringBuilder();

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

		sb.append("\n");
		sb.append("ID: \t\t").append(this.getId()).append("\n");
		sb.append("Firstname: \t").append(this.getFirstname()).append("\n");
		sb.append("Lastname: \t").append(this.getLastname()).append("\n");
		sb.append("Birth: \t\t").append(dateFormat.format(this.getBirth()))
				.append("\n");
		sb.append("Registration: \t")
				.append(dateFormat.format(this.getRegistration())).append("\n");
		sb.append("Age: \t\t").append(this.getAge()).append("\n");
		sb.append("Nationality: \t").append(this.getNationality()).append("\n");
		sb.append("Email: \t\t").append(this.getEmail()).append("\n");
		sb.append("Telephone: \t").append(this.getTelephone()).append("\n");
		sb.append("Mobile: \t").append(this.getMobile()).append("\n");
		sb.append("Street: \t").append(this.getStreet()).append("\n");
		sb.append("Postcode: \t").append(this.getPostcode()).append("\n");
		sb.append("City: \t\t").append(this.getCity()).append("\n");
		sb.append("Country: \t").append(this.getCountry()).append("\n");
		sb.append("\n");

		return sb.toString();
	}

	@Override
	public String getUser() {
		return user;
	}

	@Override
	public void setUser(String user) {
		this.user = user;
	}
}