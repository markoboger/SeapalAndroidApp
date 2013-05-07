package de.htwg.seapal.model;

import java.util.UUID;

public interface IPerson {

	String getId();

	String getFirstname();

	void setFirstname(String firstname);

	String getLastname();

	void setLastname(String lastname);

	long getBirth();

	void setBirth(long birth);

	long getRegistration();

	void setRegistration(long registration);

	int getAge();

	void setAge(int age);

	String getNationality();

	void setNationality(String nationality);

	String getEmail();

	void setEmail(String email);

	String getTelephone();

	void setTelephone(String telephone);

	String getMobile();

	void setMobile(String mobile);

	String getStreet();

	void setStreet(String street);

	int getPostcode();

	void setPostcode(int postcode);

	String getCity();

	void setCity(String city);

	String getCountry();

	void setCountry(String country);

	String getString();

	void setId(String id);

	String getUser();

	void setUser(String user);

	UUID getUUId();

	void setId(UUID id);

}