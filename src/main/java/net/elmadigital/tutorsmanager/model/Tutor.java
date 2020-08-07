package net.elmadigital.tutorsmanager.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import net.elmadigital.tutorsmanager.validator.TutCode;

public class Tutor {
	
	private long id;
	
	@NotEmpty(message = "Name should not be empty")
	private String name;
	
	private String[] expertizeAreas;
	
	@Pattern(regexp = "^[a-zA-Z0-9]{6}", message = "postcode should be 6chars/digits")
	private String postcode;
	
	@TutCode
	private String tutCode;
	
	//Constructors

	public Tutor() {}

	public Tutor(long id, 
			String name, 
			String postcode,
			String[] expertizeAreas,
			String tutCode) {
		super();
		this.id = id;
		this.name = name;
		this.postcode = postcode;
		this.expertizeAreas = expertizeAreas;
		this.tutCode = tutCode;
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

	public String[] getExpertizeAreas() {
		return expertizeAreas;
	}

	public void setExpertizeAreas(String[] expertizeAreas) {
		this.expertizeAreas = expertizeAreas;
	}
		
	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	
	public String getTutCode() {
		return tutCode;
	}

	public void setTutCode(String tutCode) {
		this.tutCode = tutCode;
	}
}
