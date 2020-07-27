package net.elmadigital.tutorsmanager.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class Tutor {
	
	private long id;
	
	@NotEmpty(message = "Name should not be empty")
	private String name;
	
	@NotEmpty(message = "Surname should not be empty")
	private String surname;
	
	@Email(message = "Email should be valid")
	private String email;
	
	private String[] expertizeAreas;
	
	//Constructors
	
	public Tutor() {}

	public Tutor(long id, String name, String surname, String email, String[] expertizeAreas) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.expertizeAreas = expertizeAreas;
	}
	
	//Getters & Setters

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String[] getExpertizeAreas() {
		return expertizeAreas;
	}

	public void setExpertizeAreas(String[] expertizeAreas) {
		this.expertizeAreas = expertizeAreas;
	}

}
