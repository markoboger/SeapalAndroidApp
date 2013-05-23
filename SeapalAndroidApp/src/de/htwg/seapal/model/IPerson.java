package de.htwg.seapal.model;

public interface IPerson extends IModel {

	String getFirstname();

	void setFirstname(String firstname);

	String getLastname();

	void setLastname(String lastname);

	Long getBirth();

	void setBirth(Long birth);

	Long getRegistration();

	void setRegistration(Long registration);

	Integer getAge();

	void setAge(Integer age);

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

	Integer getPostcode();

	void setPostcode(Integer postcode);

	String getCity();

	void setCity(String city);

	String getCountry();

	void setCountry(String country);

	String getUser();

	void setUser(String user);
}